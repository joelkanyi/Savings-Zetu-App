package com.kanyideveloper.savingszetu.data

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
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
    private val firebaseStorage = Firebase.storage

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

    override suspend fun uploadProfilePicture(uri: Uri): Resource<Any> {
        return withContext(Dispatchers.IO){
            safeCall {
                val uid = firebaseAuth.uid!!
                val imageUploadResult = firebaseStorage.getReference(uid).putFile(uri).await()
                val imageUrl = imageUploadResult?.metadata?.reference?.downloadUrl?.await().toString()
                databaseReference.child(uid).child("profilePictureUrl").setValue(imageUrl)
                Resource.Success(Any())
            }
        }
    }
}