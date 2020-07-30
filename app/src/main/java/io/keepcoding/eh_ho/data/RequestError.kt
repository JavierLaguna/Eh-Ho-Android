package io.keepcoding.eh_ho.data

import com.android.volley.VolleyError

class RequestError(
    val volleyError: VolleyError?,
    val message: String? = null,
    val messageResId: Int? = null
) {}