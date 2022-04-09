package id.shaderboi.koffie.core.data.repository

import id.shaderboi.koffie.core.data.data_source.network.KoffieAPIService
import id.shaderboi.koffie.core.domain.model.StoreFront
import id.shaderboi.koffie.core.domain.repository.VendorRepository
import id.shaderboi.koffie.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class VendorRepositoryImpl(
    private val koffieAPIService: KoffieAPIService
) : VendorRepository {
    override suspend fun loadStoreFront(): Flow<Resource<StoreFront>> =
        koffieAPIService.getStoreFront()
            .map { value -> Resource.Loaded(value) }
            .catch { cause -> Resource.Error<StoreFront>(cause) }
}