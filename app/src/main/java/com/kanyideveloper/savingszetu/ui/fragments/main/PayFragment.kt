package com.kanyideveloper.savingszetu.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.androidstudy.daraja.Daraja
import com.androidstudy.daraja.DarajaListener
import com.androidstudy.daraja.model.AccessToken
import com.androidstudy.daraja.model.LNMExpress
import com.androidstudy.daraja.model.LNMResult
import com.androidstudy.daraja.util.Env
import com.androidstudy.daraja.util.TransactionType
import com.google.firebase.messaging.FirebaseMessaging
import com.kanyideveloper.savingszetu.utils.MpesaListener
import com.kanyideveloper.savingszetu.databinding.FragmentPayBinding
import com.kanyideveloper.savingszetu.utils.EventObserver
import com.kanyideveloper.savingszetu.utils.TransactionConstants
import com.kanyideveloper.savingszetu.utils.showSnackbar
import com.kanyideveloper.savingszetu.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PayFragment : Fragment(), MpesaListener {

    private lateinit var binding: FragmentPayBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    companion object {
        lateinit var mpesaListener: MpesaListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayBinding.inflate(inflater, container, false)
        val view = binding.root

        mpesaListener = this

        navController = findNavController()

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.payToolbar.setupWithNavController(navController, appBarConfiguration)

        subscribeToObservers()

        binding.button.setOnClickListener {
            /*viewModel.saveTransaction(
                "JK3278944BO",
                "100000",
                "Joel Kanyi"
            )*/
            viewModel.currentNumber.observe(viewLifecycleOwner, Observer { amount ->
                viewModel.pay("0706003891", amount)
            })
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
            viewModel.deleteChars()
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
            showSnackbar("PaymentRepository Successful")
        })
    }

    override fun sendSuccessful(amount: String, phone: String, date: String, receipt: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(
                requireContext(), "PaymentRepository Successful\n" +
                        "Receipt: $receipt\n" +
                        "Date: $date\n" +
                        "Phone: $phone\n" +
                        "Amount: $amount", Toast.LENGTH_LONG
            ).show()

        }
    }

    override fun sendFailed(cause: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(
                requireContext(), "PaymentRepository Failed\n" +
                        "Reason: $cause", Toast.LENGTH_LONG
            ).show()
        }
    }
}