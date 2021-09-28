package com.kanyideveloper.savingszetu.model

import com.kanyideveloper.savingszetu.utils.Constants.CONSTANT_PROFILE_PIC_URL

data class User(
    val uid: String? = "",
    val email: String? = "",
    val username: String?  = "",
    val regNo: String? = "",
    val profilePictureUrl: String? = CONSTANT_PROFILE_PIC_URL
)
