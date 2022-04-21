package id.shaderboi.koffie.core.data.data_source.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import id.shaderboi.koffie.core.domain.model.store.products.Product

@Entity(tableName = "cart_item")
class CartItemEntity(
    @PrimaryKey
    val id: Int,
    val productId: Int,
    val quantity: Int,
    val product: Product
)