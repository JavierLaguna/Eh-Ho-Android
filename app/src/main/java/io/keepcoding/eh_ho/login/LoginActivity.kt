package io.keepcoding.eh_ho.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.topics.TopicsActivity
import io.keepcoding.eh_ho.isFirstTimeCreated

class LoginActivity : AppCompatActivity(), SignInFragment.SignInInteractionListener,
    SignUpFragment.SignUpInteractionListener {

    val signInFragment = SignInFragment()
    val signUpFragment = SignUpFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        val inputUserName: EditText = findViewById(R.id.input_username)
//        val button: Button = findViewById(R.id.button_login)
//
//        button.setOnClickListener {
//            Toast.makeText(it.context, "Welcome to Eh-Ho ${inputUserName.text}", Toast.LENGTH_LONG) .show()
//        }

//        if (savedInstanceState == null) {
        if (isFirstTimeCreated(savedInstanceState)) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, signInFragment)
                .commit()
        }
//        }
    }

    // SignInInteractionListener
    private fun showTopics() {
        val intent: Intent = Intent(this, TopicsActivity::class.java)
        startActivity(intent)
    }

    override fun onGoToSignUp() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, signUpFragment)
            .commit()
    }

    override fun onSignIn() {
        // Autenticación
        showTopics()
    }

    // SignUpInteractionListener
    override fun onGoToSignIn() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, signInFragment)
            .commit()
    }

    override fun onSignUp() {

    }
}