package com.ets.inven

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ets.inven.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Hide bottom nav by default
        navView.visibility = View.GONE

        // Hide or show bottom nav depending on which fragment is currently shown
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navigation_splash,
                R.id.navigation_login,
                R.id.navigation_register_one,
                R.id.navigation_register_two,
                R.id.navigation_ad_details_individual,
                R.id.navigation_company_details_individual,
                R.id.navigation_ad_applications_company,
                R.id.navigation_ad_application_details_company,
                R.id.navigation_chat_individual,
                -> {
                    navView.visibility = View.GONE
                }

                else -> {
                    navView.visibility = View.VISIBLE
                }
            }
        }

        navView.setupWithNavController(navController)
    }

    fun setIndividualMenu()  {
        navView.inflateMenu(R.menu.bottom_nav_menu_individual)
    }

    fun setCompanyMenu()  {
        navView.inflateMenu(R.menu.bottom_nav_menu_company)
    }
}