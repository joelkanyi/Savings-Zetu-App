package com.kanyideveloper.savingszetu.model

import android.os.Parcelable
import com.kanyideveloper.savingszetu.utils.Constants.CONSTANT_PROFILE_PIC_URL
import kotlinx.parcelize.Parcelize

data class User(
    val uid: String? = "",
    val email: String? = "",
    val username: String?  = "",
    val regNo: String? = "",
    val userPhoneNum: String? = "",
    val profilePictureUrl: String? = CONSTANT_PROFILE_PIC_URL,
    val current_payment_details: UserPayment? = null
)
