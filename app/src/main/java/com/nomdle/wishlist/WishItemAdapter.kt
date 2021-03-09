package com.nomdle.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WishItemAdapter(val itemList : List<WishItem>): RecyclerView.Adapter<WishItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishItemViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.layout_wish_item,parent,false)
        return WishItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: WishItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.apply { bind(item) }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}