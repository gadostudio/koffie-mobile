package id.shaderboi.koffie.ui.auth.signin.view_model

import android.app.Activity

sealed class SignInEvent {
    class SignIn(
        val phoneNumber: String,
        val activity: Activity
    ) : SignInEvent()
}