package com.nomdle.wishlist

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
// kotlinxのためbuild.gradleにpluginとdependenciesを記載
import kotlinx.android.synthetic.main.layout_wish_item.view.*

class WishItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    var view: View = v

    fun bind(item: WishItem) {
        if (item.type == DBContract.Type.Priority.value) {
            view.viewBack.setBackgroundColor(Color.parseColor("#FF9999"))
        } else {
            view.viewBack.setBackgroundColor(Color.parseColor("#AA9999"))
        }
        view.viewSubject.text = item.subject
    }
}