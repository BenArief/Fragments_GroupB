package com.example.fragments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.*
import com.example.fragments.R
import com.example.fragments.data.BudgetDataManager
import com.example.fragments.data.TouristSpot
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController

//form untuk menambahkan data Tempat Wisata baru.
// Baca aja AddExpenseFragment, kurang lebih sama soalnnya
class AddTouristSpotFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etLocation: EditText
    private lateinit var etBudget: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menghubungkan layout XML dengan fragment ini.
        return inflater.inflate(R.layout.fragment_add_tourist_spot, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi komponen UI setelah view dibuat.
        etName = view.findViewById(R.id.etSpotName)
        etLocation = view.findViewById(R.id.etLocation)
        etBudget = view.findViewById(R.id.etBudget)

        val btnSave = view.findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
            saveTouristSpot()
        }

        val btnCancel = view.findViewById<Button>(R.id.btnCancel)
        btnCancel.setOnClickListener {
            findNavController().popBackStack() // Kembali ke layar sebelumnya
        }
    }


    private fun saveTouristSpot() {
        val name = etName.text.toString().trim()
        val location = etLocation.text.toString().trim()
        val budgetStr = etBudget.text.toString().trim()

        if (name.isEmpty() || location.isEmpty() || budgetStr.isEmpty()) {
            Toast.makeText(context, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val budget = try {
            budgetStr.toDouble()
        } catch (e: NumberFormatException) {
            Toast.makeText(context, "Budget harus berupa angka", Toast.LENGTH_SHORT).show()
            return
        }

        if (budget <= 0) {
            Toast.makeText(context, "Budget harus lebih dari 0", Toast.LENGTH_SHORT).show()
            return
        }

        val touristSpot = TouristSpot(
            id = UUID.randomUUID().toString(),
            name = name,
            location = location,
            budget = budget
        )

        BudgetDataManager.addTouristSpot(touristSpot)
        Toast.makeText(context, "Wisata berhasil ditambahkan", Toast.LENGTH_SHORT).show()
        setFragmentResult("spot_added_request", bundleOf("refresh" to true))
        findNavController().popBackStack() // Kembali setelah menyimpan
    }
}