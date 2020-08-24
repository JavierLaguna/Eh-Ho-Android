package io.keepcoding.eh_ho.data

import java.util.*

data class Post(val id: String = UUID.randomUUID().toString(),
val author: String, // username
val date: Date, // created_at
val content: String) { // cooked

}