package id.shaderboi.koffie.core.domain.model.store

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class StoreWithDistance(
    val store: Store,
    val distance: Float
): Parcelable