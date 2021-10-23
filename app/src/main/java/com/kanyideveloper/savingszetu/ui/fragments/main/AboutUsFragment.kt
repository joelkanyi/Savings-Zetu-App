package com.kanyideveloper.savingszetu.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.databinding.FragmentAboutUsBinding
import com.kanyideveloper.savingszetu.databinding.FragmentHelpBinding

class AboutUsFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentAboutUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAboutUsBinding.inflate(inflater, container, false)

        val view = binding.root
        navController = findNavController()

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.aboutUsToolbar.setupWithNavController(navController, appBarConfiguration)

        return view
    }
}