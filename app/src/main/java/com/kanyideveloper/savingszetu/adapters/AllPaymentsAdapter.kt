package com.kanyideveloper.savingszetu.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kanyideveloper.savingszetu.databinding.PaymentsDoneRowBinding
import com.kanyideveloper.savingszetu.model.Transaction
import com.kanyideveloper.savingszetu.utils.formatDate

class AllPaymentsAdapter : ListAdapter<Transaction, AllPaymentsAdapter.MyViewHolder>(MyDiffUtil) {

    object MyDiffUtil : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.transactionId == newItem.transactionId
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }

    }

    inner class MyViewHolder(private val binding: PaymentsDoneRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction?) {
            binding.textViewAdmDName.text = transaction?.sendName
            binding.textViewAdmDAmount.text = transaction?.transactionAmount
            binding.textViewAdmDDate.text = formatDate(transaction?.transactionDate!!)
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
}