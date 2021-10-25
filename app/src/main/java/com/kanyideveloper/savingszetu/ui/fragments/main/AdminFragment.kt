package com.kanyideveloper.savingszetu.ui.fragments.main

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
import org.eazegraph.lib.models.PieModel
import timber.log.Timber


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
        subscribeToAllMoneyObserver()

        setData()

        binding.buttonDefaulters.setOnClickListener {
            findNavController().navigate(R.id.action_adminFragment_to_defaultersFragment)
        }

        binding.buttonThosePayed.setOnClickListener {
            findNavController().navigate(R.id.action_adminFragment_to_payersFragment)
        }

        binding.imageViewGoToAllTransactions.setOnClickListener {
            findNavController().navigate(R.id.action_adminFragment_to_allTransactionFragment)
        }
        return view
    }

    private fun setData(){
        binding.piechart.addPieSlice(PieModel("Payers",10f, Color.parseColor("#66BB6A")))
        binding.piechart.addPieSlice(PieModel("Defaulters",20f, Color.parseColor("#EF5350")))
        binding.piechart.addPieSlice(PieModel("Others",70f, Color.parseColor("#29B6F6")))

        binding.piechart.startAnimation()
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
            Timber.d(transactions.toString())
            binding.adminProgressBar.isVisible = false
            adapter.submitList(transactions)
            binding.allPaymentRecyclerView.adapter = adapter
        })
    }

    private fun subscribeToAllMoneyObserver() {
        viewModel.allMoney.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
            },
            onLoading = {
            }
        ) {money ->
            binding.textViewTotalBalance.text = "${String.format("%.2f", money.toDouble())}"
        })
    }
}