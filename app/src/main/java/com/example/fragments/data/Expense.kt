package com.example.fragments.data

// Just Data, kek database. tapi ini buat expense
data class Expense(
    val id: String,
    val touristSpotId: String,
    val category: String,
    val description: String,
    val amount: Double,
    val date: String
)