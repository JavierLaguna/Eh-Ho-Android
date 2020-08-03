package io.keepcoding.eh_ho.data

import android.content.Context
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.ServerError
import com.android.volley.toolbox.JsonObjectRequest
import io.keepcoding.eh_ho.R
import org.json.JSONObject

// Singleton
object TopicsRepo {
    val topics: MutableList<Topic> = mutableListOf()
//        get() {
//            if (field.isEmpty()) {
//                field.addAll(getDummyTopics())
//            }
//            return field
//        }

//    fun getDummyTopics() {
//        for (i in 0..10) {
//            val topic = Topic(title = "Topic $i", content = "Content $i")
//            this.topics.add(topic)
//        }
//    }

//    fun getDummyTopics(count: Int = 10): List<Topic> {
//        return (0..count).map { Topic(title = "Topic $it", content = "Content $it") }
//    }

    private fun getDummyTopics(count: Int = 10) =
        (0..count).map {
            Topic(
                title = "Topic $it"
//                content = "Content $it"
            )
        }

    fun getTopics(
        context: Context,
        onSuccess: (List<Topic>) -> Unit,
        onError: (RequestError) -> Unit
    ) {
        val request = JsonObjectRequest(
            Request.Method.GET,
            ApiRouters.getTopics(),
            null,
            { response ->
                val list = Topic.parseTopicsList(response)
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

    fun getTopic(topicId: String): Topic? = topics.find { it.id == topicId }

    fun addTopic(title: String, content: String) {
        topics.add(
            Topic(
                title = title
//                content = content
            )
        )
    }

    fun addTopic(
        context: Context,
        model: CreateTopicModel,
        onSuccess: (CreateTopicModel) -> Unit,
        onError: (RequestError) -> Unit
    ) {
        val username = UserRepo.getUsername(context)
        val request = PostRequest(
            Request.Method.POST,
            ApiRouters.createTopic(),
            model.toJson(),
            username,
            {
                onSuccess(model)
            },
            {
                it.printStackTrace()

                val errorObject = if (it is ServerError && it.networkResponse.statusCode == 422) {
//                    RequestError(it, messageResId = R.string.error_duplicated_topic)

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
            }
        )

        ApiRequestQueue.getReuestQueue(context).add(request)
    }
}