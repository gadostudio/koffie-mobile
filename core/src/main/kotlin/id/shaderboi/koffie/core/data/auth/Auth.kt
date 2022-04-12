package id.shaderboi.koffie.core.data.auth

import android.app.Activity
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import java.lang.Exception

interface Auth {
    val firebaseAuth: FirebaseAuth

    val isAuthenticated: Boolean
    fun onAuthChange(coroutineScope: CoroutineScope): SharedFlow<User?>
    val userInfo: User

    fun signInWithPhoneNumber(verificationId: String, verificationCode: String): Flow<AuthResult>
    fun register(displayName: String): Flow<Unit>
    fun startSignInWithPhoneNumber(
        phoneNumber: String,
        activity: Activity
    ): Flow<String>

    fun signOut()
}