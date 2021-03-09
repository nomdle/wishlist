package com.nomdle.wishlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
// kotlinxのためbuild.gradleにpluginとdependenciesを記載
import kotlinx.android.synthetic.main.layout_wish_item.view.*

class WishItemViewHolder(v : View) : RecyclerView.ViewHolder(v) {
    var view : View = v

    fun bind (item : WishItem) {
        view.viewSubject.text = item.subject
    }
}