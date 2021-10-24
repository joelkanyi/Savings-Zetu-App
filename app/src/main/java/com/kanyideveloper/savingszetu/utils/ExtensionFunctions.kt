package com.kanyideveloper.savingszetu.utils

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*

fun Fragment.showSnackbar(text: String) {
    Snackbar.make(
        requireView(),
        text,
        Snackbar.LENGTH_LONG
    ).show()
}

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Unknown Error Occurred")
    }
}

fun Fragment.hideKeyboard(): Boolean {
    return (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow((activity?.currentFocus ?: View(context)).windowToken, 0)
}

@SuppressLint("SimpleDateFormat")
fun formatDate(timestamp: Long): String {
    val df1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
/*            val timeZone = TimeZone.getDefault()
            df1.timeZone = timeZone*/
    val result = Date(timestamp)
    val startCalendar = Calendar.getInstance()
    startCalendar.time = result
    val format = SimpleDateFormat("EEEE, MMMM d, yyyy 'at' hh:mm a")

    return format.format(startCalendar.time)
}