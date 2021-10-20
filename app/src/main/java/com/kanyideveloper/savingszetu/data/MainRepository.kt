package com.kanyideveloper.savingszetu.data

import android.net.Uri
import com.kanyideveloper.savingszetu.model.Transaction
import com.kanyideveloper.savingszetu.model.UserPayment
import com.kanyideveloper.savingszetu.utils.Resource


interface MainRepository {
    suspend fun saveTransactionToDB(
        code: String, amount: String, sender: String): Resource<Any>

    suspend fun uploadProfilePicture(uri: Uri): Resource<Any>

    suspend fun pay(phone: String, amount: String): Resource<Any>

    suspend fun getUserTransactions(): Resource<List<Transaction>>

    suspend fun getAdminTransactions(): Resource<List<Transaction>>

    suspend fun getUserCurrentPayment(): Resource<UserPayment>
}