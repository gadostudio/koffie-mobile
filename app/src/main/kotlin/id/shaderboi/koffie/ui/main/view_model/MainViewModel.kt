package id.shaderboi.koffie.ui.main.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private var _isSplashScreenLoading = true
    val isSplashScreenLoading get() = _isSplashScreenLoading

    init {
        viewModelScope.launch {
            delay(1000)
            _isSplashScreenLoading = false
        }
    }
}