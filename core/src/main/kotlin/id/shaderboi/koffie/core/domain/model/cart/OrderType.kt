package id.shaderboi.koffie.core.domain.model.cart

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
sealed class OrderType {
    object Pickup : OrderType()
    data class Delivery(
        val name: String,
        val phone: String,
        val address: String,
    ) : OrderType()
}