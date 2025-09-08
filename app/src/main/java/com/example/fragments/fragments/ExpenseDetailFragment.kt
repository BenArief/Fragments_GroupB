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
import androidx.fragment.app.setFragmentResult

// daftar detail pengeluaran
class ExpenseDetailFragment : Fragment() {

    private lateinit var spotId: String
    private lateinit var spotName: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter

    companion object {
        private const val ARG_SPOT_ID = "spot_id"
        private const val ARG_SPOT_NAME = "spot_name"

        // Factory method buat instance fragment
        fun newInstance(spotId: String, spotName: String): ExpenseDetailFragment {
            val fragment = ExpenseDetailFragment()
            val args = Bundle()
            args.putString(ARG_SPOT_ID, spotId)
            args.putString(ARG_SPOT_NAME, spotName)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            spotId = it.getString(ARG_SPOT_ID) ?: ""
            spotName = it.getString(ARG_SPOT_NAME) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_expense_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Toolbar dengan judul dan tombol kembali.
        val toolbar = view.findViewById<Toolbar>(R.id.expenseDetailToolbar)
        toolbar.title = "Pengeluaran - $spotName"
        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
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
        val fragment = AddExpenseFragment.newInstance(spotId)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
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