package com.example.fragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragments.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.fragments.adapters.ExpenseAdapter
import com.example.fragments.data.BudgetDataManager
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

// daftar detail pengeluaran
class ExpenseDetailFragment : Fragment() {

    private lateinit var spotId: String
    private lateinit var spotName: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter
    private val args: ExpenseDetailFragmentArgs by navArgs()

    /*companion object {
        // Factory method buat instance fragment
        fun newInstance(spotId: String, spotName: String): ExpenseDetailFragment {
            val fragment = ExpenseDetailFragment() //Buat Fragment baru
            val args = Bundle() //Buat Bundle Kosong
            args.putString(ARG_SPOT_ID, spotId) //Masukin data ke bundle
            args.putString(ARG_SPOT_NAME, spotName) //Masukin data ke bundle
            fragment.arguments = args //Pasang bundle ke fragment
            return fragment //Return Fragment yang sudah di buat
        }
        private const val ARG_SPOT_ID = "spot_id"

        private const val ARG_SPOT_NAME = "spot_name"
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Mengambil data dari bundle
        // Ambil dari Safe Args
        spotId = args.spotId
        spotName = args.spotName
    }

    override fun onCreateView(
        inflater: LayoutInflater, // Buat tampilan dari layout
        container: ViewGroup?, // Parent kontainer buat ukuran referensi
        savedInstanceState: Bundle? // Bundle buat ngambil data dari fragment lain
    ): View? {
        return inflater.inflate(R.layout.fragment_expense_detail // file xml yang diubah
            , container // parent untuk referensi ukuran
            , false // memasang view yang telah kita buat ke kontainer
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.expenseDetailToolbar)
        toolbar.title = "Pengeluaran - $spotName"
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        setupRecyclerView(view)
        setupFab(view)
        refreshData()
    }

    // kiri sinyal kembali ke touristspotlist + ngerefresh total pengeluaran di touristspot list
    override fun onDestroyView() {
        super.onDestroyView()
        parentFragmentManager.setFragmentResult("expense_updated_request", bundleOf("refresh" to true))
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerViewExpenses)
        adapter = ExpenseAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun setupFab(view: View) {
        val fab = view.findViewById<FloatingActionButton>(R.id.fabAddExpense)
        fab.setOnClickListener {
            openAddExpense()
        }
    }

    // navigasi addexpense
    private fun openAddExpense() {
        val action = ExpenseDetailFragmentDirections.actionExpenseDetailFragmentToAddExpenseFragment(spotId)
        findNavController().navigate(action)
    }

    // ambil data pengeluaran terbaru dari budgetdatamanager
    private fun refreshData() {
        val expenses = BudgetDataManager.getExpensesBySpotId(spotId)
        adapter.updateData(expenses)
    }

    // memastikan daftar pengeluaran diperbarui
    override fun onResume() {
        super.onResume()
        refreshData()
    }
}