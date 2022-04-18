package id.shaderboi.koffie.ui.main.stores.view_model

import com.google.android.gms.maps.model.LatLng

sealed class StoresEvent {
    class Load(val location: LatLng): StoresEvent()
}