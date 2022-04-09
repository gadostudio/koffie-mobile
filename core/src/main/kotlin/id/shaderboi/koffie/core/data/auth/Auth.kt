package id.shaderboi.koffie.core.data.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

interface Auth {
    val firebaseAuth: FirebaseAuth

    val isAuthenticated: Boolean

    //    fun isAuthenticated(coroutineScope: CoroutineScope): SharedFlow<Boolean>
    val userInfo: User

    fun signInWithCredential(credential: AuthCredential): Task<AuthResult>
    fun signOut()
}