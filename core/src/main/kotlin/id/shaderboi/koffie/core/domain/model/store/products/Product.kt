package id.shaderboi.koffie.core.domain.model.store.products

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    @Json(name = "category_id")
    val categoryId: Int,
    @Json(name = "image_url")
    val imageUrl: String,
    val discount: Int?
)
