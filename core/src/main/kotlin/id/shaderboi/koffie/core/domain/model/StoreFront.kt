package id.shaderboi.koffie.core.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StoreFront(
    val categories: List<CategoryProducts>
)
