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
import com.kanyideveloper.savingszetu.adapters.HistoryAdapter
import com.kanyideveloper.savingszetu.databinding.FragmentHistoryBinding
import com.kanyideveloper.savingszetu.utils.EventObserver
import com.kanyideveloper.savingszetu.utils.showSnackbar
import com.kanyideveloper.savingszetu.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var navController: NavController

    private val viewModel: MainViewModel by viewModels()
    private val historyAdapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

        subscribeToObserver()

        navController = findNavController()

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbarHistory.setupWithNavController(navController, appBarConfiguration)

        return view
    }

    private fun subscribeToObserver() {
        viewModel.userTransactions.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
                binding.transactProgressbar.isVisible = false
            },
            onLoading = {
                binding.transactProgressbar.isVisible = true
            }
        ) {transactions ->
            if (transactions.isEmpty()){
                binding.imageView11Empty.isVisible = true
                binding.textView19Empty.isVisible = true
            }
            binding.transactProgressbar.isVisible = false
            historyAdapter.submitList(transactions)
            binding.recyclerViewAllHistory.adapter = historyAdapter
        })
    }
}