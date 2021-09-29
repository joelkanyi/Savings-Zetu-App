package com.kanyideveloper.savingszetu.ui.fragments.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.databinding.FragmentUserProfileBinding
import com.kanyideveloper.savingszetu.ui.activities.AuthActivity

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.textViewLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
            requireActivity().finish()
        }

        return view
    }
}