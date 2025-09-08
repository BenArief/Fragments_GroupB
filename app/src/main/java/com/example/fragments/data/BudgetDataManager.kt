package com.example.fragments.data

// buat ngolah data sih ini. pake object karenagw buat jadi singleton (buat satu data tunggal, kalo class nanti bakal buat banyak objeknya sendiri jadi list berbeda)
object BudgetDataManager {
    // simpen daftar tempat wisata dan pengeluaran. (gw Set mutable buat bisa diubah [tambah/hapus])
    private val touristSpots = mutableListOf<TouristSpot>()
    private val expenses = mutableListOf<Expense>()

    // add objek tourist
    fun addTouristSpot(spot: TouristSpot) {
        touristSpots.add(spot)
    }

    // return salinan (ini immutable btw).
    fun getTouristSpots(): List<TouristSpot> = touristSpots.toList()

    // add objek expense
    fun addExpense(expense: Expense) {
        expenses.add(expense)
        updateTotalSpent(expense.touristSpotId)
    }

    // ambil semua pengeluaran sesuai Id tempat wisatanya
    fun getExpensesBySpotId(spotId: String): List<Expense> {
        return expenses.filter { it.touristSpotId == spotId }
    }

    // Untuk itung ulang pengeluaran
    private fun updateTotalSpent(spotId: String) {
        val spot = touristSpots.find { it.id == spotId }
        // spot ketemu ?, eksekusi
        spot?.let {
            it.totalSpent = expenses.filter { expense ->
                expense.touristSpotId == spotId
            }.sumOf { expense -> expense.amount }
        }
    }

    // kebaca lah yah dari nama fungsinya males jelasin gw
    fun getTotalBudget(): Double = touristSpots.sumOf { it.budget }
    fun getTotalSpent(): Double = touristSpots.sumOf { it.totalSpent }
    fun getRemainingBudget(): Double = getTotalBudget() - getTotalSpent()
}