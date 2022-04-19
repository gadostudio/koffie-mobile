package id.shaderboi.koffie.core.domain.model.store.products

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
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
): Parcelable
