package id.shaderboi.koffie.core.data.data_source.network

import id.shaderboi.koffie.core.domain.model.cart.Cart
import id.shaderboi.koffie.core.domain.model.common.Coordinate
import id.shaderboi.koffie.core.domain.model.coupon.Coupon
import id.shaderboi.koffie.core.domain.model.store.Store
import id.shaderboi.koffie.core.domain.model.store.products.Product
import id.shaderboi.koffie.core.domain.model.store.products.Products
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface KoffieAPIService {
    @GET("/v1/store/:storeId")
    suspend fun getStore(@Query("storeId") storeId: Int): Store

    @GET("/v1/store")
    suspend fun getStores(): List<Store>

    @GET("/v1/store/:storeId/product")
    suspend fun getProducts(@Query("storeId") storeId: Int): Products

    @GET("/v1/store/:storeId/product/:productId")
    suspend fun getProduct(
        @Query("storeId") storeId: Int,
        @Query("productId") productId: Int
    ): Product

    @POST("/v1/store/nearest")
    suspend fun getNearestStore(coordinate: Coordinate): Int?

    @GET("/v1/coupon")
    suspend fun getCoupons(): List<Coupon>

    @POST("/v1/cart")
    suspend fun checkCart(cart: Cart): Unit

    @POST("/v1/checkout")
    suspend fun checkout(cart: Cart): Unit
}