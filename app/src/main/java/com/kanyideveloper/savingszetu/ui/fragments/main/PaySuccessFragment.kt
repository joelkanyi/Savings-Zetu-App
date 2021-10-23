package com.kanyideveloper.savingszetu.ui.fragments.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.databinding.FragmentPaySuccessBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaySuccessFragment : Fragment() {

    private lateinit var binding: FragmentPaySuccessBinding

    private val args: PaySuccessFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaySuccessBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.textViewAmountSent.text = "KSH.${args.payment.transactionAmount}"
        binding.textViewPaymentDate.text = args.payment.sendName
        binding.textViewTransactionCode.text = args.payment.transactionCode

        binding.button3.setOnClickListener {
            findNavController().navigate(R.id.action_paySuccessFragment_to_homeFragment)
        }

        return view
    }
}