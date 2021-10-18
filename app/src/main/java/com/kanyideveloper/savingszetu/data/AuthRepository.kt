package com.kanyideveloper.savingszetu.data

import com.google.firebase.auth.AuthResult
import com.kanyideveloper.savingszetu.utils.Resource

interface AuthRepository {
     suspend fun register(email: String, userName: String, regNo: String, password: String) : Resource<AuthResult>
     suspend fun login(email: String, password: String) : Resource<AuthResult>
}