package com.kanyideveloper.savingszetu.model

data class Transaction(
    val transactionId: String? = null,
    val transactionCode: String? = null,
    val transactionAmount: String? = null,
    val transactionDate: String? = null,
    val transactionSender: String? = null,
    val userUID: String? = null
)
