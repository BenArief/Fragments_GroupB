package com.example.fragments.data

// Just Data, kek database. tapi ini buat tourist spot
data class TouristSpot(
    val id: String,
    val name: String,
    val location: String,
    val budget: Double,
    var totalSpent: Double = 0.0
)