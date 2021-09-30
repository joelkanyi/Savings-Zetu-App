package com.kanyideveloper.savingszetu.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentMain) as NavHostFragment
        navController = navHostFragment.findNavController()

        binding.bottomNavigationView.apply {
            setupWithNavController(navController)
        }


        //hiding bottom navigation for splash screen  & onboading
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.payFragment -> {
                    binding.bottomNavigationView.isVisible = false
                }
                R.id.notificationsFragment -> {
                    binding.bottomNavigationView.isVisible = false
                }
                R.id.paySuccessFragment -> {
                    binding.bottomNavigationView.isVisible = false
                }
                R.id.notificationsFragment -> {
                    binding.bottomNavigationView.isVisible = false
                }
                R.id.adminFragment -> {
                    binding.bottomNavigationView.isVisible = false
                }
                else -> binding.bottomNavigationView.isVisible =
                    destination.id != R.id.statisticsFragment
            }
        }

    }
}