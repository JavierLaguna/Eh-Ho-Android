package io.keepcoding.eh_ho.posts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.isFirstTimeCreated

const val EXTRA_TOPIC_ID = "TOPIC_ID"
const val EXTRA_TOPIC_TITLE = "TOPIC_TITLE"
const val TRANSACTION_CREATE_POST = "create_post"

class PostsActivity : AppCompatActivity(), PostsFragment.PostsInteractionListener,
    CreatePostFragment.CreatePostInteractionListener {
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

    // PostsInteractionListener
    override fun onCreatePost() {
        val topicId = intent.getStringExtra(EXTRA_TOPIC_ID) ?: ""
        val topicTitle = intent.getStringExtra(EXTRA_TOPIC_TITLE) ?: ""

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, CreatePostFragment(topicId, topicTitle))
            .addToBackStack(TRANSACTION_CREATE_POST)
            .commit()
    }

    // CreatePostInteractionListener
    override fun onPostCreated() {
        supportFragmentManager.popBackStack()
    }
}