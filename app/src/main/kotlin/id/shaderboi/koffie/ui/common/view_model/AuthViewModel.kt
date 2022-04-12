package id.shaderboi.koffie.ui.common.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.koffie.core.data.auth.Auth
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val auth: Auth
): ViewModel() {
    val authenticationFlow = auth.onAuthChange(viewModelScope)
}