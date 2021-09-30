package com.kanyideveloper.savingszetu.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.databinding.FragmentPayBinding
import com.kanyideveloper.savingszetu.utils.EventObserver
import com.kanyideveloper.savingszetu.utils.showSnackbar
import com.kanyideveloper.savingszetu.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayFragment : Fragment() {

    private lateinit var binding: FragmentPayBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPayBinding.inflate(inflater, container, false)
        val view = binding.root

        subscribeToObservers()

        binding.button.setOnClickListener {
            viewModel.saveTransaction(
                "JK3278944BO",
                "100000",
                "Joel Kanyi"
            )
        }

        binding.textViewOne.setOnClickListener {
            viewModel.keyClicked("1")
        }

        binding.textViewTwo.setOnClickListener {
            viewModel.keyClicked("2")
        }

        binding.textViewThree.setOnClickListener {
            viewModel.keyClicked("3")
        }

        binding.textViewFour.setOnClickListener {
            viewModel.keyClicked("4")
        }

        binding.textViewFive.setOnClickListener {
            viewModel.keyClicked("5")
        }

        binding.textViewSix.setOnClickListener {
            viewModel.keyClicked("6")
        }

        binding.textViewSeven.setOnClickListener {
            viewModel.keyClicked("7")
        }

        binding.textViewEight.setOnClickListener {
            viewModel.keyClicked("8")
        }

        binding.textViewNine.setOnClickListener {
            viewModel.keyClicked("9")
        }

        binding.textViewZero.setOnClickListener {
            viewModel.keyClicked("0")
        }

        binding.textViewDoubleZero.setOnClickListener {
            viewModel.keyClicked("00")
        }

        binding.backspace.setOnClickListener {

        }

        viewModel.currentNumber.observe(viewLifecycleOwner, Observer {
            binding.textViewAmount.text = it
        })

        return view
    }

    private fun subscribeToObservers(){
        viewModel.saveTransactionStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                binding.paymentProgressbar.isVisible = false
                showSnackbar(it)
            },
            onLoading = {
                binding.paymentProgressbar.isVisible = true
            }
        ){
            binding.paymentProgressbar.isVisible = false
            showSnackbar("Payment Successful")
        })
    }
}