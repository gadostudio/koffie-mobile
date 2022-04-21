package id.shaderboi.koffie.core.domain.repository

import id.shaderboi.koffie.core.data.data_source.local.database.entities.CartItemEntity
import id.shaderboi.koffie.core.domain.model.cart.Cart
import id.shaderboi.koffie.core.domain.model.store.products.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    val cartItemsFlow: Flow<List<CartItemEntity>>
    suspend fun reduceCartItem(productId: Int)
    suspend fun addCartItem(product: Product)
    suspend fun deleteCartItem(productId: Int)
}
