package com.kanyideveloper.savingszetu.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.adapters.AdminTransacAdapter
import com.kanyideveloper.savingszetu.databinding.FragmentAdminBinding
import com.kanyideveloper.savingszetu.utils.EventObserver
import com.kanyideveloper.savingszetu.utils.showSnackbar
import com.kanyideveloper.savingszetu.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AdminFragment : Fragment() {

    private lateinit var binding: FragmentAdminBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController

    private val adapter: AdminTransacAdapter by lazy { AdminTransacAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminBinding.inflate(inflater,container, false)
        val view = binding.root

        navController = findNavController()

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.adminToolbar.setupWithNavController(navController, appBarConfiguration)

        subscribeToObserver()

        binding.buttonDefaulters.setOnClickListener {
            findNavController().navigate(R.id.action_adminFragment_to_defaultersFragment)
        }

        binding.buttonThosePayed.setOnClickListener {
            findNavController().navigate(R.id.action_adminFragment_to_payersFragment)
        }

        return view
    }

    private fun subscribeToObserver() {
        viewModel.adminFourTransactions.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
                binding.adminProgressBar.isVisible = false
            },
            onLoading = {
                binding.adminProgressBar.isVisible = true
            }
        ) {transactions ->
            binding.adminProgressBar.isVisible = false
            adapter.submitList(transactions)
            binding.allPaymentRecyclerView.adapter = adapter
        })
    }
}