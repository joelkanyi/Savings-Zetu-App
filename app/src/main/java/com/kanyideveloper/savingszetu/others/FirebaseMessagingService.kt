package com.kanyideveloper.savingszetu.others

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.kanyideveloper.savingszetu.model.MpesaTransaction
import com.kanyideveloper.savingszetu.ui.fragments.main.PayFragment
import timber.log.Timber


class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Timber.d("onMessageReceived: " + remoteMessage.data)

        if (remoteMessage.data.isNotEmpty()) {
            val payload = remoteMessage.data["payload"]
            val gson = Gson()

            val mpesaResponse: MpesaTransaction =
                gson.fromJson(payload, MpesaTransaction::class.java)

            val id = mpesaResponse.body.stkCallback.checkoutRequestID

            if (mpesaResponse.body.stkCallback.resultCode != 0) {
                Timber.d("Operation Failed")
                val reason = mpesaResponse.body.stkCallback.resultDesc
                PayFragment.mpesaListener.sendFailed(reason)

            } else {
                Timber.d("Operation Success")
                val list: List<MpesaTransaction.Body.StkCallback.CallbackMetadata.Item> =
                    mpesaResponse.body.stkCallback.callbackMetadata.item

                var receipt = ""
                var date = ""
                var phone = ""
                var amount = ""

                list.forEach { item ->
                    if (item.name == "MpesaReceiptNumber") {
                        receipt = item.value
                    }
                    if (item.name == "TransactionDate") {
                        date = item.value
                    }
                    if (item.name == "PhoneNumber") {
                        phone = item.value
                    }
                    if (item.name == "Amount") {
                        amount = item.value
                    }
                }
                PayFragment.mpesaListener.sendSuccessful(amount, phone, getDate(date), receipt)
                Timber.d("\n" + "Receipt: " + receipt + "\n" + "Date: " + getDate(date) + "\n" + "Phone: " + phone + "\n" + "Amount: " + amount)
            }

            FirebaseMessaging.getInstance().unsubscribeFromTopic(id)
        }
    }

    private fun getDate(date: String): String {
        return "${date.subSequence(6, 8)} ${date.subSequence(4, 6)} ${
            date.subSequence(0, 4)
        } at ${date.subSequence(8, 10)} : ${date.subSequence(10, 12)} : ${
            date.subSequence(12, 14)
        }"
    }
}