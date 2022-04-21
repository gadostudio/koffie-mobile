package id.shaderboi.koffie.core.data.data_source.local.database.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import id.shaderboi.koffie.core.domain.model.store.products.Product

class ProductConverter {
    private val moshi = Moshi.Builder().build()
    private val converter = moshi.adapter(Product::class.java)

    @TypeConverter
    fun fromProduct(product: Product): String {
        return converter.toJson(product)
    }

    @TypeConverter
    fun toProduct(json: String): Product {
        return converter.fromJson(json)!!
    }
}