package id.shaderboi.koffie.ui.auth.registration.view_model

import id.shaderboi.koffie.util.StringDisplay

sealed class RegistrationUIEvent {
    class ShowErrorMessage(val message: StringDisplay): RegistrationUIEvent()
    class IsEnableInput(val enabled: Boolean) : RegistrationUIEvent()
    object Finished: RegistrationUIEvent()
}