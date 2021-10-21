package com.kanyideveloper.savingszetu.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kanyideveloper.savingszetu.databinding.PaymentsDoneRowBinding
import com.kanyideveloper.savingszetu.model.Transaction
import java.text.SimpleDateFormat
import java.util.*


class AdminTransacAdapter :
    ListAdapter<Transaction, AdminTransacAdapter.MyViewHolder>(CHARACTER_COMPARATOR) {

    inner class MyViewHolder(private val binding: PaymentsDoneRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")

        fun bind(transaction: Transaction?) {
            binding.textViewAdmDName.text = transaction?.sendName!!
            binding.textViewAdmDDate.text = formatDate(transaction.transactionDate!!)
            binding.textViewAdmDAmount.text = "KSH.${String.format("%.2f", transaction.transactionAmount?.toDouble())}"
        }

        @SuppressLint("SimpleDateFormat")
        fun formatDate(timestamp: Long): String {
            val df1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
/*            val timeZone = TimeZone.getDefault()
            df1.timeZone = timeZone*/
            val result = Date(timestamp)
            val startCalendar = Calendar.getInstance()
            startCalendar.time = result
            val format = SimpleDateFormat("EEEE, MMMM d, yyyy")

            return format.format(startCalendar.time)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            PaymentsDoneRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction)
    }

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return oldItem.transactionId == newItem.transactionId
            }

            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return oldItem == newItem
            }
        }
    }
}