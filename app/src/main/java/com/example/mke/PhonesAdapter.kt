package com.example.mke

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhonesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var mPhonesList: ArrayList<PhoneModel> = ArrayList()

    fun setupPhones(phonesList: Array<PhoneModel>){
        mPhonesList.clear()
        mPhonesList.addAll(phonesList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PhonesViewHolder){
            holder.bind(mPhones = mPhonesList[position])
        }
    }

    override fun getItemCount(): Int {
        return mPhonesList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.recyclerview_item, parent, false)
        return PhonesViewHolder(itemView)
    }

    class PhonesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


        fun bind(mPhones: PhoneModel){
            val tvPhoneName = itemView.findViewById<TextView>(R.id.tv_phone_name)
            val tvPrice = itemView.findViewById<TextView>(R.id.tv_price)
            val tvDate = itemView.findViewById<TextView>(R.id.tv_date)
            val tvScore = itemView.findViewById<TextView>(R.id.tv_score)

            tvPhoneName.text = mPhones.name
            tvPrice.text = mPhones.price
            tvDate.text = mPhones.date
            tvScore.text = mPhones.score
        }
    }
}