package io.keepcoding.eh_ho.data

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

data class Post(
    val id: String,
    val author: String,
    val date: Date,
    val content: String
) {

    companion object {
        fun parsePostsList(response: JSONObject): List<Post> {
            val objectList = response.getJSONObject("post_stream")
                .getJSONArray("posts")

            val posts = mutableListOf<Post>()
            for (i in 0 until objectList.length()) {
                posts.add(parsePost(objectList.getJSONObject(i)))
            }

            return posts
        }

        fun parsePost(jsonObject: JSONObject): Post {
            val date = jsonObject.getString("created_at")
                .replace("Z", "+0000")
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
            val dateFormatted = dateFormat.parse(date) ?: Date()

            return Post(
                id = jsonObject.getInt("id").toString(),
                author = jsonObject.getString("username"),
                date = dateFormatted,
                content = jsonObject.getString("cooked")
            )
        }
    }

    fun formattedDate(): String {
        val format = SimpleDateFormat("EEEE, d MMM yyyy", Locale.getDefault())
        return format.format(date)
    }
}