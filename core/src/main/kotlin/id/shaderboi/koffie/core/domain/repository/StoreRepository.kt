package id.shaderboi.koffie.core.domain.repository

import id.shaderboi.koffie.core.domain.model.common.Coordinate
import id.shaderboi.koffie.core.domain.model.store.Store
import id.shaderboi.koffie.core.domain.model.store.StoreWithDistance
import id.shaderboi.koffie.core.domain.model.store.products.CategorizedProduct
import id.shaderboi.koffie.core.domain.model.store.products.Product
import id.shaderboi.koffie.core.domain.model.store.products.Products

interface StoreRepository {
    suspend fun getStores(coordinate: Coordinate): List<StoreWithDistance>
    suspend fun getStore(storeId: Int): Store
    suspend fun getProducts(storeId: Int): List<CategorizedProduct>
    suspend fun getProduct(storeId: Int, productId: Int): Product
}