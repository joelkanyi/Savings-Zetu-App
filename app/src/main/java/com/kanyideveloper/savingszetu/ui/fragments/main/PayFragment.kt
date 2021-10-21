package com.kanyideveloper.savingszetu.ui.fragments.main

import android.annotation.SuppressLint
import android.graphics.Color
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
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.kanyideveloper.savingszetu.databinding.FragmentPayBinding
import com.kanyideveloper.savingszetu.utils.*
import com.kanyideveloper.savingszetu.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PayFragment : Fragment(), MpesaListener {

    private lateinit var binding: FragmentPayBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var phone: String

    private lateinit var successAlert: SweetAlertDialog
    private lateinit var loadingAlert: SweetAlertDialog
    private lateinit var confirmDialog: SweetAlertDialog
    private lateinit var errorAlert: SweetAlertDialog
    private lateinit var sentAlertDialog: SweetAlertDialog

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
        subscribeToObservers()
        subscribeToSendObservers()
        subscribeToUserProfileObserver()

        //Success Dialog
        successAlert = SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
        successAlert.progressHelper.barColor = Color.parseColor("#A5DC86")
        successAlert.titleText = "Payment Success"
        successAlert.contentText = "You payment was sent Successfully"
        successAlert.setConfirmClickListener { successAlert.dismissWithAnimation() }
        successAlert.setCancelable(false)

        //Loading Dialog
        loadingAlert = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        loadingAlert.progressHelper.barColor = Color.parseColor("#A5DC86")
        loadingAlert.titleText = "Loading..."
        loadingAlert.setCancelable(false)

        //Error Dialog
        errorAlert = SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
        errorAlert.titleText = "Oops..."
        errorAlert.contentText = "Something went wrong!"

        //Sent Dialog
        sentAlertDialog = SweetAlertDialog(requireContext())
        sentAlertDialog.titleText = "STK Pane"
        sentAlertDialog.contentText =
            "Please wait for the Safaricom pane where you can input your pin"


        //Confirm Dialog
/*        confirmDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Are you sure you want to request garbage collection?")
            .setCancelText("No")
            .setConfirmText("Yes")
            .showCancelButton(true)
            .setConfirmClickListener {
                this.showLoadingAlert()

                // Do some logic stuffs
                this.showSuccessAlert()
            }
            .setCancelClickListener {
                //dismiss the dialog
            }
            .show()*/

        navController = findNavController()

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.payToolbar.setupWithNavController(navController, appBarConfiguration)



        binding.button.setOnClickListener {
            /*viewModel.saveTransaction(
                "JK3278944BO",
                "100000",
                "Joel Kanyi"
            )*/
            loadingAlert.show()
            viewModel.currentNumber.observe(viewLifecycleOwner, Observer { amount ->
                viewModel.pay(phone, amount)
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

    private fun subscribeToObservers() {
        viewModel.saveTransactionStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                binding.paymentProgressbar.isVisible = false
                showSnackbar(it)
            },
            onLoading = {
                binding.paymentProgressbar.isVisible = true
            }
        ) {
            binding.paymentProgressbar.isVisible = false
            showSnackbar("PaymentRepository Successful")
        })
    }

    private fun subscribeToSendObservers() {
        viewModel.saveSendTransactionStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                loadingAlert.dismissWithAnimation()
                errorAlert.show()
            },
            onLoading = {
                sentAlertDialog.show()
            }
        ) {
            loadingAlert.dismissWithAnimation()
            sentAlertDialog.show()
        })
    }

    @SuppressLint("SetTextI18n")
    private fun subscribeToUserProfileObserver() {
        viewModel.userProfile.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
            },
            onLoading = {
            }
        ) { profile ->
            phone = profile.userPhoneNum!!
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
            viewModel.saveTransaction(receipt, amount, phone)
            sentAlertDialog.dismissWithAnimation()
            successAlert.show()
        }
    }

    override fun sendFailed(cause: String) {
        requireActivity().runOnUiThread {
            Toast.makeText(
                requireContext(), "PaymentRepository Failed\n" +
                        "Reason: $cause", Toast.LENGTH_LONG
            ).show()
            sentAlertDialog.dismissWithAnimation()
            errorAlert.show()
        }
    }
}