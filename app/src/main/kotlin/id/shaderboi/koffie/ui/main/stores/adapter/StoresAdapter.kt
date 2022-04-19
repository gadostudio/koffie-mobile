package id.shaderboi.koffie.ui.main.stores.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.shaderboi.koffie.core.domain.model.store.StoreWithDistance
import id.shaderboi.koffie.databinding.ItemStoreBinding
import java.text.DecimalFormat

class StoresAdapter(
    val stores: List<StoreWithDistance>,
    val numberFormatter: DecimalFormat,
    val onClick: (store: StoreWithDistance) -> Unit
) :
    RecyclerView.Adapter<StoresAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemStoreBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store = stores[position]
        holder.binding.apply {
            val distance = numberFormatter.format(store.distance / 1000F)

            textViewTitle.text = store.store.name
            textViewDistance.text = "${distance}km"
            textViewAddress.text = store.store.address
            root.setOnClickListener {
                onClick(store)
            }
        }
    }

    override fun getItemCount(): Int = stores.size
}