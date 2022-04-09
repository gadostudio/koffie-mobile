package id.shaderboi.koffie.ui.common.view_model

import android.app.Activity
import androidx.navigation.NavController

sealed class AuthEvent {
    class Auth(val phoneNumber: String, val activity: Activity, val onSent: () -> Unit) :
        AuthEvent()

    class Verify(
        val verificationCode: String,
        val activity: Activity,
        val navController: NavController
    ) : AuthEvent()

    object SignOut : AuthEvent()
}