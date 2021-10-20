package com.kanyideveloper.savingszetu.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.databinding.FragmentHomeBinding
import com.kanyideveloper.savingszetu.utils.EventObserver
import com.kanyideveloper.savingszetu.utils.showSnackbar
import com.kanyideveloper.savingszetu.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: MainViewModel by  viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        subscribeToObserver()

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

    private fun subscribeToObserver() {
        viewModel.userCurrentTransactionDetails.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
                //binding.transactProgressbar.isVisible = false
            },
            onLoading = {
                //binding.transactProgressbar.isVisible = true
            }
        ) {details ->
            //binding.transactProgressbar.isVisible = false
            binding.textViewTotalContrib.text = String.format("%.2f", details.total_payed?.toDouble())
            binding.textViewPending.text = String.format("%.2f", details.pending?.toDouble())
            binding.textViewOverpay.text = String.format("%.2f", details.overpay?.toDouble())
        })
    }
}