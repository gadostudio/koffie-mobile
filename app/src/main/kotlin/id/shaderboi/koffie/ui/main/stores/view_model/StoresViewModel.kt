package id.shaderboi.koffie.ui.main.stores.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.koffie.core.domain.model.common.Coordinate
import id.shaderboi.koffie.core.domain.model.common.toCoordinate
import id.shaderboi.koffie.core.domain.model.store.Store
import id.shaderboi.koffie.core.domain.model.store.StoreWithDistance
import id.shaderboi.koffie.core.domain.repository.StoreRepository
import id.shaderboi.koffie.core.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoresViewModel @Inject constructor(
    val storeRepository: StoreRepository
) : ViewModel() {
    private val _stores = MutableSharedFlow<Resource<List<StoreWithDistance>>>()
    val stores = _stores.asSharedFlow()

    fun onEvent(event: StoresEvent) {
        when (event) {
            is StoresEvent.Load -> load(event.location.toCoordinate())
        }
    }

    private fun load(coordinate: Coordinate) = viewModelScope.launch {
        _stores.emit(Resource.Loading())
        _stores.emit(Resource.Loaded(storeRepository.getStores(coordinate)))
    }
}