package id.shaderboi.koffie.ui.product.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.koffie.core.domain.model.store.products.Product
import id.shaderboi.koffie.core.domain.repository.CartRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    fun onEvent(event: ProductEvent) {
        when(event) {
            is ProductEvent.AddProductToCart -> addProductToCart(event.product)
        }
    }

    private fun addProductToCart(product: Product) = viewModelScope.launch {
        cartRepository.addCartItem(product)
    }
}