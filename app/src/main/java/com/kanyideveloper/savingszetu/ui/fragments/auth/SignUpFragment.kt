package com.kanyideveloper.savingszetu.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.databinding.FragmentSignUpBinding
import com.kanyideveloper.savingszetu.utils.EventObserver
import com.kanyideveloper.savingszetu.utils.hideKeyboard
import com.kanyideveloper.savingszetu.utils.showSnackbar
import com.kanyideveloper.savingszetu.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        subscribeToObservers()

        binding.buttonSignUp.setOnClickListener {
            //findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            viewModel.registerUser(
                binding.editTextTextEmail.editText?.text.toString(),
                binding.editTextTextFullName.editText?.text.toString(),
                binding.editTextTextRegNo.editText?.text.toString(),
                binding.editTextTextPass.editText?.text.toString(),
                binding.editTextPhoneNumber.editText?.text.toString()
            )
            this.hideKeyboard()
        }

        binding.textViewHaveAcc.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        return binding.root
    }

    private fun subscribeToObservers() {
        viewModel.registerStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                binding.registerProgressbar.isVisible = false
                showSnackbar(it)
            },
            onLoading = { binding.registerProgressbar.isVisible = true }
        ) {
            binding.registerProgressbar.isVisible = false
            Toast.makeText(requireContext(), "A verification link has been sent to your email, please verify", Toast.LENGTH_SHORT).show()
            showSnackbar("Registration successful")
        })
    }
}