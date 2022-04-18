package id.shaderboi.koffie.ui.main.store.view_model

sealed class StoreEvent {
    class Load(val storeId: Int): StoreEvent()
}