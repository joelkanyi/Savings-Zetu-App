package com.kanyideveloper.savingszetu.ui.fragments.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.databinding.FragmentSignInBinding
import com.kanyideveloper.savingszetu.ui.activities.MainActivity
import com.kanyideveloper.savingszetu.utils.EventObserver
import com.kanyideveloper.savingszetu.utils.hideKeyboard
import com.kanyideveloper.savingszetu.utils.showSnackbar
import com.kanyideveloper.savingszetu.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        subscribeToObserver()

        binding.buttonSignIn.setOnClickListener {
            viewModel.loginUser(
                binding.editTextEmail.editText?.text.toString(),
                binding.editTextPassword.editText?.text.toString()
            )
            this.hideKeyboard()
        }

        binding.textViewDontHaveAcc.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.textViewForgotPassword.setOnClickListener {
            val passwordResetFragment = PasswordResetFragment()
            passwordResetFragment.show(childFragmentManager, "Forgot Pass Dialog")
        }

        return binding.root
    }

    private fun subscribeToObserver() {
        viewModel.loginStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
                binding.loginProgressBar.isVisible = false
            },
            onLoading = {
                binding.loginProgressBar.isVisible = true
            }
        ) {
            binding.loginProgressBar.isVisible = false
            showSnackbar("Logged in successfully")
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        })
    }
}