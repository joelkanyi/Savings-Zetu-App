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
import com.kanyideveloper.savingszetu.adapters.PaymentAdapter
import com.kanyideveloper.savingszetu.databinding.FragmentPayersBinding
import com.kanyideveloper.savingszetu.utils.EventObserver
import com.kanyideveloper.savingszetu.utils.showSnackbar
import com.kanyideveloper.savingszetu.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PayersFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentPayersBinding
    private val adapter: PaymentAdapter by lazy { PaymentAdapter() }
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayersBinding.inflate(inflater, container, false)

        val view = binding.root

        navController = findNavController()

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.payersToolbar.setupWithNavController(navController, appBarConfiguration)

        subscribeToPayersObserver()

        return view
    }

    private fun subscribeToPayersObserver() {
        viewModel.payers.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
                binding.payersProgressBar.isVisible = false
            },
            onLoading = {
                binding.payersProgressBar.isVisible = true
            }
        ) { payers ->
            binding.payersProgressBar.isVisible = false
            adapter.submitList(payers)
            binding.payersRecyclerView.adapter = adapter
        })
    }

}