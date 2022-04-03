package id.shaderboi.koffie.ui.auth.view_model

import android.app.Activity
import androidx.navigation.NavController

sealed class SignInEvent {
    class SignIn(val phoneNumber: String, val activity: Activity, val onSent: () -> Unit) :
        SignInEvent()

    class Verify(
        val phoneNumber: String,
        val activity: Activity,
        val navController: NavController
    ) : SignInEvent()
}