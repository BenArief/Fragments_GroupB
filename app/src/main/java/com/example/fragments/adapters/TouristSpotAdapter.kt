package com.example.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fragments.R
import com.example.fragments.data.TouristSpot
import java.text.NumberFormat
import java.util.*

// buat tampilin data Tempat Wisata dalam Recycler View
class TouristSpotAdapter(
    private val onItemClick: (TouristSpot) -> Unit
) : RecyclerView.Adapter<TouristSpotAdapter.ViewHolder>() {

    // Simpen daftar data wisata yang nanti bakal di tampilin
    private var spots = listOf<TouristSpot>()

    //Formatter doang ini buat ke uang rupiah
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

    // Buat perbarui data di dalam adapter
    fun updateData(newSpots: List<TouristSpot>) {
        spots = newSpots
        notifyDataSetChanged()
    }

    // Buat viewholder baru kalo RecycleView butuhin
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tourist_spot, parent, false)
        return ViewHolder(view)
    }

    // Hubungin data dari listt ke viewholders (sesuai posisi) [susah gw jelasin]
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = spots[position]
        holder.bind(spot)
    }

    // ambil total item di spots
    override fun getItemCount(): Int = spots.size

    // Class viewholder yang nampung referensi view dari layotu item_tourist_spot.xml
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvSpotName)
        private val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        private val tvBudget: TextView = itemView.findViewById(R.id.tvBudget)
        private val tvSpent: TextView = itemView.findViewById(R.id.tvSpent)
        private val tvRemaining: TextView = itemView.findViewById(R.id.tvRemaining)


        // Masukin data aja sih ini ke viewholdernya
        fun bind(spot: TouristSpot) {
            tvName.text = spot.name
            tvLocation.text = spot.location
            tvBudget.text = "Budget: ${currencyFormat.format(spot.budget)}"
            tvSpent.text = "Terpakai: ${currencyFormat.format(spot.totalSpent)}"

            val remaining = spot.budget - spot.totalSpent
            tvRemaining.text = "Sisa: ${currencyFormat.format(remaining)}"

            // Kalo overbudget merah, kalo gk hijau
            if (remaining < 0) {
                tvRemaining.setTextColor(itemView.context.getColor(android.R.color.holo_red_dark))
            } else {
                tvRemaining.setTextColor(itemView.context.getColor(android.R.color.holo_green_dark))
            }

            // Listener klik jadi kalo diklik bakal ngebuka detail tempat wisata
            itemView.setOnClickListener {
                onItemClick(spot)
            }
        }
    }
}