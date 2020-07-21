package io.keepcoding.eh_ho

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class TopicsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)

        Log.d(TopicsActivity::class.simpleName, TopicsRepo.topics.toString())
        // TopicsRepo.topics.toString()
    }
}