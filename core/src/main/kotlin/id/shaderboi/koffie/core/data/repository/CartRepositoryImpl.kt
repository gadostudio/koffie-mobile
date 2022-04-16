package id.shaderboi.koffie.core.data.repository

import id.shaderboi.koffie.core.data.data_source.local.database.KoffieDatabase
import id.shaderboi.koffie.core.data.data_source.local.database.entities.CartItemEntity
import id.shaderboi.koffie.core.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl(
    private val koffieDatabase: KoffieDatabase
): CartRepository {
    private val cartDao = koffieDatabase.cartDao

    override fun getCartItems(): Flow<List<CartItemEntity>> = cartDao.getCartItems()
    override suspend fun reduceCartItem(itemId: Int) = cartDao.reduceCartItem(itemId)
    override suspend fun addCartItem(itemId: Int) = cartDao.addCartItem(itemId)
    override suspend fun deleteCartItem(itemId: Int) = cartDao.deleteCartItem(itemId)
}