package id.shaderboi.koffie.ui.main.orders.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.shaderboi.koffie.R
import id.shaderboi.koffie.databinding.ItemOrderBinding

class HistoryOrderShimmerAdapter :
    RecyclerView.Adapter<HistoryOrderShimmerAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemOrderBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.binding.root.context
        holder.binding.apply {
            textViewTotalItem.background =
                ContextCompat.getDrawable(context, R.color.placeholder)
            textViewTitle.background =
                ContextCompat.getDrawable(context, R.color.placeholder)
            textViewDatetime.background =
                ContextCompat.getDrawable(context, R.color.placeholder)
            textViewTotalPrice.background =
                ContextCompat.getDrawable(context, R.color.placeholder)
        }
    }

    override fun getItemCount(): Int = 10
}