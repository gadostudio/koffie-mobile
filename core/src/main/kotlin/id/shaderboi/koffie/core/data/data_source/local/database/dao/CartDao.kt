package id.shaderboi.koffie.core.data.data_source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import id.shaderboi.koffie.core.data.data_source.local.database.KoffieDatabase
import id.shaderboi.koffie.core.data.data_source.local.database.entities.CartItemEntity
import id.shaderboi.koffie.core.domain.model.store.products.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT EXISTS(SELECT * FROM cart_item WHERE productId = :productId)")
    suspend fun _isProductExists(productId: Int): Boolean

    @Insert
    suspend fun _insertCartItem(cartItemEntity: CartItemEntity)

    @Query("UPDATE cart_item SET quantity = quantity + 1 WHERE productId = :productId")
    suspend fun _addCartItem(productId: Int)

    @Query("UPDATE cart_item SET quantity = quantity - 1 WHERE productId = :productId")
    suspend fun _reduceCartItem(productId: Int)

    // Public use

    @Query("SELECT * FROM cart_item")
    fun getCartItems(): Flow<List<CartItemEntity>>

    @Query("DELETE FROM cart_item")
    suspend fun deleteAllCartItem()

    @Query("SELECT quantity IS 0 FROM cart_item WHERE productId = :productId")
    suspend fun isCartItemEmpty(productId: Int): Boolean

    @Query("DELETE FROM cart_item WHERE productId = :productId")
    suspend fun deleteCartItem(productId: Int)

    @Transaction
    suspend fun addCartItem(product: Product) {
        if (_isProductExists(product.id)) {
            _addCartItem(product.id)
        } else {
            _insertCartItem(CartItemEntity(0, product.id, 1, product))
        }
    }

    @Transaction
    suspend fun reduceCartItem(productId: Int) {
        _reduceCartItem(productId)
        if (isCartItemEmpty(productId)) {
            deleteCartItem(productId)
        }
    }
}