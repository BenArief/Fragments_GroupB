package com.example.fragments

import android.os.Bundle
import android.view.View //
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.fragments.fragments.*
import com.google.android.material.appbar.AppBarLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // tetapin layout xml
        setContentView(R.layout.activity_main)

        //insialisasi komponen ui
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val appBarLayout = findViewById<AppBarLayout>(R.id.appBarLayout)
        //panggil fungsi mengatur viewpager
        setupViewPager(viewPager)

        // tambahin listener buat pantau perubahan di back stack fragmant (berissi riwayat fragment yg udh dibuka)
        supportFragmentManager.addOnBackStackChangedListener {
            // kalo back stack > 0, di fragment detail
            if (supportFragmentManager.backStackEntryCount > 0) {
                viewPager.visibility = View.GONE
                appBarLayout.visibility = View.GONE
            } else {
                // kalo back stack 0 = kembali ke layar utama
                viewPager.visibility = View.VISIBLE
                appBarLayout.visibility = View.VISIBLE
            }
        }
    }

    //helper buat konfigurasi viewparger
    private fun setupViewPager(viewPager: ViewPager2) {
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        //buat instance dari adapter internal
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // kelas utilitas yang hubungin TabLayout dengan view pager. menangani sinkornisasi antara tab yang dipilih dan halaman yang ditampilin
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            //tetapin teks untuk tiap tab sesuai posisinya
            tab.text = when(position) {
                0 -> "Wisata"
                1 -> "Budget Overview"
                else -> "Tab ${position + 1}"
            }
        }.attach()
    }

    // adapter internal buat nyediain fragment untuk viewparger
    private class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {

            override fun getItemCount(): Int = 2

            override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> TouristSpotListFragment() // daftar wisata
                1 -> BudgetOverviewFragment() // ringkasan budge
                else -> TouristSpotListFragment() // fallback default
            }
        }
    }
}