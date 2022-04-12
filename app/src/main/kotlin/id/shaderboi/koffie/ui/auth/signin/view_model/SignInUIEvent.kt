package id.shaderboi.koffie.ui.auth.signin.view_model

import id.shaderboi.koffie.util.StringDisplay

sealed class SignInUIEvent {
    class ShowErrorMessage(val message: StringDisplay): SignInUIEvent()
    class Finished(val verificationId: String): SignInUIEvent()
    class IsEnableInput(val enable: Boolean): SignInUIEvent()
}