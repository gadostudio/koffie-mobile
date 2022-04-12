package id.shaderboi.koffie.core.data.data_source.network

import id.shaderboi.koffie.core.domain.model.StoreFront
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface KoffieAPIService {
    @GET("/v1/products")
    fun getStoreFront(): Flow<StoreFront>
}