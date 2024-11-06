package com.example.testapppayments.model.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapppayments.R
import com.example.testapppayments.model.responseModel.ResponsePayments

class PaymentsAdapter: RecyclerView.Adapter<PaymentsAdapter.PaymentsViewHolder>() {

    private var listPayments = emptyList <ResponsePayments?> ()

    class PaymentsViewHolder(view:View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_payments,parent,false)
        return PaymentsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPayments.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PaymentsViewHolder, position: Int) {

        val titlePayment = holder.itemView.findViewById<TextView>(R.id.id_item_payment_tv_title)
        val amountPayment = holder.itemView.findViewById<TextView>(R.id.id_item_payment_tv_amount)
        val createdPayment = holder.itemView.findViewById<TextView>(R.id.id_item_payment_tv_created)
        val idPayment = holder.itemView.findViewById<TextView>(R.id.id_item_payment_tv_id)

        titlePayment.text = listPayments[position]!!.title
        idPayment.text = "id : ${listPayments[position]!!.id}"

        if(listPayments[position]!!.amount!=null && listPayments[position]!!.amount!=""){
            amountPayment.text = "amount : ${listPayments[position]!!.amount}"
        }else{
            amountPayment.text = "amount: -"
        }

        if(listPayments[position]!!.created!=null && listPayments[position]!!.created!=""){
            createdPayment.text = "created : ${listPayments[position]!!.created}"
        }else{
            createdPayment.text = "created : -"
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<ResponsePayments?>?){
        list?.let {
            listPayments = it
            notifyDataSetChanged()
        }
    }

}