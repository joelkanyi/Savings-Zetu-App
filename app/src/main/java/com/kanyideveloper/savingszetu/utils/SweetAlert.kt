package com.kanyideveloper.savingszetu.utils

import android.content.Context
import android.graphics.Color
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog

object SweetAlert {

    fun Context.showLoadingAlert() {
        val loadingAlert = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        loadingAlert.progressHelper.barColor = Color.parseColor("#A5DC86")
        loadingAlert.titleText = "Loading..."
        loadingAlert.setCancelable(false)
    }

    fun Context.showSuccessAlert() {
        val successAlert = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        successAlert.progressHelper.barColor = Color.parseColor("#A5DC86")
        successAlert.setConfirmClickListener { successAlert.dismissWithAnimation() }
        successAlert.setCancelable(false)
    }

    fun Context.showConfirmationAlert(){
        val confirmDialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Are you sure you want to request garbage collection?")
            .setCancelText("No")
            .setConfirmText("Yes")
            .showCancelButton(true)
            .setConfirmClickListener {
                this.showLoadingAlert()

                // Do some logic stuffs
                this.showSuccessAlert()
            }
            .setCancelClickListener {
                //dismiss the dialog
            }
            .show()
    }
}