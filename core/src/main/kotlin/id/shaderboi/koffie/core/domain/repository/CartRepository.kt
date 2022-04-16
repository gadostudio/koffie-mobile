package id.shaderboi.koffie.core.domain.repository

import id.shaderboi.koffie.core.data.data_source.local.database.entities.CartItemEntity
import id.shaderboi.koffie.core.domain.model.cart.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItemEntity>>
    suspend fun reduceCartItem(itemId: Int)
    suspend fun addCartItem(itemId: Int)
    suspend fun deleteCartItem(itemId: Int)
}
