package com.kanyideveloper.savingszetu.ui.fragments.auth

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.kanyideveloper.savingszetu.data.AuthRepository
import com.kanyideveloper.savingszetu.utils.Constants.MAX_REG_NO_LENGTH
import com.kanyideveloper.savingszetu.utils.Constants.MIN_PASSWORD_LENGTH
import com.kanyideveloper.savingszetu.utils.Event
import com.kanyideveloper.savingszetu.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val applicationContext: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _registerStatus = MutableLiveData<Event<Resource<AuthResult>>>()
    val registerStatus: LiveData<Event<Resource<AuthResult>>>
        get() = _registerStatus

    fun registerUser(
        email: String,
        username: String,
        regNo: String,
        password: String,
        repeatedPassword: String
    ) {
        var error = if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            "Empty Strings"
        } else if (password != repeatedPassword) {
            "Passwords Incorrect"
        } else if (regNo.length != MAX_REG_NO_LENGTH) {
            "Invalid Registration Number"
        } else if (password.length < MIN_PASSWORD_LENGTH || repeatedPassword.length < MIN_PASSWORD_LENGTH) {
            "Password to short"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Not a valid Email"
        } else null

        error?.let {
            _registerStatus.postValue(Event(Resource.Error(it)))
            return
        }
        _registerStatus.postValue(Event(Resource.Loading()))

        viewModelScope.launch(dispatcher) {
            val result = authRepository.register(email, username, regNo, password)
            _registerStatus.postValue(Event(result))
        }
    }
}