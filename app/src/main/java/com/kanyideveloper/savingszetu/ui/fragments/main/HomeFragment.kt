package com.kanyideveloper.savingszetu.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.databinding.FragmentHomeBinding
import de.hdodenhof.circleimageview.CircleImageView

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        val userImage: CircleImageView = view.findViewById(R.id.profile_image)
        val notif: ImageView = view.findViewById(R.id.imageView3)

        userImage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_userProfileFragment)
        }

        notif.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_notificationsFragment)
        }

        binding.cardView2.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_payFragment)
        }

        binding.cardView3.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_statisticsFragment)
        }

        binding.cardView4.setOnClickListener {

        }

        binding.cardView5.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_adminFragment)
        }

        binding.imageView8.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_historyFragment)
        }


        return view
    }
}