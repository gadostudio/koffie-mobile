package id.shaderboi.koffie.core.util.firebase

import com.google.firebase.auth.FirebaseUser
import id.shaderboi.koffie.core.data.auth.User

fun FirebaseUser.toUserMaybe(): User? {
    if (phoneNumber == null) return null
    return User(
        displayName,
        phoneNumber!!,
    )
}