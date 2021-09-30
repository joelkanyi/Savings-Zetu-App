package com.kanyideveloper.savingszetu.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanyideveloper.savingszetu.data.MainRepository
import com.kanyideveloper.savingszetu.utils.Event
import com.kanyideveloper.savingszetu.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _saveTransactionStatus = MutableLiveData<Event<Resource<Any>>>()
    val saveTransactionStatus: LiveData<Event<Resource<Any>>> = _saveTransactionStatus

    private val _currentNumber = MutableLiveData<String>("")
    val currentNumber: LiveData<String> = _currentNumber

    fun saveTransaction(
        code: String,
        amount: String,
        sender: String
    ){
        _saveTransactionStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = mainRepository.saveTransactionToDB(code, amount, sender)
            _saveTransactionStatus.postValue(Event(result))
        }
    }

    fun keyClicked(number: String){
        _currentNumber.postValue(StringBuilder().append(_currentNumber.value).append(number).toString())
    }

    /*
    private fun onNumberButtonClick(number: String, isHistory: Boolean = false) {

        var currentValue: String = textViewCurrentNumber.text.toString()
        // In Kotlin there is no more conditional operator ? : like it is in Java, which is used as a shortcut for setting a single variable to one of two states based on a single condition. Here everything can be conveniently done using if..else statement.
        // In Kotlin, using the equality operator == will call the equals method behind the scenes, so it's totaly acceptable to use it for string comparision.
        currentValue = if (currentValue == ZERO || isFutureOperationButtonClicked || isInstantOperationButtonClicked || isEqualButtonClicked || isHistory) number else StringBuilder().append(currentValue).append(number).toString()

        try {
            currentNumber = formatStringToDouble(currentValue)
        } catch (e: ParseException) {
            throw IllegalArgumentException("String must be number.")
        }

        textViewCurrentNumber.text = currentValue

        if (isEqualButtonClicked) {
            currentOperation = INIT
            historyText = ""
        }

        if (isInstantOperationButtonClicked) {
            historyInstantOperationText = ""
            textViewHistoryText.text = StringBuilder().append(historyText).append(currentOperation).toString()
            isInstantOperationButtonClicked = false
        }
    }
     */
}