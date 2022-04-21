package id.shaderboi.koffie.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.shaderboi.koffie.core.BuildConfig
import id.shaderboi.koffie.core.data.auth.Auth
import id.shaderboi.koffie.core.data.auth.AuthFirebase
import id.shaderboi.koffie.core.data.data_source.local.database.KoffieDatabase
import id.shaderboi.koffie.core.data.data_source.network.KoffieAPIService
import id.shaderboi.koffie.core.data.repository.CartRepositoryImpl
import id.shaderboi.koffie.core.data.repository.FakeStoreRepository
import id.shaderboi.koffie.core.domain.repository.CartRepository
import id.shaderboi.koffie.core.domain.repository.StoreRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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

    @Provides
    fun provideKoffieServiceAPI(retrofit: Retrofit): KoffieAPIService =
        retrofit.create(KoffieAPIService::class.java)

    @Provides
    fun provideAuth(): Auth = AuthFirebase()

    @Provides
    fun provideKoffieDatabase(@ApplicationContext context: Context): KoffieDatabase =
        Room.databaseBuilder(context, KoffieDatabase::class.java, KoffieDatabase.NAME)
            .build()

    @Provides
    fun provideCartRepository(koffieDatabase: KoffieDatabase): CartRepository =
        CartRepositoryImpl(koffieDatabase)

    @Provides
    fun provideVendorRepository(koffieAPIService: KoffieAPIService): StoreRepository =
        FakeStoreRepository()
//        StoreRepositoryImpl(koffieAPIService)
}