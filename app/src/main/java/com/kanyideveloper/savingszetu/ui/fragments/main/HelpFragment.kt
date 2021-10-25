package com.kanyideveloper.savingszetu.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.databinding.FragmentHelpBinding
import dagger.hilt.android.AndroidEntryPoint





@AndroidEntryPoint
class HelpFragment : Fragment(){

    private lateinit var navController: NavController
    private lateinit var binding: FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHelpBinding.inflate(inflater, container, false)

        val view = binding.root
        navController = findNavController()

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.helpToolbar.setupWithNavController(navController, appBarConfiguration)

        return view
    }
}