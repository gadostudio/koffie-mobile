package id.shaderboi.koffie.ui.product.view_model

import id.shaderboi.koffie.core.domain.model.store.products.Product

sealed class ProductEvent {
    class AddProductToCart(val product: Product) : ProductEvent()
}