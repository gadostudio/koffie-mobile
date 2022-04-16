package id.shaderboi.koffie.core.domain.repository

import androidx.datastore.preferences.core.edit
import id.shaderboi.koffie.core.data.data_source.local.datastore.APP_THEME
import id.shaderboi.koffie.core.data.data_source.local.datastore.AppSettings
import id.shaderboi.koffie.core.domain.model.theme.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AppSettingsRepository {
    val flow: Flow<AppSettings>

    suspend fun setTheme(theme: Theme)
}