package id.shaderboi.koffie.core.domain.model.store.products

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Products(
    val products: List<Product>,
    val categories: List<Category>,
) {
    fun categorize(): List<CategorizedProduct> = categories.map { category ->
        CategorizedProduct(
            category.categoryName,
            products.filter { product -> product.categoryId == category.id }
        )
    }
}