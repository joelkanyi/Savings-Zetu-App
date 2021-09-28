package com.kanyideveloper.savingszetu.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.databinding.FragmentSignUpBinding
import com.kanyideveloper.savingszetu.utils.EventObserver
import com.kanyideveloper.savingszetu.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        subscribeToObservers()

        binding.buttonSignUp.setOnClickListener {
            //findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
            viewModel.registerUser(
                binding.editTextTextEmail.text.toString(),
                binding.editTextTextFullName.text.toString(),
                binding.editTextTextRegNo.text.toString(),
                binding.editTextTextPass.text.toString(),
                binding.editTextTextPass.text.toString()
            )
        }

        binding.textViewHaveAcc.setOnClickListener {
            if (findNavController().previousBackStackEntry != null) {
                findNavController().popBackStack()
            } else findNavController()
                .navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        return binding.root
    }

    private fun subscribeToObservers() {
        viewModel.registerStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                binding.registerProgressbar.isVisible = true
                showSnackbar(it)
            },
            onLoading = { binding.registerProgressbar.isVisible = true }
        ) {
            binding.registerProgressbar.isVisible = false
            showSnackbar("Registration successful")
        })
    }
}