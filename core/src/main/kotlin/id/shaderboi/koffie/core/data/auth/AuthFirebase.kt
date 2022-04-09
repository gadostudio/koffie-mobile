package id.shaderboi.koffie.core.data.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import id.shaderboi.koffie.core.data.auth.exception.AuthenticationRequredException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class AuthFirebase @Inject constructor() : Auth {
    override val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override val isAuthenticated get() = firebaseAuth.currentUser != null
//    override fun isAuthenticated(coroutineScope: CoroutineScope) = callbackFlow {
//        val authStateListener: ((FirebaseAuth) -> Unit) = { auth ->
//            trySend(auth.currentUser !== null)
//        }
//
//        firebaseAuth.addAuthStateListener(authStateListener)
//        awaitClose {
//            firebaseAuth.removeAuthStateListener(authStateListener)
//        }
//    }.shareIn(
//        scope = coroutineScope,
//        replay = 1,
//        started = SharingStarted.WhileSubscribed()
//    )

    override val userInfo
        get() = run {
            val currentUser = firebaseAuth.currentUser ?: throw AuthenticationRequredException()
            User(
                currentUser.displayName!!,
                currentUser.email!!,
                currentUser.phoneNumber!!,
            )
        }

    override fun signInWithCredential(credential: AuthCredential): Task<AuthResult> =
        firebaseAuth.signInWithCredential(credential)

    override fun signOut() = firebaseAuth.signOut()
}