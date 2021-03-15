package com.nomdle.wishlist

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_wish_item_add.*

class WishItemAddAcitviry() : AppCompatActivity() {
    private val LOGTAG = this::class.java.simpleName
    private lateinit var dbHelper: DbHelper
    private var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOGTAG, "main add start")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_wish_item_add)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val pos = intent.extras?.getInt("pos")
        if (pos != null) {
            dbHelper = DbHelper(this)
            val item = dbHelper.selectItem(pos)
            if (item != null) {
                position = item.position
                inputSubject.setText(item.subject)
                checkPriority.isChecked = (item.type == DBContract.Type.Priority.value)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(LOGTAG, "item:" + item.itemId)
        Log.d(LOGTAG, "R.id:" + android.R.id.home)
        if (item.itemId == android.R.id.home) {
            if (!inputSubject.text.trim().isEmpty()) {

                dbHelper = DbHelper(this)
                val subject = inputSubject.text.toString()
                val type = if (checkPriority.isChecked) DBContract.Type.Priority else DBContract.Type.Normal
                Log.d(LOGTAG, "subject:" + subject)
                Log.d(LOGTAG, "type:" + type)
                if (position > -1) {
                    dbHelper.updateItem(position, subject, type, null, DBContract.Status.Active)
                } else {
                    val pos = dbHelper.getItemCount() + 1
                    dbHelper.insertItem(pos, subject, type, null, DBContract.Status.Active)
                }
            }
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}