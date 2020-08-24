package io.keepcoding.eh_ho.posts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.isFirstTimeCreated

const val EXTRA_TOPIC_ID = "TOPIC_ID"

class PostsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        if (isFirstTimeCreated(savedInstanceState)) {
            val topicId = intent.getStringExtra(EXTRA_TOPIC_ID) ?: ""

            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, PostsFragment(topicId))
                .commit()
        }
    }
}