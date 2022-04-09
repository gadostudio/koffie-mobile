package id.shaderboi.koffie.core.data.data_source.network

import id.shaderboi.koffie.core.domain.model.StoreFront
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface KoffieAPIService {
    @GET("/api/v1/blabla")
    fun getStoreFront(): Flow<StoreFront>
}