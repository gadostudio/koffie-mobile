package id.shaderboi.koffie.core.data.auth

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import id.shaderboi.koffie.core.data.auth.exception.AuthenticationRequredException
import id.shaderboi.koffie.core.util.firebase.toUserMaybe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthFirebase @Inject constructor() : Auth {
    override val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override val isAuthenticated get() = firebaseAuth.currentUser != null

    override fun onAuthChange(coroutineScope: CoroutineScope): SharedFlow<User?> = callbackFlow {
        val authStateListener: ((FirebaseAuth) -> Unit) = { auth ->
            trySend(auth.currentUser?.toUserMaybe())
        }

        firebaseAuth.addAuthStateListener(authStateListener)
        awaitClose {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }.shareIn(
        scope = coroutineScope,
        replay = 1,
        started = SharingStarted.WhileSubscribed()
    )

    override val userInfo
        get() = run {
            firebaseAuth.currentUser?.toUserMaybe() ?: throw AuthenticationRequredException()
        }

    override fun register(displayName: String): Flow<Unit> = flow {
        firebaseAuth.currentUser!!.updateProfile(
            UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build()
        ).await()
        emit(Unit)
    }

    override fun signInWithPhoneNumber(
        verificationId: String,
        verificationCode: String
    ): Flow<AuthResult> = flow {
        val credential = PhoneAuthProvider.getCredential(verificationId, verificationCode)
        val result =  firebaseAuth
            .signInWithCredential(credential)
            .await()
        emit(result)
    }

    override fun startSignInWithPhoneNumber(
        phoneNumber: String,
        activity: Activity
    ): Flow<String> = callbackFlow {
        val options = PhoneAuthOptions
            .newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(10L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(
                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        credential
                    }

                    override fun onVerificationFailed(exception: FirebaseException) {
                        close(exception)
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        forceResendingToken: PhoneAuthProvider.ForceResendingToken
                    ) {
                        trySend(verificationId)
                    }
                }
            )
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        awaitClose {  }
    }

    override fun signOut() = firebaseAuth.signOut()
}