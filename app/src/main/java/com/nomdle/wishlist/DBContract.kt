package com.nomdle.wishlist

import android.provider.BaseColumns

object DBContract {
    class DBEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "wishitems"
            const val POSITION = "position"
            const val SUBJECT = "subject"
            const val TYPE = "type"
            const val DETAIL = "detail"
            const val STATUS = "status"
            const val CLOSED_DATE = "closedDate"
            const val CREATED_DATE = "createdDate"
        }
    }

    enum class Type(val value: Int) {
        Priority(0),
        Normal(1)
    }

    enum class Status(val value: Int) {
        Active(0),
        Done(1)
    }
}