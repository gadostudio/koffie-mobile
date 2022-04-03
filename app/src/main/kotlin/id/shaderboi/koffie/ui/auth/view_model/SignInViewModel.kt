package id.shaderboi.koffie.ui.auth.view_model

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.koffie.ui.auth.SignInFragmentDirections
import id.shaderboi.koffie.ui.main.MainActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private var verificationId: String? = null

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignIn -> signIn(event.phoneNumber, event.activity, event.onSent)
            is SignInEvent.Verify -> verificationId?.let { verificationId ->
                val credential = PhoneAuthProvider.getCredential(verificationId, event.phoneNumber)
                signInWithPhoneAuthCredential(credential, event.activity)
            }
        }
    }

    private fun signIn(phoneNumber: String, activity: Activity, onSent: () -> Unit) {
        val options = PhoneAuthOptions
            .newBuilder(firebaseAuth)
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
        firebaseAuth.signInWithCredential(credential)
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