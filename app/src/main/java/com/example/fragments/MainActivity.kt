package com.example.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarLayout = findViewById<AppBarLayout>(R.id.appBarLayout)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        // Tambahkan tab secara manual
        tabLayout.addTab(tabLayout.newTab().setText("Wisata"))
        tabLayout.addTab(tabLayout.newTab().setText("Budget Overview"))

        // Listener untuk mengatur visibilitas AppBar berdasarkan tujuan navigasi
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.touristSpotListFragment || destination.id == R.id.budgetOverviewFragment) {
                appBarLayout.visibility = View.VISIBLE
            } else {
                appBarLayout.visibility = View.GONE
            }
        }

        // Listener untuk menangani klik pada tab
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> if (navController.currentDestination?.id != R.id.touristSpotListFragment) {
                        navController.navigate(R.id.touristSpotListFragment)
                    }
                    1 -> if (navController.currentDestination?.id != R.id.budgetOverviewFragment) {
                        navController.navigate(R.id.budgetOverviewFragment)
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}