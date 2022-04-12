package id.shaderboi.koffie.ui.auth.signin.view_model

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.koffie.core.data.auth.Auth
import id.shaderboi.koffie.util.StringDisplay
import id.shaderboi.koffie.util.firebase.auth.errorCodeResId
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val auth: Auth
) : ViewModel() {
    private val _uiFlow = MutableSharedFlow<SignInUIEvent>()
    val uiFlow = _uiFlow.asSharedFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignIn -> signIn(event.phoneNumber, event.activity)
        }
    }

    private fun signIn(phoneNumber: String, activity: Activity) = viewModelScope.launch {
        _uiFlow.emit(SignInUIEvent.IsEnableInput(false))
        auth.startSignInWithPhoneNumber(phoneNumber, activity)
            .catch { err ->
                Timber.e(err)
                val message = when (err) {
                    is FirebaseAuthException -> err.errorCodeResId?.let { resId ->
                        StringDisplay.StringRes(resId)
                    } ?: StringDisplay.String("Uknown error. ${err.message}")
                    else -> StringDisplay.String("Uknown error. ${err.message}")
                }
                _uiFlow.emit(SignInUIEvent.ShowErrorMessage(message))
                _uiFlow.emit(SignInUIEvent.IsEnableInput(true))
            }
            .collectLatest { verificationId ->
                _uiFlow.emit(SignInUIEvent.Finished(verificationId))
                _uiFlow.emit(SignInUIEvent.IsEnableInput(true))
            }
    }
}