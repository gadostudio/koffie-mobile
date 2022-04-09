package id.shaderboi.koffie.core.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    val itemId: Int,
    val name: String,
    val description: String,
    val price: Int,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "undiscounted_price")
    val undiscountedPrice: Int?,
    @Json(name = "created_at")
    val createdAt: String
)
