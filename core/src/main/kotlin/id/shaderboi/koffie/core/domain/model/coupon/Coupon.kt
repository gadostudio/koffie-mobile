package id.shaderboi.koffie.core.domain.model.coupon

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coupon(
    val id: Int,
    val title: String,
    val description: String,
)