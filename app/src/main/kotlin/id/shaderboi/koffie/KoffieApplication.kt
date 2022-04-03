package id.shaderboi.koffie

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import id.shaderboi.koffie.util.logger.ReleaseTree
import timber.log.Timber

@HiltAndroidApp
class KoffieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }
}