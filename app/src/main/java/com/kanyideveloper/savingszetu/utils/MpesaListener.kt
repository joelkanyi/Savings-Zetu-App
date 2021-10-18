package com.kanyideveloper.savingszetu.utils

interface MpesaListener {
    fun sendSuccessful(amount: String, phone: String, date: String, receipt: String)

    fun sendFailed(cause: String)
}