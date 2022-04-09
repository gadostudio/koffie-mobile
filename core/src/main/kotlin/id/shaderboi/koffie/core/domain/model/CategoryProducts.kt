package id.shaderboi.koffie.core.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryProducts(
    @Json(name = "category_name")
    val categoryName: String,
    @Json(name = "category_id")
    val categoryId: Int,
    val products: List<Product>
)
