package io.keepcoding.eh_ho.data

import android.net.Uri

object ApiRouters {

    private fun uriBuilder() = Uri.Builder()
        .scheme("https")
        .authority("mdiscourse.keepcoding.io")

    fun signIn(username: String) = uriBuilder()
        .appendPath("users")
        .appendPath("$username.json")
        .build()
        .toString()
}