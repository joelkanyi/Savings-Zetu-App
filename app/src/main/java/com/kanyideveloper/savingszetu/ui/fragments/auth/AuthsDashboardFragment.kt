package com.kanyideveloper.savingszetu.ui.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.databinding.FragmentAuthsDashboardBinding


class AuthsDashboardFragment : Fragment() {

    private lateinit var binding: FragmentAuthsDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthsDashboardBinding.inflate(inflater, container, false)

        binding.buttonSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_authsDashboardFragment_to_signInFragment)
        }

        binding.buttonGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_authsDashboardFragment_to_signUpFragment)
        }

        return binding.root
    }
}