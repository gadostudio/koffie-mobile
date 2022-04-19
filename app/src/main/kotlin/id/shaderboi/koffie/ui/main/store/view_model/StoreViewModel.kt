package id.shaderboi.koffie.ui.main.store.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.koffie.core.domain.model.store.Store
import id.shaderboi.koffie.core.domain.model.store.products.CategorizedProduct
import id.shaderboi.koffie.core.domain.repository.StoreRepository
import id.shaderboi.koffie.core.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) : ViewModel() {
    private val _store = MutableSharedFlow<Resource<Store>>(1)
    val store = _store.asSharedFlow()

    private val _products = MutableSharedFlow<Resource<List<CategorizedProduct>>>(1)
    val products = _products.asSharedFlow()

    fun onEvent(event: StoreEvent) {
        when (event) {
            is StoreEvent.Load -> loadStoreAndProduct(event.storeId)
        }
    }

    private fun loadStoreAndProduct(storeId: Int) = viewModelScope.launch {
        _products.emit(Resource.Loading())
        _store.emit(Resource.Loading())
        launch {
            try {
                _store.emit(Resource.Loaded(storeRepository.getStore(storeId)))
            } catch (ex: Exception) {
                _store.emit(Resource.Error(ex))
            }
        }
        launch {
            try {
                _products.emit(Resource.Loaded(storeRepository.getProducts(storeId)))
            } catch (ex: Exception) {
                _products.emit(Resource.Error(ex))
            }
        }
    }
}