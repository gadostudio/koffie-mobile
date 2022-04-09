package id.shaderboi.koffie.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.shaderboi.koffie.core.BuildConfig
import id.shaderboi.koffie.core.data.auth.Auth
import id.shaderboi.koffie.core.data.auth.AuthFirebase
import id.shaderboi.koffie.core.data.data_source.network.KoffieAPIService
import id.shaderboi.koffie.core.data.repository.VendorRepositoryImpl
import id.shaderboi.koffie.core.domain.repository.VendorRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideKoffieServiceAPI(retrofit: Retrofit): KoffieAPIService =
        retrofit.create(KoffieAPIService::class.java)

    @Singleton
    @Provides
    fun provideAuth(): Auth = AuthFirebase()

    @Singleton
    @Provides
    fun provideVendorRepository(koffieAPIService: KoffieAPIService): VendorRepository =
        VendorRepositoryImpl(koffieAPIService)
}