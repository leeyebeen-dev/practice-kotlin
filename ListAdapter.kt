package com.example.bustame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(val itemList: ArrayList<ListLayout>): RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        holder.busNumber.text = itemList[position].busNumber
        holder.busStopId.text = itemList[position].busStopId
        holder.customerType.text = itemList[position].customerType
        holder.id.text = itemList[position].id
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val busNumber: TextView = itemView.findViewById(R.id.tv_busNumber)
        val busStopId: TextView = itemView.findViewById(R.id.tv_busstopId)
        val customerType: TextView = itemView.findViewById(R.id.tv_customerType)
        val id: TextView = itemView.findViewById(R.id.tv_id)
    }
}