package id.shaderboi.koffie.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.shaderboi.koffie.core.data.data_source.network.KoffieAPIService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl("")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun provideKoffieServiceAPI(retrofit: Retrofit): KoffieAPIService =
        retrofit.create(KoffieAPIService::class.java)

//    @Provides
//    fun provideMidtransUIFlow(@ApplicationContext context: Context) {
//        SdkUIFlowBuilder
//            .init()
//            .setClientKey()
//            .setContext(context)
//            .setMerchantBaseUrl("")
//            .enableLog(true)
//            .buildSDK();
//    }
}