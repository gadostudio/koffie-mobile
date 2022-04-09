package id.shaderboi.koffie.core.domain.repository

import id.shaderboi.koffie.core.domain.model.StoreFront
import id.shaderboi.koffie.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface VendorRepository {
    suspend fun loadStoreFront(): Flow<Resource<StoreFront>>
}