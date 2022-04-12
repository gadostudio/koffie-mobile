package id.shaderboi.koffie.core.data.auth

data class User(
    val displayName: String?,
    val phoneNumber: String,
) {
    val isRegistered get() = displayName?.isNotBlank() == true
}
