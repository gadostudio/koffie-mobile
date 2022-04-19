package id.shaderboi.koffie.core.data.repository

import android.location.Location
import id.shaderboi.koffie.core.domain.model.common.Coordinate
import id.shaderboi.koffie.core.domain.model.store.Store
import id.shaderboi.koffie.core.domain.model.store.StoreWithDistance
import id.shaderboi.koffie.core.domain.model.store.products.CategorizedProduct
import id.shaderboi.koffie.core.domain.model.store.products.Product
import id.shaderboi.koffie.core.domain.repository.StoreRepository
import kotlinx.coroutines.delay

class FakeStoreRepository : StoreRepository {
    val storeData = listOf(
        Store(
            0,
            "Janji Jiwa 1111",
            "Jalan satu",
            Coordinate(-6.297104100590488, 107.1688246619523)
        ),
        Store(
            1,
            "Janji Jiwa 22222",
            "Jalan dua",
            Coordinate(-6.299852003025651, 107.15783541824175)
        )
    )

    override suspend fun getStores(coordinate: Coordinate): List<StoreWithDistance> {
        delay(2000)

        return storeData.map { store ->
            val result = FloatArray(1)
            Location.distanceBetween(
                coordinate.lat,
                coordinate.lon,
                store.coordinate.lat,
                store.coordinate.lon,
                result
            )

            StoreWithDistance(store, result[0])
        }
            .sortedBy { storeWithDistance -> storeWithDistance.distance }
    }

    override suspend fun getStore(storeId: Int): Store {
        return storeData[storeId]
    }

    override suspend fun getProducts(storeId: Int): List<CategorizedProduct> {
        delay(2000)
        return listOf(
            CategorizedProduct(
                "Minuman",
                listOf(
                    Product(
                        0,
                        "ABC",
                        "Some description",
                        12000,
                        0,
                        "https://i.epvpimg.com/avEeeab.png",
                        2000
                    ),
                    Product(
                        1,
                        "DEF",
                        "Some description",
                        22000,
                        0,
                        "https://i.epvpimg.com/avEeeab.png",
                        7000
                    )
                )
            ),
            CategorizedProduct(
                "Makanan",
                listOf(
                    Product(
                        3,
                        "XYZ",
                        "Some description",
                        12000,
                        1,
                        "https://i.epvpimg.com/avEeeab.png",
                        null
                    )
                )
            )
        )
    }

    override suspend fun getProduct(storeId: Int, productId: Int): Product {
        TODO("Not yet implemented")
    }
}