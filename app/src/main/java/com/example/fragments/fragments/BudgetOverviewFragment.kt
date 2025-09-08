package com.example.fragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fragments.R
import com.example.fragments.data.BudgetDataManager
import java.text.NumberFormat
import java.util.*

class BudgetOverviewFragment : Fragment() {

    // Formatter
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_budget_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateBudgetInfo(view)
    }

    // ambil data dari budgetdata manager lalu tampilin di textview
    private fun updateBudgetInfo(view: View) {
        // inisialisasi
        val tvTotalBudget = view.findViewById<TextView>(R.id.tvTotalBudget)
        val tvTotalSpent = view.findViewById<TextView>(R.id.tvTotalSpent)
        val tvRemaining = view.findViewById<TextView>(R.id.tvRemaining)

        val totalBudget = BudgetDataManager.getTotalBudget()
        val totalSpent = BudgetDataManager.getTotalSpent()
        val remaining = BudgetDataManager.getRemainingBudget()

        //tampil
        tvTotalBudget.text = "Total Budget: ${currencyFormat.format(totalBudget)}"
        tvTotalSpent.text = "Total Pengeluaran: ${currencyFormat.format(totalSpent)}"
        tvRemaining.text = "Sisa Budget: ${currencyFormat.format(remaining)}"

        // ubah warna
        if (remaining < 0) {
            tvRemaining.setTextColor(requireContext().getColor(android.R.color.holo_red_dark))
        } else {
            tvRemaining.setTextColor(requireContext().getColor(android.R.color.holo_green_dark))
        }
    }

    // Lifecycle method dipanggil untuk tiap kali fragment ini jadi layar aktif. jadi buat kasih terupdate
    override fun onResume() {
        super.onResume()
        view?.let { updateBudgetInfo(it) }
    }
}