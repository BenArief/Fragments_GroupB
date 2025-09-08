package com.example.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fragments.R
import com.example.fragments.data.Expense
import java.text.NumberFormat
import java.util.*


class ExpenseAdapter : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    // Simpen data pengeluaran (gw private buat bisa diubah dari updateData doang)
    private var expenses = listOf<Expense>()

    //Ubah angka (long) jadi mata uang rupiah
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

    // buat memperbarui data aja
    fun updateData(newExpenses: List<Expense>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }

    // Buat viewholder baru kalo RecycleView butuhin
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ViewHolder(view)
    }

    // Hubungin data dari listt ke viewholders (sesuai posisi)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(expenses[position])
    }

    // ambil total item di expenses
    override fun getItemCount(): Int = expenses.size

    // Class viewholder yang nampung referensi view dari layotu item_expense.xml
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)

        // Masukin data aja sih ini ke viewholdernya
        fun bind(expense: Expense) {
            tvCategory.text = expense.category
            tvDescription.text = expense.description
            tvAmount.text = currencyFormat.format(expense.amount)
            tvDate.text = expense.date
        }
    }
}