package id.shaderboi.koffie.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.shaderboi.koffie.core.data.repository.AppSettingsRepositoryImpl
import id.shaderboi.koffie.core.domain.repository.AppSettingsRepository
import java.text.DecimalFormat

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideNumberFormatter(): DecimalFormat = DecimalFormat("#,###.00")

    @Provides
    fun provideAppSettingsRepository(@ApplicationContext context: Context): AppSettingsRepository =
        AppSettingsRepositoryImpl(context)
}
