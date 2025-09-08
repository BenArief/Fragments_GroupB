package com.example.fragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragments.adapters.TouristSpotAdapter
import com.example.fragments.data.TouristSpot
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.fragments.R
import android.widget.TextView
import androidx.fragment.app.setFragmentResultListener
import com.example.fragments.data.BudgetDataManager


class TouristSpotListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TouristSpotAdapter
    private lateinit var tvEmpty: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tourist_spot_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvEmpty = view.findViewById(R.id.tvEmpty)

        setupRecyclerView(view)
        setupFab(view)

        //dapet sinyal dari fragment lain kalo ada perubahan
        setFragmentResultListener("spot_added_request") { requestKey, bundle ->
            refreshData()
        }

        setFragmentResultListener("expense_updated_request") { requestKey, bundle ->
            refreshData()
        }
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)

        //inisialisasi adafter dan ngedefinisiin aksi saat di klik
        adapter = TouristSpotAdapter { spot ->
            openExpenseDetail(spot) // buka detail pengeluaran
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        refreshData()
    }

    private fun setupFab(view: View) {
        val fab = view.findViewById<FloatingActionButton>(R.id.fabAdd)
        fab.setOnClickListener {
            openAddTouristSpot()
        }
    }

    // navigasi ke addtourist
    private fun openAddTouristSpot() {
        val fragment = AddTouristSpotFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    // navigassi ke expensedetail
    private fun openExpenseDetail(spot: TouristSpot) {
        val fragment = ExpenseDetailFragment.newInstance(spot.id, spot.name)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    // mengambil data terbaru + memperbarui recyclerview
    private fun refreshData() {
        val spots = BudgetDataManager.getTouristSpots()
        adapter.updateData(spots)

        // kalo kosong daftarnya
        if (spots.isEmpty()) {
            recyclerView.visibility = View.GONE
            tvEmpty.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            tvEmpty.visibility = View.GONE
        }
    }

    // kek lainnya, fresh data
    override fun onResume() {
        super.onResume()
        refreshData()
    }
}