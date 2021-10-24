package com.kanyideveloper.savingszetu.ui.fragments.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kanyideveloper.savingszetu.databinding.FragmentForgotPasswordBinding
import android.widget.Toast

import androidx.core.content.ContentProviderCompat.requireContext

import com.google.firebase.auth.FirebaseAuth

import android.util.Patterns

import android.text.TextUtils
import androidx.core.content.ContentProviderCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.utils.Utils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.kanyideveloper.savingszetu.databinding.ForgotPasswordBinding
import com.kanyideveloper.savingszetu.ui.activities.MainActivity
import com.kanyideveloper.savingszetu.utils.EventObserver
import com.kanyideveloper.savingszetu.utils.hideKeyboard
import com.kanyideveloper.savingszetu.utils.showSnackbar
import com.kanyideveloper.savingszetu.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.annotations.NotNull
import java.lang.Exception

@AndroidEntryPoint
class PasswordResetFragment : DialogFragment() {
    private lateinit var binding: ForgotPasswordBinding

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ForgotPasswordBinding.inflate(inflater, container, false)
        val view: View = binding.root

        subscribeToObserver()

        binding.dialogConfirm.setOnClickListener { v ->

            this.hideKeyboard()

            if (TextUtils.isEmpty(binding.userEmail.text.toString())) {
                binding.userEmail.error = "Required"
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.userEmail.text.toString())
                    .matches()
            ) {
                binding.userEmail.error = "Invalid email format"
                return@setOnClickListener
            }

            viewModel.forgotPassword(binding.userEmail.text.toString())
        }
        return view
    }

    private fun subscribeToObserver() {
        viewModel.forgotPassStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
                Toast.makeText(
                    requireContext(),
                    "An error has occurred, try again",
                    Toast.LENGTH_LONG
                ).show()
                dismiss()
            },
            onLoading = {
            }
        ) {
            dismiss()
            Toast.makeText(
                requireContext(),
                "A reset link has been sent to your email address",
                Toast.LENGTH_LONG
            ).show()
            showSnackbar("A reset link has been sent to your email address")
        })
    }
}