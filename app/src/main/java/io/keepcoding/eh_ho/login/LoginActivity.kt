package io.keepcoding.eh_ho.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.RequestError
import io.keepcoding.eh_ho.data.SignInModel
import io.keepcoding.eh_ho.data.SignUpModel
import io.keepcoding.eh_ho.data.UserRepo
import io.keepcoding.eh_ho.topics.TopicsActivity
import io.keepcoding.eh_ho.isFirstTimeCreated
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), SignInFragment.SignInInteractionListener,
    SignUpFragment.SignUpInteractionListener {

    val signInFragment = SignInFragment()
    val signUpFragment = SignUpFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (isFirstTimeCreated(savedInstanceState)) {
            checkSession()
        }
    }

    private fun checkSession() {
        if (UserRepo.isLogged(applicationContext)) {
            showTopics()
        } else {
            onGoToSignIn()
        }
    }

    // SignInInteractionListener
    private fun showTopics() {
        val intent: Intent = Intent(this, TopicsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onGoToSignUp() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, signUpFragment)
            .commit()
    }

    override fun onSignIn(signInModel: SignInModel) {
        enableLoading()

        UserRepo.signIn(applicationContext, signInModel, {
            showTopics()
        }, { error ->
            enableLoading(false)
            handleError(error)
        })
    }

    private fun handleError(error: RequestError) {
        if (error.messageResId != null) {
            Snackbar.make(container, error.messageResId, Snackbar.LENGTH_SHORT).show()
        } else if (error.message != null) {
            Snackbar.make(container, error.message, Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(container, R.string.error_default, Snackbar.LENGTH_SHORT)
        }
    }

    // SignUpInteractionListener
    override fun onGoToSignIn() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, signInFragment)
            .commit()
    }

    override fun onSignUp(signUpModel: SignUpModel) {
        enableLoading()

        UserRepo.signUp(applicationContext, signUpModel, {
            enableLoading(false)
            Snackbar.make(container, R.string.message_sign_up, Snackbar.LENGTH_LONG).show()
        }, {
            enableLoading(false)
            handleError(it)
        })
    }

    private fun enableLoading(enabled: Boolean = true) {
        if (enabled) {
            fragmentContainer.visibility = View.INVISIBLE
            viewLoading.visibility = View.VISIBLE
        } else {
            fragmentContainer.visibility = View.VISIBLE
            viewLoading.visibility = View.INVISIBLE
        }
    }
}