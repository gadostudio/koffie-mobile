package id.shaderboi.koffie.ui.location

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        setupMap()
    }

    private fun setupMap() {
        val shop = LatLng(-31.0, 151.0)
        map.addMarker(
            MarkerOptions()
                .position(shop)
                .title("Shop")
        )
        map.moveCamera(CameraUpdateFactory.newLatLng(shop))
    }

}