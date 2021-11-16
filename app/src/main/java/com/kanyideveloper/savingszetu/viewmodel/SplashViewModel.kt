package com.kanyideveloper.savingszetu.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SplashViewModel: ViewModel() {

    var value: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun setValue(){
        Handler().postDelayed({
            if (FirebaseAuth.getInstance().currentUser != null) {
                value.value = false;
            } else {
                value.value = true
            }
        }, 3000)
    }
}