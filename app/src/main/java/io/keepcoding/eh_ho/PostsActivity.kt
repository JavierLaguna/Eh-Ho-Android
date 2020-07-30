package io.keepcoding.eh_ho

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.keepcoding.eh_ho.data.TopicsRepo
import kotlinx.android.synthetic.main.activity_posts.*

const val EXTRA_TOPIC_ID = "TOPIC_ID"

class PostsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        val topicId = intent.getStringExtra(EXTRA_TOPIC_ID) ?: ""
        val topic = TopicsRepo.getTopic(topicId)

//        labelTitle.text = topic?.title
        topic?.let {
            labelTitle.text = it.title
        }
    }
}