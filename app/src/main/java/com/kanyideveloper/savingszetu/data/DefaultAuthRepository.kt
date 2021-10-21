package com.kanyideveloper.savingszetu.data

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.kanyideveloper.savingszetu.model.User
import com.kanyideveloper.savingszetu.model.UserPayment
import com.kanyideveloper.savingszetu.utils.Resource
import com.kanyideveloper.savingszetu.utils.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DefaultAuthRepository : AuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().getReference("users")

    override suspend fun register(email: String, userName: String, regNo: String, password: String, phoneNum: String): Resource<AuthResult> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                val uid = result.user?.uid!!
                val user = User(uid,email,userName,regNo,phoneNum)
                //val user = User(uid,email,userName,regNo,phoneNum)
                databaseReference.child(uid).setValue(user).await()
                val transaction = UserPayment("0","0","0")
                databaseReference.child(uid).child("current_payment_details").setValue(transaction)
                Resource.Success(result)
            }
        }
    }

    override suspend fun login(email: String, password: String): Resource<AuthResult> {
        return withContext(Dispatchers.IO){
            safeCall {
                val result = firebaseAuth.signInWithEmailAndPassword(email,password).await()
                Resource.Success(result)
            }
        }
    }
}