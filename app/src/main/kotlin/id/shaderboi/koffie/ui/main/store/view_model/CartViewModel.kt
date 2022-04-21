package id.shaderboi.koffie.ui.main.store.view_model

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.koffie.core.domain.repository.CartRepository
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
): ViewModel() {
    val cartFlow = cartRepository.cartItemsFlow
}