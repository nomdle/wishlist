package com.nomdle.wishlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_wish_list.*

class WishListActivity : AppCompatActivity() {

    private val LOGTAG = this::class.java.simpleName
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOGTAG, "main activity start")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_wish_list)
        showList()

        btnAdd.setOnClickListener {
            var intent = Intent(applicationContext, WishItemAddAcitviry::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        showList()
    }

    private fun showList() {
        dbHelper = DbHelper(this)

        val adapter = WishItemAdapter(dbHelper.selectAllItems())
        viewWishList.adapter = adapter
    }
}
