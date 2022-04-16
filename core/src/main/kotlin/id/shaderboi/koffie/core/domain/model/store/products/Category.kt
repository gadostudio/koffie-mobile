package id.shaderboi.koffie.core.domain.model.store.products

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import id.shaderboi.koffie.core.domain.model.store.products.Product

@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "category_id")
    val id: Int,
    @Json(name = "category_name")
    val categoryName: String,
)
