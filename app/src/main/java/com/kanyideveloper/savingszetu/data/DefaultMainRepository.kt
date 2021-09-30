package com.kanyideveloper.savingszetu.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.kanyideveloper.savingszetu.model.Transaction
import com.kanyideveloper.savingszetu.utils.Resource
import com.kanyideveloper.savingszetu.utils.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class DefaultMainRepository : MainRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().reference
    override suspend fun saveTransactionToDB(
        code: String,
        amount: String,
        sender: String
    ): Resource<Any> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val uid = firebaseAuth.uid!!
                val transactionId = UUID.randomUUID().toString()
                val transaction = Transaction(
                    transactionId = transactionId,
                    transactionCode = code,
                    transactionAmount = amount,
                    transactionDate = System.currentTimeMillis().toString(),
                    transactionSender = sender
                )
                databaseReference.child("transactions").child(uid).child(transactionId)
                    .setValue(transaction).await()
                Resource.Success(Any())
            }
        }
    }
}