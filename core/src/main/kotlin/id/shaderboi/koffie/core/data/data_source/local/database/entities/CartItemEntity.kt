package id.shaderboi.koffie.core.data.data_source.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_item")
class CartItemEntity(
    @PrimaryKey
    val id: Int,
    val productId: Int,
    val quantity: Int,
)