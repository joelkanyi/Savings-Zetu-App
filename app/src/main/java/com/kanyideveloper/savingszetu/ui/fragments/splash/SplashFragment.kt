package com.kanyideveloper.savingszetu.ui.fragments.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.databinding.FragmentSplashBinding
import com.kanyideveloper.savingszetu.ui.activities.MainActivity
import com.kanyideveloper.savingszetu.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        val view = binding.root

        viewModel.value.observe(viewLifecycleOwner, {
            if(it){
                findNavController().navigate(R.id.action_splashFragment_to_authsDashboardFragment)
            }else{
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        })
        viewModel.setValue()
        return view
    }
}