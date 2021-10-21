package com.kanyideveloper.savingszetu.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kanyideveloper.savingszetu.databinding.TransactionsRowBinding
import com.kanyideveloper.savingszetu.model.Transaction
import java.text.SimpleDateFormat
import java.util.*


class HistoryAdapter :
    ListAdapter<Transaction, HistoryAdapter.MyViewHolder>(CHARACTER_COMPARATOR) {

    inner class MyViewHolder(private val binding: TransactionsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")

        fun bind(transaction: Transaction?) {
            binding.transDate.text = formatDate(transaction?.transactionDate!!)

            val rounded = String.format("%.2f", transaction?.transactionAmount?.toDouble())

            binding.transAmount.text = "KSH.$rounded"
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
            TransactionsRowBinding.inflate(
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