package id.shaderboi.koffie.core.data.data_source.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import id.shaderboi.koffie.core.domain.model.theme.Theme

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

data class AppSettings(
    val theme: Theme
)

val APP_THEME = intPreferencesKey("APP_THEME")
