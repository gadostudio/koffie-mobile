package id.shaderboi.koffie.ui.auth.verify.view_model

sealed class VerifyEvent {
    class Verify(
        val verificationId: String,
        val verificationCode: String,
    ) : VerifyEvent()
}