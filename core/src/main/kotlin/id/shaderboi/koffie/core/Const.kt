import android.Manifest
import android.os.Build

object IntentExtra {
    const val WITH_SPLASH_SCREEN = "WITH_SPLASH_SCREEN"
    const val PRODUCT_ID = "PRODUCT_ID"
}

object Permission {
    const val REQUEST_CODE_LOCATION = 0

    val location: Array<String>
        get() = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
}

object Bundle {
    val MAPVIEW_KEY = "MAPVIEW_BUNDLE_KEY"
}
