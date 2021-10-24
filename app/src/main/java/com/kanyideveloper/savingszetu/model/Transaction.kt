package com.kanyideveloper.savingszetu.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Transaction(
    val transactionId: String? = null,
    val transactionCode: String? = null,
    val transactionAmount: String? = null,
    val transactionDate: Long? = null,
    val transactionSender: String? = null,
    val userUID: String? = null,
    val sendName: String? = null
) : Parcelable
