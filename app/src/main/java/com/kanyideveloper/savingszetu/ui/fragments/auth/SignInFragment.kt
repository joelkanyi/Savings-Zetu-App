package com.kanyideveloper.savingszetu.ui.fragments.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.databinding.FragmentSignInBinding
import com.kanyideveloper.savingszetu.ui.activities.MainActivity

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.buttonSignIn.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }

        binding.textViewDontHaveAcc.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        return binding.root
    }
}