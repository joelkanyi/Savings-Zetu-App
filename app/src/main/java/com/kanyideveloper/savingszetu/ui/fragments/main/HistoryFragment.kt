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
import com.kanyideveloper.savingszetu.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root
        navController = findNavController()

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarHistory.setupWithNavController(navController, appBarConfiguration)

        return view
    }
}