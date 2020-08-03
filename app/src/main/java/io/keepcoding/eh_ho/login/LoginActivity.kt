package io.keepcoding.eh_ho.login

import android.content.Intent
import android.os.AsyncTask
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

//        val inputUserName: EditText = findViewById(R.id.input_username)
//        val button: Button = findViewById(R.id.button_login)
//
//        button.setOnClickListener {
//            Toast.makeText(it.context, "Welcome to Eh-Ho ${inputUserName.text}", Toast.LENGTH_LONG) .show()
//        }

//        if (savedInstanceState == null) {
        if (isFirstTimeCreated(savedInstanceState)) {
            checkSession()
        }
//        }
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
        // showTopics()

        enableLoading()
//        simulateLoading(signInModel.username)
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
//        simulateLoading("")

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

    // El método más básico y antiguo
    private fun simulateLoadingRunnable() {
        val runnable = Runnable {
            Thread.sleep(3000)

            viewLoading.post { // Ejecutar en el hilo donde está "viewLoading"
                showTopics()
            }
        }

        Thread(runnable).start()
    }

    // Usando AsyncTask
    private fun simulateLoading(signInModel: SignInModel) {
        val task = object : AsyncTask<Long, Void, Boolean>() {

            override fun doInBackground(vararg time: Long?): Boolean {
                Thread.sleep(time[0] ?: 3000)
                return true
            }

            override fun onPostExecute(result: Boolean?) {
                super.onPostExecute(result)

//                UserRepo.signIn(applicationContext, signInModel)
                showTopics()
            }
        }

        task.execute(5000)
    }
}