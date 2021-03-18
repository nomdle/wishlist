package com.nomdle.wishlist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DbHelper(context: Context) : SQLiteOpenHelper(context, DB_FILE, null, DB_VERSION) {
    private val LOGTAG = this::class.java.simpleName
    private val dbDateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE)
        Log.d(LOGTAG, "table created")

        val values = ContentValues()
        values.put(DBContract.DBEntry.POSITION, 0)
        values.put(DBContract.DBEntry.SUBJECT, "Register what you want!")
        values.put(DBContract.DBEntry.TYPE, DBContract.Type.Priority.value)
        values.put(DBContract.DBEntry.STATUS, DBContract.Status.Active.value)
        values.put(DBContract.DBEntry.CREATED_DATE, getCurrentDate())
        db?.insert(DBContract.DBEntry.TABLE_NAME, null, values)
        Log.d(LOGTAG, "insert default record")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            Log.println(Log.DEBUG, "TODO", "for database update")
        }
    }

    fun selectItem(pos: Int): WishItem? {
        Log.d(LOGTAG, "select items pos=$pos")

        val db = readableDatabase
        var item: WishItem? = null

        val cursor = db?.rawQuery(
            "select * from " + DBContract.DBEntry.TABLE_NAME +
                    " where " + DBContract.DBEntry.POSITION + "=" + pos, null
        )
        if (cursor!!.moveToFirst()) {
            item = WishItem(
                cursor.getInt(cursor.getColumnIndex(DBContract.DBEntry.POSITION)),
                cursor.getString(cursor.getColumnIndex(DBContract.DBEntry.SUBJECT)),
                cursor.getInt(cursor.getColumnIndex(DBContract.DBEntry.TYPE)),
                cursor.getString(cursor.getColumnIndex(DBContract.DBEntry.DETAIL)),
                cursor.getInt(cursor.getColumnIndex(DBContract.DBEntry.STATUS)),
                cursor.getString(cursor.getColumnIndex(DBContract.DBEntry.CLOSED_DATE)),
                cursor.getString(cursor.getColumnIndex(DBContract.DBEntry.CREATED_DATE))
            )
        }
        cursor.close()
        db.close()
        return item
    }

    fun selectAllItems(): ArrayList<WishItem> {
        Log.d(LOGTAG, "select all items")

        val db = readableDatabase
        val items = ArrayList<WishItem>()

        val cursor = db.rawQuery(
            "select * from " + DBContract.DBEntry.TABLE_NAME +
                    " where " + DBContract.DBEntry.STATUS + "=" + DBContract.Status.Active.value +
                    " order by " + DBContract.DBEntry.TYPE + " asc," +
                    DBContract.DBEntry.POSITION + " desc", null
        )

        if (cursor!!.moveToFirst()) {
            do {
                items.add(
                    WishItem(
                        cursor.getInt(cursor.getColumnIndex(DBContract.DBEntry.POSITION)),
                        cursor.getString(cursor.getColumnIndex(DBContract.DBEntry.SUBJECT)),
                        cursor.getInt(cursor.getColumnIndex(DBContract.DBEntry.TYPE)),
                        cursor.getString(cursor.getColumnIndex(DBContract.DBEntry.DETAIL)),
                        cursor.getInt(cursor.getColumnIndex(DBContract.DBEntry.STATUS)),
                        cursor.getString(cursor.getColumnIndex(DBContract.DBEntry.CLOSED_DATE)),
                        cursor.getString(cursor.getColumnIndex(DBContract.DBEntry.CREATED_DATE))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return items
    }

    fun getItemCount(): Int {
        val db = readableDatabase

        val cursor = db.rawQuery("select count(*) from " + DBContract.DBEntry.TABLE_NAME, null)
        var cnt = 0
        if (cursor != null) {
            cursor.moveToFirst()
            cnt = cursor.getInt(0)
        }
        Log.d(LOGTAG, "getItemCount return : $cnt")
        cursor.close()
        db.close()
        return cnt
    }

    fun insertItem(
        position: Int,
        subject: String,
        type: DBContract.Type,
        detail: String?,
        status: DBContract.Status
    ) {
        val db = writableDatabase

        val values = ContentValues()
        values.put(DBContract.DBEntry.POSITION, position)
        values.put(DBContract.DBEntry.SUBJECT, subject)
        values.put(DBContract.DBEntry.TYPE, type.value)
        values.put(DBContract.DBEntry.DETAIL, detail)
        values.put(DBContract.DBEntry.STATUS, status.value)
        values.put(DBContract.DBEntry.CREATED_DATE, getCurrentDate())
        db.insert(DBContract.DBEntry.TABLE_NAME, null, values)
        db.close()
    }

    fun updateItem(
        position: Int,
        subject: String,
        type: DBContract.Type,
        detail: String?,
        status: DBContract.Status
    ) {
        val db = writableDatabase

        val values = ContentValues()
        values.put(DBContract.DBEntry.SUBJECT, subject)
        values.put(DBContract.DBEntry.TYPE, type.value)
        values.put(DBContract.DBEntry.DETAIL, detail)
        values.put(DBContract.DBEntry.STATUS, status.value)
        values.put(DBContract.DBEntry.CREATED_DATE, getCurrentDate())
        db.update(
            DBContract.DBEntry.TABLE_NAME,
            values,
            DBContract.DBEntry.POSITION + "=?",
            arrayOf(position.toString())
        )
        db.close()
    }

    private fun getCurrentDate(): String {
        val current = LocalDateTime.now()
        return current.format(dbDateFormatter)
    }

    companion object {
        const val DB_VERSION = 1
        const val DB_FILE = "wishitems.db"

        private const val SQL_CREATE = "create table " +
                DBContract.DBEntry.TABLE_NAME + " (" +
                DBContract.DBEntry.POSITION + " INTEGER, " +
                DBContract.DBEntry.SUBJECT + " TEXT, " +
                DBContract.DBEntry.TYPE + " INTEGER, " +
                DBContract.DBEntry.DETAIL + " TEXT, " +
                DBContract.DBEntry.STATUS + " INTEGER, " +
                DBContract.DBEntry.CLOSED_DATE + " TEXT, " +
                DBContract.DBEntry.CREATED_DATE + " TEXT);"
    }
}
