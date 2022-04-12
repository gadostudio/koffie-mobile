package id.shaderboi.koffie.ui.auth.verify.view_model

import id.shaderboi.koffie.core.data.auth.User
import id.shaderboi.koffie.util.StringDisplay

sealed class VerifyUIEvent {
    class ShownErrorMessage(val message: StringDisplay): VerifyUIEvent()
    class Finished(val user: User): VerifyUIEvent()
    class IsEnableInput(val enable: Boolean): VerifyUIEvent()
}