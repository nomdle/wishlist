package com.nomdle.wishlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WishItemAdapter(private val itemList: List<WishItem>) :
    RecyclerView.Adapter<WishItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishItemViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_wish_item, parent, false)
        return WishItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: WishItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemView.setOnClickListener {
            // Toast.makeText(holder.itemView.context, item.subject, Toast.LENGTH_SHORT).show()
            val intent = Intent(holder.itemView.context, WishItemAddAcitviry::class.java)
            intent.putExtra("pos", item.position)
            holder.itemView.context.startActivity(intent)
        }
        holder.apply { bind(item) }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}