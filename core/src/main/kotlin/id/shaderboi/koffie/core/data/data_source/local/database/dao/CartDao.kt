package id.shaderboi.koffie.core.data.data_source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import id.shaderboi.koffie.core.data.data_source.local.database.entities.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_item")
    fun getCartItems(): Flow<List<CartItemEntity>>

    @Query("UPDATE cart_item SET quantity = quantity - 1 WHERE productId = :productId")
    suspend fun _reduceCartItem(productId: Int)

    @Query("DELETE FROM cart_item WHERE quantity = 0 AND productId = :productId")
    suspend fun deleteEmptyCartItem(productId: Int)

    @Query("DELETE FROM cart_item WHERE productId = :productId")
    suspend fun deleteCartItem(productId: Int)

    @Transaction
    suspend fun reduceCartItem(productId: Int) {
        _reduceCartItem(productId)
        deleteEmptyCartItem(productId)
    }

    @Query("SELECT EXISTS(SELECT * FROM cart_item WHERE productId = :productId)")
    suspend fun isProductExists(productId: Int): Boolean

    @Insert
    suspend fun insertCartItem(cartItemEntity: CartItemEntity)

    @Query("UPDATE cart_item SET quantity = quantity + 1 WHERE productId = :productId")
    suspend fun _addCartItem(productId: Int)

    @Transaction
    suspend fun addCartItem(productId: Int) {
        if (isProductExists(productId)) {
            _addCartItem(productId)
        } else {
            // Set as 1
            insertCartItem(CartItemEntity(0, productId, 1))
        }
    }
}