package com.kanyideveloper.savingszetu.data

import com.kanyideveloper.savingszetu.utils.Resource


interface MainRepository {
    suspend fun saveTransactionToDB(
        code: String, amount: String, sender: String): Resource<Any>
}