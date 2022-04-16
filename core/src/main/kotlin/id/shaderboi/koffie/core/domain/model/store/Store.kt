package id.shaderboi.koffie.core.domain.model.store

import com.squareup.moshi.JsonClass
import id.shaderboi.koffie.core.domain.model.common.Coordinate

@JsonClass(generateAdapter = true)
class Store(
    val id: Int,
    val name: String,
    val address: String,
    val coordinate: Coordinate
)
