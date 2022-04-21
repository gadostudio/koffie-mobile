package id.shaderboi.koffie.ui.product.view_model

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor() : ViewModel() {
    fun onEvent(event: ProductEvent) {
        when(event) {
            is ProductEvent.AddProductToCart -> {
                event.product
            }
//            is ProductEvent.
        }
    }
}