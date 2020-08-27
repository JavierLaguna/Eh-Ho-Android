package io.keepcoding.eh_ho.data

import android.content.Context
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import io.keepcoding.eh_ho.R

object PostsRepo {

    fun getPosts(
        context: Context,
        topicId: String,
        onSuccess: (List<Post>) -> Unit,
        onError: (RequestError) -> Unit
    ) {
        val request = JsonObjectRequest(
            Request.Method.GET,
            ApiRouters.getPosts(topicId),
            null,
            { response ->
                val list = Post.parsePostsList(response)
                onSuccess(list)
            },
            {
                it.printStackTrace()

                val errorObject = if (it is NetworkError) {
                    RequestError(it, messageResId = R.string.error_no_internet)
                } else {
                    RequestError(it)
                }

                onError(errorObject)
            }
        )

        ApiRequestQueue.getReuestQueue(context).add(request)
    }
}