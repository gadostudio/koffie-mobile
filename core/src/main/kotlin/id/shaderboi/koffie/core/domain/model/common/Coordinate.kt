package id.shaderboi.koffie.core.domain.model.common

import android.location.Location
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
class Coordinate(
    val lat: Double,
    val lon: Double,
): Parcelable {
    fun toLatLng() = LatLng(lat, lon)

    fun distanceTo(other: Coordinate): Float {
        val result = FloatArray(1)
        Location.distanceBetween(
            lat,
            lon,
            other.lat,
            other.lon,
            result
        )
        return result[0]
    }
}

fun LatLng.toCoordinate() = Coordinate(latitude, longitude)
