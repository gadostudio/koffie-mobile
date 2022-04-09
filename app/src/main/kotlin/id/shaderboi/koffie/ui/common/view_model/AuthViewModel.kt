package id.shaderboi.koffie.ui.common.view_model

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.koffie.core.data.auth.Auth
import id.shaderboi.koffie.ui.main.MainActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: Auth
) : ViewModel() {
    private var verificationId: String? = null

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.Auth -> signIn(event.phoneNumber, event.activity, event.onSent)
            is AuthEvent.Verify -> verificationId?.let { verificationId ->
                val credential =
                    PhoneAuthProvider.getCredential(verificationId, event.verificationCode)
                signInWithPhoneAuthCredential(credential, event.activity)
            }
            is AuthEvent.SignOut -> auth.signOut()
        }
    }

    private fun signIn(phoneNumber: String, activity: Activity, onSent: () -> Unit) {
        val options = PhoneAuthOptions
            .newBuilder(auth.firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        signInWithPhoneAuthCredential(credential, activity)
                    }

                    override fun onVerificationFailed(exception: FirebaseException) {
                        exception
                    }

                    override fun onCodeSent(
                        _verificationId: String,
                        forceResendingToken: PhoneAuthProvider.ForceResendingToken
                    ) {
                        verificationId = _verificationId

                        onSent()
                    }
                }
            )
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, activity: Activity) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    activity.startActivity(intent)
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    }
                }
            }
    }
}