package com.kanyideveloper.savingszetu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kanyideveloper.savingszetu.databinding.TransactionsRowBinding
import com.kanyideveloper.savingszetu.model.Transaction


class HistoryAdapter :
    ListAdapter<Transaction, HistoryAdapter.MyViewHolder>(CHARACTER_COMPARATOR) {

    inner class MyViewHolder(private val binding: TransactionsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(transaction: Transaction?) {
            binding.transDate.text = transaction?.transactionDate

            val rounded = String.format("%.2f", transaction?.transactionAmount?.toDouble())

            binding.transAmount.text = "KSH.$rounded"
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