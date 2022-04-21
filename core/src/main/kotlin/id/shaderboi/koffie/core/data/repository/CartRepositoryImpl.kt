package id.shaderboi.koffie.core.data.repository

import id.shaderboi.koffie.core.data.data_source.local.database.KoffieDatabase
import id.shaderboi.koffie.core.data.data_source.local.database.entities.CartItemEntity
import id.shaderboi.koffie.core.domain.model.store.products.Product
import id.shaderboi.koffie.core.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl(
    private val koffieDatabase: KoffieDatabase
): CartRepository {
    private val cartDao = koffieDatabase.cartDao

    override val cartItemsFlow: Flow<List<CartItemEntity>> get() = cartDao.getCartItems()
    override suspend fun reduceCartItem(productId: Int) = cartDao.reduceCartItem(productId)
    override suspend fun addCartItem(product: Product) = cartDao.addCartItem(product)
    override suspend fun deleteCartItem(productId: Int) = cartDao.deleteCartItem(productId)
}