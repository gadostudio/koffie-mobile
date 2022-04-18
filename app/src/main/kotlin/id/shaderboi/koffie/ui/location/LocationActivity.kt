package id.shaderboi.koffie.ui.location

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.databinding.ActivityLocationBinding
import Bundle as BundleConst

@AndroidEntryPoint
class LocationActivity : AppCompatActivity() {
    private lateinit var map: GoogleMap

    private var _binding: ActivityLocationBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLocationBinding.inflate(layoutInflater)

        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(BundleConst.MAPVIEW_KEY)
        }

        binding.mapView.onCreate(mapViewBundle)
        binding.mapView.getMapAsync { _map ->
            map = _map
            setupMap()
        }

        setContentView(binding.root)
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

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        var mapViewBundle = outState.getBundle(BundleConst.MAPVIEW_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(BundleConst.MAPVIEW_KEY, mapViewBundle)
        }

        binding.mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onLowMemory() {
        super.onLowMemory()

        binding.mapView.onLowMemory()
    }

    override fun onPause() {
        binding.mapView.onPause()

        super.onPause()
    }

    override fun onStop() {
        super.onStop()

        binding.mapView.onStop()
    }

    override fun onStart() {
        super.onStart()

        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()

        binding.mapView.onResume()
    }

    override fun onDestroy() {
        binding.mapView.onDestroy()

        super.onDestroy()
    }

}