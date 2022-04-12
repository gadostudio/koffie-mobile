package id.shaderboi.koffie.ui.auth.registration.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.koffie.core.data.auth.Auth
import id.shaderboi.koffie.util.StringDisplay
import id.shaderboi.koffie.util.firebase.auth.errorCodeResId
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val auth: Auth
) : ViewModel() {
    private val _uiFlow = MutableSharedFlow<RegistrationUIEvent>()
    val uiFlow = _uiFlow.asSharedFlow()

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.Register -> {
                register(event.displayName)
            }
        }
    }

    private fun register(displayName: String) = viewModelScope.launch {
        _uiFlow.emit(RegistrationUIEvent.IsEnableInput(false))
        auth.register(displayName)
            .catch { err ->
                val message = when(err) {
                    is FirebaseAuthException -> err.errorCodeResId?.let { resId ->
                        StringDisplay.StringRes(resId)
                    } ?: StringDisplay.String("Uknown error. ${err.message}")
                    else -> StringDisplay.String("Uknown error. ${err.message}")
                }
                _uiFlow.emit(RegistrationUIEvent.ShowErrorMessage(message))
                _uiFlow.emit(RegistrationUIEvent.IsEnableInput(true))
            }
            .collectLatest {
                _uiFlow.emit(RegistrationUIEvent.Finished)
                _uiFlow.emit(RegistrationUIEvent.IsEnableInput(true))
            }
    }
}