package id.shaderboi.koffie.ui.auth.registration.view_model

import android.app.Activity
import com.google.firebase.auth.AuthResult

sealed class RegistrationEvent {
    class Register(val displayName: String) : RegistrationEvent()
}