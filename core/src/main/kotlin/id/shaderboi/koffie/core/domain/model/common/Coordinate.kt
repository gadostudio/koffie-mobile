package id.shaderboi.koffie.core.domain.model.common

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Coordinate(
    val lat: Float,
    val lon: Float,
)
