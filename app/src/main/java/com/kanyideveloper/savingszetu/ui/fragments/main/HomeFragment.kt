package com.kanyideveloper.savingszetu.ui.fragments.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.kanyideveloper.savingszetu.adapters.HistoryAdapter
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.databinding.FragmentHomeBinding
import com.kanyideveloper.savingszetu.utils.EventObserver
import com.kanyideveloper.savingszetu.utils.showSnackbar
import com.kanyideveloper.savingszetu.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var userName: TextView

    private val viewModel: MainViewModel by  viewModels()

    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    private lateinit var userImage: CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        subscribeToObserver()
        subscribeToTransactionsObserver()
        subscribeToUserProfileObserver()

        userImage = view.findViewById(R.id.profile_image)
        val notif: ImageView = view.findViewById(R.id.imageView3)
        userName = view.findViewById(R.id.textViewUserName)

        userImage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_userProfileFragment)
        }

        notif.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_notificationsFragment)
        }

        binding.cardView2.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_payFragment)
        }


        binding.cardView4.setOnClickListener {

        }

        binding.cardView5.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_adminFragment)
        }

        binding.imageView8.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_historyFragment)
        }

        binding.textView27.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_historyFragment)
        }


        return view
    }

    private fun subscribeToObserver() {
        viewModel.userCurrentTransactionDetails.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
                Timber.d(it)
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

    private fun subscribeToTransactionsObserver() {
        viewModel.userFourTransactions.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
                binding.fourTransactionsprogressBar.isVisible = false
            },
            onLoading = {
                binding.fourTransactionsprogressBar.isVisible = true
            }
        ) {transactions ->
            binding.fourTransactionsprogressBar.isVisible = false
            adapter.submitList(transactions)
            binding.fourTransactionsRecyclerView.adapter = adapter
        })
    }

    @SuppressLint("SetTextI18n")
    private fun subscribeToUserProfileObserver() {
        viewModel.userProfile.observe(viewLifecycleOwner, EventObserver(
            onError = {
                showSnackbar(it)
                //binding.fourTransactionsprogressBar.isVisible = false
            },
            onLoading = {
                //binding.fourTransactionsprogressBar.isVisible = true
            }
        ) { profile ->
            userName.text = "Hello ${profile.username?.substring(0, profile.username.indexOf(' '))},"
            Glide.with(userImage).load(profile.profilePictureUrl).centerCrop().into(userImage)

            if (profile.privilege.equals("Admin")){
                binding.cardView5.isVisible = true
            }
        })
    }
}