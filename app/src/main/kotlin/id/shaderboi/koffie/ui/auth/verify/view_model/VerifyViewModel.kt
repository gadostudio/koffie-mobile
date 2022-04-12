package id.shaderboi.koffie.ui.auth.verify.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.koffie.core.data.auth.Auth
import id.shaderboi.koffie.core.util.firebase.toUserMaybe
import id.shaderboi.koffie.util.StringDisplay
import id.shaderboi.koffie.util.firebase.auth.errorCodeResId
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(
    private val auth: Auth,
) : ViewModel() {
    val isAuthenticated get() = auth.isAuthenticated
    val authenticationStateFlow = auth.onAuthChange(viewModelScope)

    private val _uiFlow = MutableSharedFlow<VerifyUIEvent>()
    val uiFlow = _uiFlow.asSharedFlow()

    fun onEvent(event: VerifyEvent) {
        when (event) {
            is VerifyEvent.Verify -> verify(event.verificationId, event.verificationCode)
        }
    }

    private fun verify(verificationId: String, verificationCode: String) = viewModelScope.launch {
        _uiFlow.emit(VerifyUIEvent.IsEnableInput(false))
        auth.signInWithPhoneNumber(verificationId, verificationCode)
            .catch { err ->
                Timber.e(err)
                val message = when (err) {
                    is FirebaseAuthException -> err.errorCodeResId?.let { resId ->
                        StringDisplay.StringRes(resId)
                    } ?: StringDisplay.String("Uknown error. ${err.message}")
                    else -> StringDisplay.String("Uknown error. ${err.message}")
                }
                _uiFlow.emit(VerifyUIEvent.ShownErrorMessage(message))
                _uiFlow.emit(VerifyUIEvent.IsEnableInput(true))
            }
            .collectLatest { authResult ->
                _uiFlow.emit(VerifyUIEvent.Finished(authResult.user!!.toUserMaybe()!!))
                _uiFlow.emit(VerifyUIEvent.IsEnableInput(true))
            }
    }
}