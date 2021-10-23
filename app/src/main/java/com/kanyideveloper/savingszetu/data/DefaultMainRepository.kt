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
import com.kanyideveloper.savingszetu.model.User
import com.kanyideveloper.savingszetu.model.UserPayment
import com.kanyideveloper.savingszetu.utils.Resource
import com.kanyideveloper.savingszetu.utils.TransactionConstants
import com.kanyideveloper.savingszetu.utils.safeCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

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

    override suspend fun pay(phone: String, amount: String): Resource<Any> {
        return withContext(Dispatchers.Main) {

            safeCall {
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

                val request =
                    daraja.requestMPESAExpress(lnmExpress, object : DarajaListener<LNMResult> {
                        override fun onResult(result: LNMResult) {
                            Timber.d(result.ResponseDescription.toString())
                            FirebaseMessaging.getInstance()
                                .subscribeToTopic(result.CheckoutRequestID.toString())
                        }

                        override fun onError(error: String?) {
                            Timber.d(error.toString())
                        }
                    })

                Resource.Success(request)
            }
        }
    }

    override suspend fun updateCurrentUserTransactionDetails(
        amountPayed: String
    ): Resource<Any> {
        return withContext(Dispatchers.IO) {
            safeCall {

                val user = getCurrentUserProfile()
                val paymentDet = user.data?.current_payment_details

                val uid = firebaseAuth.uid!!
                val currentPaymentRecords =
                    databaseReference.child("users").child(uid).child("current_payment_details")

                val details = currentPaymentRecords.get().await().getValue(UserPayment::class.java)
                    ?: throw IllegalArgumentException()

                var totalMoneyPayed = details.total_payed?.toDouble()
                var pendingMoney = details.pending?.toDouble()
                var overpayMoney = details.overpay?.toDouble()

                totalMoneyPayed = totalMoneyPayed!! + amountPayed.toDouble()
                pendingMoney = pendingMoney!! - amountPayed.toDouble()

                if (pendingMoney == 0.00) {
                    pendingMoney = 0.00
                }else if (pendingMoney < 0.00){
                    overpayMoney = overpayMoney!! + abs(pendingMoney)
                    pendingMoney = 0.00
                }else{
                    overpayMoney = 0.00
                }

                val paymentDetails = UserPayment(abs(overpayMoney!!).toString(), pendingMoney.toString(), totalMoneyPayed.toString())

                databaseReference.child("users").child(uid).child("current_payment_details").setValue(paymentDetails).await()

                Resource.Success(Any())
            }
        }
    }

    override suspend fun getUserTransactions(): Resource<List<Transaction>> {
        return withContext(Dispatchers.IO) {
            val uid = firebaseAuth.uid!!
            val transactsList = ArrayList<Transaction>()
            safeCall {
                val transactions = databaseReference.child("transactions")
                val transactionLists = transactions.child(uid).get().await()
                for (i in transactionLists.children) {
                    val result = i.getValue(Transaction::class.java)
                    transactsList.add(result!!)
                }
                Resource.Success(transactsList)
            }
        }
    }

    override suspend fun getFourCurrentUserTransactions(): Resource<List<Transaction>> {
        return withContext(Dispatchers.IO) {
            val uid = firebaseAuth.uid!!
            val transactsList = ArrayList<Transaction>()
            safeCall {
                val transactions = databaseReference.child("transactions")
                val transactionLists = transactions.child(uid).limitToFirst(4).get().await()
                for (i in transactionLists.children) {
                    val result = i.getValue(Transaction::class.java)
                    transactsList.add(result!!)
                }
                Resource.Success(transactsList)
            }
        }
    }

    override suspend fun getFourAdminTransactions(): Resource<List<Transaction>> {
        return withContext(Dispatchers.IO) {
            val transactsList = ArrayList<Transaction>()
            safeCall {
                val uid = firebaseAuth.uid!!
                val transactions = databaseReference.child("all_transactions").limitToFirst(4).get().await()
                for (i in transactions.children) {
                    val result = i.getValue(Transaction::class.java)
                    transactsList.add(result!!)
                }
                Resource.Success(transactsList)
            }
        }
    }

    override suspend fun getAllAdminsTransactions(): Resource<List<Transaction>> {
        return withContext(Dispatchers.IO) {
            val transactsList = ArrayList<Transaction>()
            safeCall {
                val uid = firebaseAuth.uid!!
                val transactions = databaseReference.child("all_transactions").get().await()
                for (i in transactions.children) {
                    val result = i.getValue(Transaction::class.java)
                    transactsList.add(result!!)
                }
                Resource.Success(transactsList)
            }
        }
    }


    override suspend fun getUserCurrentPayment(): Resource<UserPayment> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val uid = firebaseAuth.uid!!
                val currentPaymentRecords =
                    databaseReference.child("users").child(uid).child("current_payment_details")

                val details = currentPaymentRecords.get().await().getValue(UserPayment::class.java)
                    ?: throw IllegalArgumentException()

                Resource.Success(details)

            }
        }
    }

    override suspend fun getAllMoney(): Resource<String> {
        return withContext(Dispatchers.IO){
            safeCall {
                val allMoney = databaseReference.child("total_money").child("balance").get().await().value.toString()
                Resource.Success(allMoney)
            }
        }
    }

    override suspend fun getCurrentUserProfile(): Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val uid = firebaseAuth.uid!!
                val currentUserDetails = databaseReference.child("users").child(uid).get().await()
                    .getValue(User::class.java) ?: throw IllegalArgumentException()
                Resource.Success(currentUserDetails)
            }
        }
    }

    override suspend fun getDefaulters(): Resource<List<User>> {
        return withContext(Dispatchers.IO) {
            val defaultersList = ArrayList<User>()
            safeCall {
                val defaulters = databaseReference.child("users").get().await()

                for (i in defaulters.children) {
                    val result = i.getValue(User::class.java)
                    if(result?.current_payment_details?.total_payed?.toDouble()!! <= 0.0){
                        defaultersList.add(result)
                    }
                }
                Resource.Success(defaultersList)
            }
        }
    }

    override suspend fun getThoseWhoHavePayed(): Resource<List<User>> {
        return withContext(Dispatchers.IO) {
            val payersList = ArrayList<User>()
            safeCall {
                val payers = databaseReference.child("users").get().await()

                for (i in payers.children) {
                    val result = i.getValue(User::class.java)
                    if(result?.current_payment_details?.total_payed?.toDouble()!! >= 1){
                        payersList.add(result)
                    }
                }
                Resource.Success(payersList)
            }
        }
    }

    override suspend fun saveTransactionToDB(
        code: String,
        amount: String,
        sender: String,
        senderName: String
    ): Resource<Any> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val uid = firebaseAuth.uid!!
                val transactionId = UUID.randomUUID().toString()
                val transaction = Transaction(
                    transactionId = transactionId,
                    transactionCode = code,
                    transactionAmount = amount,
                    transactionDate = System.currentTimeMillis(),
                    transactionSender = sender,
                    uid,
                    senderName
                )
                databaseReference.child("transactions").child(uid).child(transactionId)
                    .setValue(transaction).await()
                databaseReference.child("all_transactions").child(transactionId)
                    .setValue(transaction).await()
                val money = getAllMoney().data?.toDouble()

                databaseReference.child("total_money").child("balance").setValue(money!! + amount.toDouble()).await()
                Resource.Success(Any())
            }
        }
    }

    override suspend fun uploadProfilePicture(uri: Uri): Resource<Any> {
        return withContext(Dispatchers.IO) {
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