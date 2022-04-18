package id.shaderboi.koffie.ui.main.stores.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.shaderboi.koffie.R
import id.shaderboi.koffie.databinding.ItemStoreBinding

class StoresShimmerAdapter : RecyclerView.Adapter<StoresShimmerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemStoreBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.binding.root.context
        holder.binding.apply {
            textViewTitle.background = ContextCompat.getDrawable(context, R.color.placeholder)
            textViewDistance.background = ContextCompat.getDrawable(context, R.color.placeholder)
            textViewAddress.apply {
                background = ContextCompat.getDrawable(context, R.color.placeholder)
                minLines = 2
            }
        }
    }

    override fun getItemCount(): Int = 10
}