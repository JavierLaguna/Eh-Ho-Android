package io.keepcoding.eh_ho.data

import android.content.Context
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.ServerError
import com.android.volley.toolbox.JsonObjectRequest
import io.keepcoding.eh_ho.R

const val PREFERENCES_SESSION = "session"
const val PREFERENCES_USERNAME = "session"

object UserRepo {

    fun signIn(
        context: Context,
        signInModel: SignInModel,
        success: (SignInModel) -> Unit,
        error: (RequestError) -> Unit
    ) {

        // !. Crear petición
        val request = JsonObjectRequest(
            Request.Method.GET,
//            "https://mdiscourse.keepcoding.io/users/${signInModel.username}.json",
            ApiRouters.signIn(signInModel.username),
            null,
            { response ->
                // 5. Notificar que la petición fie exitosa
                success(signInModel)
                // 6. Guardar la sesión
                saveSession(
                    context,
                    signInModel.username
                )
            },
            { e ->
                // 5.1 Procesar ese error
                e.printStackTrace()
//                error(e)

                val errorObject = if (e is ServerError && e.networkResponse.statusCode == 404) {
                    RequestError(e, messageResId = R.string.error_not_registered)
                } else if (e is NetworkError) {
                    RequestError(e, messageResId = R.string.error_no_internet)
                } else {
                    RequestError(e)
                }

                error(errorObject)
            }
        )

        // 2. Encolar la petición
//        val requestQueue = Volley.newRequestQueue(context)
//        requestQueue.add(request)
        ApiRequestQueue.getReuestQueue(context).add(request)

        // 3. Permisos de acceso a internet en el AndroidManifest.ml
    }

    fun signUp(
        context: Context, signUpModel: SignUpModel,
        success: (SignUpModel) -> Unit,
        error: (RequestError) -> Unit
    ) {
        val request = PostRequest(
            Request.Method.POST,
            ApiRouters.signUp(),
            signUpModel.toJson(),
            null,
            { response ->
                val successStatus = response?.getBoolean("success") ?: false

                if (successStatus) {
                    success(signUpModel)
                } else {
                    error(RequestError(message = response?.getString("message")))
                }
            },
            { e ->
                e.printStackTrace()

                val errorObject = if (e is NetworkError) {
                    RequestError(e, messageResId = R.string.error_no_internet)
                } else {
                    RequestError(e)
                }

                error(errorObject)
            }
        )

        ApiRequestQueue.getReuestQueue(context).add(request)
    }

    fun getUsername(context: Context): String? {
        val preferences = context.getSharedPreferences(PREFERENCES_SESSION, Context.MODE_PRIVATE)
        return preferences.getString(PREFERENCES_USERNAME, null)
    }

    private fun saveSession(context: Context, username: String) {
        val preferences = context.getSharedPreferences(PREFERENCES_SESSION, Context.MODE_PRIVATE)

        preferences.edit()
            .putString(PREFERENCES_USERNAME, username)
            .apply()
    }

    fun logout(context: Context) {
        val preferences = context.getSharedPreferences(PREFERENCES_SESSION, Context.MODE_PRIVATE)

        preferences.edit()
            .putString(PREFERENCES_USERNAME, null)
            .apply()
    }

    fun isLogged(context: Context): Boolean {
        val preferences = context.getSharedPreferences(PREFERENCES_SESSION, Context.MODE_PRIVATE)

        val username = preferences.getString(PREFERENCES_USERNAME, null)
        return username != null
    }
}