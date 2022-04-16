package id.shaderboi.koffie.core.domain.model.cart

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Cart(
    val items: List<CartItem>,
    @Json(name = "coupon_id")
    val couponId: Int?,
    @Json(name = "order_type")
    val orderType: OrderType
)
