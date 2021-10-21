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
import com.kanyideveloper.savingszetu.adapters.AllPaymentsAdapter
import com.kanyideveloper.savingszetu.databinding.FragmentAllTransactionBinding
import com.kanyideveloper.savingszetu.utils.EventObserver
import com.kanyideveloper.savingszetu.utils.showSnackbar
import com.kanyideveloper.savingszetu.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllTransactionFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private val adapter: AllPaymentsAdapter by lazy { AllPaymentsAdapter() }
    private lateinit var binding: FragmentAllTransactionBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllTransactionBinding.inflate(inflater, container, false)
        val view = binding.root

        navController = findNavController()

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.allTransactionsToolbar.setupWithNavController(navController, appBarConfiguration)

        subscribeToObserver()

        return view
    }

    private fun subscribeToObserver() {
        viewModel.adminTransactions.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
                binding.allPaymentsProgressBar.isVisible = false
            },
            onLoading = {
                binding.allPaymentsProgressBar.isVisible = true
            }
        ) { transactions ->
            binding.allPaymentsProgressBar.isVisible = false
            adapter.submitList(transactions)
            binding.allAdminsTransactsRecyclerView.adapter = adapter
        })
    }
}