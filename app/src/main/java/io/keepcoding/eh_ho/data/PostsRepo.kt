package io.keepcoding.eh_ho.data

import android.content.Context
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.ServerError
import com.android.volley.toolbox.JsonObjectRequest
import io.keepcoding.eh_ho.R
import org.json.JSONObject

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

    fun addPost(
        context: Context,
        model: CreatePostModel,
        onSuccess: (CreatePostModel) -> Unit,
        onError: (RequestError) -> Unit
    ) {
        val username = UserRepo.getUsername(context)
        val request = PostRequest(
            Request.Method.POST,
            ApiRouters.createPost(),
            model.toJson(),
            {
                onSuccess(model)
            },
            {
                it.printStackTrace()

                val errorObject = if (it is ServerError && it.networkResponse.statusCode == 422) {
                    val body = String(it.networkResponse.data, Charsets.UTF_8)
                    val jsonError = JSONObject(body)
                    val errors = jsonError.getJSONArray("errors")
                    var errorMessage = ""

                    for (i in 0 until errors.length()) {
                        errorMessage += "${errors[i]}"
                    }

                    RequestError(it, message = errorMessage)

                } else if (it is NetworkError) {
                    RequestError(it, messageResId = R.string.error_no_internet)
                } else {
                    RequestError(it)
                }

                onError(errorObject)
            },
            username
        )

        ApiRequestQueue.getReuestQueue(context).add(request)
    }
}