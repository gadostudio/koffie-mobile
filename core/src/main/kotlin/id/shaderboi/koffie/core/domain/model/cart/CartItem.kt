package id.shaderboi.koffie.core.domain.model.cart

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CartItem(
    @Json(name = "product_id")
    val productId: Int,
    @Json(name = "quantity")
    val quantity: Int,
)