package id.shaderboi.koffie.core.data.repository

import android.location.Location
import id.shaderboi.koffie.core.data.data_source.network.KoffieAPIService
import id.shaderboi.koffie.core.domain.model.common.Coordinate
import id.shaderboi.koffie.core.domain.model.store.Store
import id.shaderboi.koffie.core.domain.model.store.StoreWithDistance
import id.shaderboi.koffie.core.domain.model.store.products.CategorizedProduct
import id.shaderboi.koffie.core.domain.model.store.products.Product
import id.shaderboi.koffie.core.domain.repository.StoreRepository

class StoreRepositoryImpl(
    private val koffieAPIService: KoffieAPIService
) : StoreRepository {
    override suspend fun getStores(coordinate: Coordinate): List<StoreWithDistance> =
        koffieAPIService.getStores()
            .map { store ->
                StoreWithDistance(store, store.coordinate.distanceTo(coordinate))
            }
            .sortedBy { storeWithDistance -> storeWithDistance.distance }

    override suspend fun getStore(storeId: Int): Store =
        koffieAPIService.getStore(storeId)

    override suspend fun getProducts(storeId: Int): List<CategorizedProduct> =
        koffieAPIService.getProducts(storeId).categorize()

    override suspend fun getProduct(storeId: Int, productId: Int): Product =
        koffieAPIService.getProduct(storeId, productId)
}