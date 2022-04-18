package id.shaderboi.koffie.core.domain.model.common

import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Coordinate(
    val lat: Double,
    val lon: Double,
)

fun LatLng.toCoordinate() = Coordinate(latitude, longitude)
