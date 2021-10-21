package com.kanyideveloper.savingszetu.viewmodel

import android.annotation.SuppressLint
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanyideveloper.savingszetu.data.MainRepository
import com.kanyideveloper.savingszetu.model.Transaction
import com.kanyideveloper.savingszetu.model.User
import com.kanyideveloper.savingszetu.model.UserPayment
import com.kanyideveloper.savingszetu.utils.Event
import com.kanyideveloper.savingszetu.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _saveTransactionStatus = MutableLiveData<Event<Resource<Any>>>()
    val saveTransactionStatus: LiveData<Event<Resource<Any>>> = _saveTransactionStatus

    private val _saveSendTransactionStatus = MutableLiveData<Event<Resource<Any>>>()
    val saveSendTransactionStatus: LiveData<Event<Resource<Any>>> = _saveSendTransactionStatus

    private var _userTransactions = MutableLiveData<Event<Resource<List<Transaction>>>>()
    val userTransactions: LiveData<Event<Resource<List<Transaction>>>> = _userTransactions

    private var _userFourTransactions = MutableLiveData<Event<Resource<List<Transaction>>>>()
    val userFourTransactions: LiveData<Event<Resource<List<Transaction>>>> = _userFourTransactions

    private var _adminFourTransactions = MutableLiveData<Event<Resource<List<Transaction>>>>()
    val adminFourTransactions: LiveData<Event<Resource<List<Transaction>>>> = _adminFourTransactions

    private var _adminTransactions = MutableLiveData<Event<Resource<List<Transaction>>>>()
    val adminTransactions: LiveData<Event<Resource<List<Transaction>>>> = _adminTransactions

    private var _defaulter = MutableLiveData<Event<Resource<List<User>>>>()
    val defaulter: LiveData<Event<Resource<List<User>>>> = _defaulter

    private var _payers = MutableLiveData<Event<Resource<List<User>>>>()
    val payers: LiveData<Event<Resource<List<User>>>> = _payers

    private var _userProfile = MutableLiveData<Event<Resource<User>>>()
    val userProfile: LiveData<Event<Resource<User>>> = _userProfile

    private var _userCurrentTransactionDetails = MutableLiveData<Event<Resource<UserPayment>>>()
    val userCurrentTransactionDetails: LiveData<Event<Resource<UserPayment>>> =
        _userCurrentTransactionDetails


    init {
        getUserTransactions()
        getUserCurrentPayments()
        getFourUserTransactions()
        getUserProfile()
        getDefaulter()
        getThosePayed()
        getAdminUserTransactions()
        getAllAdminsTransactions()
    }

    private fun getAllAdminsTransactions(){
        _adminTransactions.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = mainRepository.getAllAdminsTransactions()
            _adminTransactions.postValue(Event(result))
        }
    }

    private fun getDefaulter() {
        _defaulter.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = mainRepository.getDefaulters()
            _defaulter.postValue(Event(result))
        }
    }

    private fun getThosePayed() {
        _payers.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = mainRepository.getThoseWhoHavePayed()
            _payers.postValue(Event(result))
        }
    }

    private fun getUserTransactions() {
        _userTransactions.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = mainRepository.getUserTransactions()
            _userTransactions.postValue(Event(result))
        }
    }

    private fun getUserProfile() {
        _userProfile.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = mainRepository.getCurrentUserProfile()
            _userProfile.postValue(Event(result))
        }
    }

    private fun getFourUserTransactions() {
        _userFourTransactions.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = mainRepository.getFourCurrentUserTransactions()
            _userFourTransactions.postValue(Event(result))
        }
    }

    private fun getAdminUserTransactions() {
        _adminFourTransactions.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = mainRepository.getFourAdminTransactions()
            _adminFourTransactions.postValue(Event(result))
        }
    }

    private fun getUserCurrentPayments() {
        _userCurrentTransactionDetails.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = mainRepository.getUserCurrentPayment()
            _userCurrentTransactionDetails.postValue(Event(result))
        }
    }

    private val _curImageUri = MutableLiveData<Uri>()
    val curImageUri: LiveData<Uri> = _curImageUri

    fun setCurImageUri(uri: Uri) {
        _curImageUri.postValue(uri)
    }

    private val _currentNumber = MutableLiveData<String>("0")
    val currentNumber: LiveData<String> = _currentNumber

    fun saveTransaction(code: String, amount: String, sender: String, senderName: String) {
        _saveTransactionStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = mainRepository.saveTransactionToDB(code, amount, sender, senderName)
            _saveTransactionStatus.postValue(Event(result))
        }
    }

    fun pay(phone: String, amount: String) {
        viewModelScope.launch(dispatcher) {
            val result = mainRepository.pay(phone, amount)
            _saveSendTransactionStatus.postValue(Event(result))
        }
    }

    fun keyClicked(number: String) {
        when {
            _currentNumber.value == "0" -> {
                _currentNumber.postValue(number)
            }
            _currentNumber.value!!.length >= 6 -> {
                return
            }
            else -> {
                _currentNumber.postValue(
                    StringBuilder().append(_currentNumber.value).append(number).toString()
                )
            }
        }
    }

    fun deleteChars() {
        when {
            _currentNumber.value == "" -> {
                _currentNumber.postValue("0")
                return
            }
            _currentNumber.value == "0" -> {
                return
            }
            _currentNumber.value!!.length == 1 -> {
                _currentNumber.postValue("0")
            }
            else -> {
                _currentNumber.postValue(_currentNumber.value!!.dropLast(1))
            }
        }
    }

    fun uploadProfileImage(currentImageUri: Uri?) {

    }
}