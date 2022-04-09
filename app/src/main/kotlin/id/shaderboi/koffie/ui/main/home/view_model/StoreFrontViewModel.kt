package id.shaderboi.koffie.ui.main.home.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.koffie.core.domain.model.StoreFront
import id.shaderboi.koffie.core.domain.repository.VendorRepository
import id.shaderboi.koffie.core.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreFrontViewModel @Inject constructor(
    private val vendorRepository: VendorRepository
) : ViewModel() {
    private val _storeFront = MutableSharedFlow<Resource<StoreFront>>()
    val storeFront = _storeFront.asSharedFlow()

    init {
        onEvent(StoreFrontEvent.Load)
    }

    fun onEvent(event: StoreFrontEvent) {
        when (event) {
            StoreFrontEvent.Load -> loadStoreFront()
        }
    }

    private fun loadStoreFront() = viewModelScope.launch {
        _storeFront.emit(Resource.Loading())
        vendorRepository.loadStoreFront().collectLatest { resource ->
            _storeFront.emit(resource)
        }
    }
}