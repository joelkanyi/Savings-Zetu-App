package com.kanyideveloper.savingszetu.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kanyideveloper.savingszetu.databinding.NameRowBinding
import com.kanyideveloper.savingszetu.model.User


class PaymentAdapter :
    ListAdapter<User, PaymentAdapter.MyViewHolder>(CHARACTER_COMPARATOR) {

    inner class MyViewHolder(private val binding: NameRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(user: User?) {
            binding.textViewSZName.text = user?.username
            binding.textViewSZBalance.text = "Total Contribution: KSH.${
                String.format(
                    "%.2f",
                    user?.current_payment_details?.total_payed?.toDouble()
                )
            }"
            binding.textViewSZPending.text =
                "Pending: KSH.${String.format("%.2f", user?.current_payment_details?.pending?.toDouble())}"
            binding.textViewSZOverpay.text =
                "Overpay: KSH.${String.format("%.2f", user?.current_payment_details?.overpay?.toDouble())}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            NameRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}