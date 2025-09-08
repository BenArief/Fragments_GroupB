package com.example.fragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.fragments.R
import java.text.SimpleDateFormat
import java.util.*
import com.example.fragments.data.BudgetDataManager
import com.example.fragments.data.Expense

// Fragment buat nambahin data pengeluaran
class AddExpenseFragment : Fragment() {

    // inisialisasi (lateinit buat buat non-null dan juga gk butuh di beri nilai saat itu juga. jadi user bisa isi datanya nanti)
    private lateinit var spotId: String
    private lateinit var spinnerCategory: Spinner
    private lateinit var etDescription: EditText
    private lateinit var etAmount: EditText

    // Nampung fungsi dengan class
    companion object {
        // kunci konstan
        private const val ARG_SPOT_ID = "spot_id"

        fun newInstance(spotId: String): AddExpenseFragment {
            val fragment = AddExpenseFragment()
            val args = Bundle()
            args.putString(ARG_SPOT_ID, spotId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ambil argumen dari bundle newinstance
        arguments?.let {
            // ambil id tempat wisata dari bundle, kalo gk ada pake string kosong
            spotId = it.getString(ARG_SPOT_ID) ?: ""
        }
    }

    // buat tampilan fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_expense, container, false)
    }

    // buat memanipulasi view setelah tampilan dibuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // inisialisasi view dari layout
        spinnerCategory = view.findViewById(R.id.spinnerCategory)
        etDescription = view.findViewById(R.id.etDescription)
        etAmount = view.findViewById(R.id.etAmount)

        // panggil fungsi buat isi data ke spinner kategori
        setupCategorySpinner()

        // buat listener buat tombol simpan dan batal
        val btnSave = view.findViewById<Button>(R.id.btnSaveExpense)
        btnSave.setOnClickListener {
            saveExpense()
        }

        val btnCancel = view.findViewById<Button>(R.id.btnCancelExpense)
        btnCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    // Fungsi helper buat kategori
    private fun setupCategorySpinner() {
        val categories = arrayOf(
            "Transportasi", "Akomodasi", "Makanan", "Tiket Masuk",
            "Oleh-oleh", "Lainnya"
        )

        // buat array adapter untuk hubungin kategorri sama spinner
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories
        )

        // Layout dalam bentuk drop down
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter
    }

    // Menyimpan data
    private fun saveExpense() {
        // ambil
        val category = spinnerCategory.selectedItem.toString()
        val description = etDescription.text.toString().trim()
        val amountStr = etAmount.text.toString().trim()

        // validasi input
        if (description.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(context, "Deskripsi dan jumlah harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        // validasi input angka
        val amount = try {
            amountStr.toDouble()
        } catch (e: NumberFormatException) {
            Toast.makeText(context, "Jumlah harus berupa angka", Toast.LENGTH_SHORT).show()
            return
        }

        // jumlah lebih dari 0
        if (amount <= 0) {
            Toast.makeText(context, "Jumlah harus lebih dari 0", Toast.LENGTH_SHORT).show()
            return
        }

        // tanggal hari ini
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        // buat objek enxpense baru + masukin semua datannya
        val expense = Expense(
            id = UUID.randomUUID().toString(),  //generate id unik
            touristSpotId = spotId,
            category = category,
            description = description,
            amount = amount,
            date = currentDate
        )

        // simpan
        BudgetDataManager.addExpense(expense)

        //kasih feedback
        Toast.makeText(context, "Pengeluaran berhasil ditambahkan", Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()
    }
}
