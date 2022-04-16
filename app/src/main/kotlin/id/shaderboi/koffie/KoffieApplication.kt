package id.shaderboi.koffie

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import dagger.hilt.android.HiltAndroidApp
import id.shaderboi.koffie.core.domain.model.theme.Theme
import id.shaderboi.koffie.core.domain.repository.AppSettingsRepository
import id.shaderboi.koffie.util.logger.ReleaseTree
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.singleOrNull
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class KoffieApplication : Application() {
    @Inject
    lateinit var appSettingsRepository: AppSettingsRepository

    val coroutineScope = MainScope()

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        FirebaseApp.initializeApp(this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            SafetyNetAppCheckProviderFactory.getInstance()
        )

        runBlocking {
            withTimeout(50) {
                appSettingsRepository.flow.firstOrNull()?.let { appSettings ->
                    when (appSettings.theme) {
                        Theme.FollowSystem -> {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_UNSPECIFIED)
                        }
                        Theme.Dark -> {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        }
                        Theme.Light -> {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        }
                    }
                }
            }
        }

        coroutineScope.launch {
            appSettingsRepository.flow.collectLatest { appSettings ->
                when (appSettings.theme) {
                    Theme.FollowSystem -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_UNSPECIFIED)
                    }
                    Theme.Dark -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    Theme.Light -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        }
    }
}