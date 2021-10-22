package com.kanyideveloper.savingszetu.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.kanyideveloper.savingszetu.data.AuthRepository
import com.kanyideveloper.savingszetu.utils.Constants.MAX_REG_NO_LENGTH
import com.kanyideveloper.savingszetu.utils.Constants.MIN_PASSWORD_LENGTH
import com.kanyideveloper.savingszetu.utils.Constants.MIN_PHONE_LENGTH
import com.kanyideveloper.savingszetu.utils.Event
import com.kanyideveloper.savingszetu.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _registerStatus = MutableLiveData<Event<Resource<AuthResult>>>()
    val registerStatus: LiveData<Event<Resource<AuthResult>>>
        get() = _registerStatus

    private val _loginStatus = MutableLiveData<Event<Resource<AuthResult>>>()
    val loginStatus: LiveData<Event<Resource<AuthResult>>>
        get() = _loginStatus

    private val _forgotPassStatus = MutableLiveData<Event<Resource<Any>>>()
    val forgotPassStatus: LiveData<Event<Resource<Any>>>
        get() = _forgotPassStatus

    fun registerUser(
        email: String,
        username: String,
        regNo: String,
        password: String,
        phoneNum: String
    ) {
        var error = if (email.isEmpty() || username.isEmpty() || password.isEmpty() || phoneNum.isEmpty()) {
            "Empty Strings"
        } else if (regNo.length != MAX_REG_NO_LENGTH) {
            "Invalid Registration Number"
        } else if (password.length < MIN_PASSWORD_LENGTH) {
            "Password to short"
        } else if (phoneNum.length < MIN_PHONE_LENGTH) {
            "Phone to short"
        } else if (phoneNum.length > MIN_PHONE_LENGTH) {
            "Phone to long"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "Not a valid Email"
        } else null

        error?.let {
            _registerStatus.postValue(Event(Resource.Error(it)))
            return
        }
        _registerStatus.postValue(Event(Resource.Loading()))

        viewModelScope.launch(dispatcher) {
            val result = authRepository.register(email, username, regNo, password, phoneNum)
            _registerStatus.postValue(Event(result))
        }
    }

    fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _loginStatus.postValue(Event(Resource.Error("Empty Strings")))
        } else {
            _loginStatus.postValue(Event(Resource.Loading()))
            viewModelScope.launch(dispatcher) {
                val result = authRepository.login(email, password)
                _loginStatus.postValue(Event(result))
            }
        }
    }

    fun forgotPassword(email: String){
        if (email.isEmpty()){
            _forgotPassStatus.postValue(Event(Resource.Error("No Email Entered")))
        }else{
            _forgotPassStatus.postValue(Event(Resource.Loading()))
            viewModelScope.launch(dispatcher) {
                val result = authRepository.forgotPassword(email)
                _forgotPassStatus.postValue(Event(result))
            }
        }
    }
}