package com.kanyideveloper.savingszetu.model

data class UserPayment(
    val overpay: String? = "",
    val pending: String? = "",
    val total_payed: String? = ""
)
