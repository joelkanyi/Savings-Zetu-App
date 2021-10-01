package com.kanyideveloper.savingszetu.data

import android.net.Uri
import com.kanyideveloper.savingszetu.utils.Resource


interface MainRepository {
    suspend fun saveTransactionToDB(
        code: String, amount: String, sender: String): Resource<Any>

    suspend fun uploadProfilePicture(uri: Uri): Resource<Any>
}