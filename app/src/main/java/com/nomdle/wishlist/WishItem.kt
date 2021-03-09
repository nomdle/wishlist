package com.nomdle.wishlist

data class WishItem(var position: Int, var subject: String, var type: Int, var detail: String?, var status: Int, var closedDate: String?, var createdDate: String) {
}