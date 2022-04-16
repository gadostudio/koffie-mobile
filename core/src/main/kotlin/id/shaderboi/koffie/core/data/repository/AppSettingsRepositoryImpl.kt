package id.shaderboi.koffie.core.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import id.shaderboi.koffie.core.data.data_source.local.datastore.APP_THEME
import id.shaderboi.koffie.core.data.data_source.local.datastore.AppSettings
import id.shaderboi.koffie.core.data.data_source.local.datastore.settingsDataStore
import id.shaderboi.koffie.core.domain.model.theme.Theme
import id.shaderboi.koffie.core.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppSettingsRepositoryImpl @Inject constructor(
    context: Context
): AppSettingsRepository {
    private val settingsDataStore = context.settingsDataStore
    override val flow = settingsDataStore.data.map { pref ->
        val theme = Theme.values()[pref[APP_THEME] ?: Theme.FollowSystem.ordinal]
        AppSettings(theme)
    }

    override suspend fun setTheme(theme: Theme) {
        settingsDataStore.edit { pref ->
            pref[APP_THEME] = theme.ordinal
        }
    }
}