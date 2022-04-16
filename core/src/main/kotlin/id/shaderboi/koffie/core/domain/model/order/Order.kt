package id.shaderboi.koffie.core.domain.model.order

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.datetime.Instant

@JsonClass(generateAdapter = true)
class Order(
    @Json(name = "restaurant_name")
    val restaurantName: String,
    @Json(name = "item_amount")
    val itemAmount: Int,
    @Json(name = "total_price")
    val totalPrice: Int,
    @Json(name = "created_at")
    val createdAt: Instant
)
