package io.keepcoding.eh_ho.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.SignUpModel
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.lang.IllegalArgumentException

class SignUpFragment : Fragment() {

    var signUpInteractionListener: SignUpInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SignUpInteractionListener) {
            signUpInteractionListener = context
        } else {
            throw IllegalArgumentException("Context doesn´t implement ${SignUpInteractionListener::class.java.canonicalName}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_sign_up)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSignUp.setOnClickListener {
            signUp()
        }

        labelCreateAccount.setOnClickListener {
            signUpInteractionListener?.onGoToSignIn()
        }
    }

    private fun signUp() {
        if (isFormValid()) {
            val signUpModel = SignUpModel(
                inputUsername.text.toString(),
                inputEmail.text.toString(),
                inputPassword.text.toString()
            )
            signUpInteractionListener?.onSignUp(signUpModel)
        } else {
            showErrors()
        }
    }

    private fun isFormValid() = inputUsername.text.isNotEmpty() &&
            inputEmail.text.isNotEmpty() &&
            inputPassword.text.isNotEmpty() &&
            inputConfirmPassword.text.isNotEmpty() &&
            inputPassword.text.toString() == inputConfirmPassword.text.toString()

    private fun showErrors() {
        if (inputUsername.text.isEmpty()) {
            inputUsername.error = getString(R.string.error_empty)
        }

        if (inputEmail.text.isEmpty()) {
            inputEmail.error = getString(R.string.error_empty)
        }

        if (inputPassword.text.isEmpty()) {
            inputPassword.error = getString(R.string.error_empty)
        }

        if (inputConfirmPassword.text.isEmpty()) {
            inputConfirmPassword.error = getString(R.string.error_empty)
        } else if (inputPassword.text.toString() != inputConfirmPassword.text.toString()) {
            inputConfirmPassword.error = getString(R.string.error_mismatch_password)
        }
    }

    interface SignUpInteractionListener {
        fun onGoToSignIn()
        fun onSignUp(signUpModel: SignUpModel)
    }
}