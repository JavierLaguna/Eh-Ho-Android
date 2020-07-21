package io.keepcoding.eh_ho

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        val inputUserName: EditText = findViewById(R.id.input_username)
//        val button: Button = findViewById(R.id.button_login)
//
//        button.setOnClickListener {
//            Toast.makeText(it.context, "Welcome to Eh-Ho ${inputUserName.text}", Toast.LENGTH_LONG) .show()
//        }
    }

    fun showTopics(view: View) {
        val intent: Intent = Intent(this, TopicsActivity::class.java)
        startActivity(intent)
    }
}