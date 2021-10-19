package com.kanyideveloper.savingszetu.data

import android.net.Uri
import com.androidstudy.daraja.Daraja
import com.androidstudy.daraja.DarajaListener
import com.androidstudy.daraja.model.AccessToken
import com.androidstudy.daraja.model.LNMExpress
import com.androidstudy.daraja.model.LNMResult
import com.androidstudy.daraja.util.Env
import com.androidstudy.daraja.util.TransactionType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.ktx.storage
import com.kanyideveloper.savingszetu.model.Transaction
import com.kanyideveloper.savingszetu.utils.MpesaListener
import com.kanyideveloper.savingszetu.utils.Resource
import com.kanyideveloper.savingszetu.utils.TransactionConstants
import com.kanyideveloper.savingszetu.utils.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*

class DefaultMainRepository : MainRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val firebaseStorage = Firebase.storage

    private val daraja: Daraja = Daraja.with(
    TransactionConstants.CONSUMER_KEY, TransactionConstants.CONSUMER_SECRET, Env.SANDBOX,
    object : DarajaListener<AccessToken> {
        override fun onResult(result: AccessToken) {
            Timber.d("Access Token: ${result.access_token}")
        }

        override fun onError(error: String?) {
            Timber.d("Error: $error")
        }

    })

    override suspend fun pay(phone: String, amount: String){
        val lnmExpress = LNMExpress(
            "174379",
            "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
            TransactionType.CustomerPayBillOnline,
            amount,
            phone,
            "174379",
            phone,
            "https://us-central1-savingszetu.cloudfunctions.net/api/myCallbackUrl",
            "001ABC",
            "Goods PaymentRepository"
        )

        daraja.requestMPESAExpress(lnmExpress, object : DarajaListener<LNMResult> {
            override fun onResult(result: LNMResult) {
                Timber.d(result.ResponseDescription.toString())
                FirebaseMessaging.getInstance().subscribeToTopic(result.CheckoutRequestID.toString())
                //Resource.Success(result.ResponseDescription)
            }

            override fun onError(error: String?) {
                Timber.d(error.toString())
                //Resource.Error(error.toString())
            }
        })
    }


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