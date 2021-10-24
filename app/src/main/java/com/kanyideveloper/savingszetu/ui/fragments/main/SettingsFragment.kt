package com.kanyideveloper.savingszetu.ui.fragments.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.bumptech.glide.Glide
import com.kanyideveloper.savingszetu.R
import com.kanyideveloper.savingszetu.model.User
import com.kanyideveloper.savingszetu.utils.EventObserver
import com.kanyideveloper.savingszetu.utils.showSnackbar
import com.kanyideveloper.savingszetu.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: MainViewModel by viewModels()
    private var userName: String? = ""
    private var phone: String? = ""
    private var user: User? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToUserProfileObserver()

        val usernamePreference: EditTextPreference? = findPreference("username")
        val phoneNumPreference: EditTextPreference? = findPreference("phonenum")
        val passwordPreference: EditTextPreference? = findPreference("password")
        val ratePreference: Preference? = findPreference("rate")
        val updatesPreference: Preference? = findPreference("updates")

        usernamePreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                usernamePreference?.text = newValue.toString()
                viewModel.updateProfile(newValue.toString())
                Toast.makeText(requireContext(), "User Name Update", Toast.LENGTH_SHORT).show()
                false
            }

        phoneNumPreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                phoneNumPreference?.text = newValue.toString()
                viewModel.updatePhoneNumber(newValue.toString())
                Toast.makeText(requireContext(), "Phone Number Updated", Toast.LENGTH_SHORT).show()
                false
            }

        passwordPreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                passwordPreference?.text = newValue.toString()
                viewModel.sendPasswordResetLink(newValue.toString())
                Toast.makeText(
                    requireContext(),
                    "A password Reset Link has been sent to your Email Address",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }

        ratePreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            Toast.makeText(requireContext(), "App not yet on PlayStore", Toast.LENGTH_SHORT).show()
            false
        }

        updatesPreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            Toast.makeText(requireContext(), "Your App Version is Up-to-Date", Toast.LENGTH_SHORT)
                .show()
            false
        }
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
            user = profile
            userName = profile.username
            phone = profile.userPhoneNum
        })
    }
}