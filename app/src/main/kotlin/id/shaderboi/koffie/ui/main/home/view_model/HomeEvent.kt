package id.shaderboi.koffie.ui.main.home.view_model

sealed class HomeEvent {
    class Load(val storeId: Int): HomeEvent()
}