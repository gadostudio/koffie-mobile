package id.shaderboi.koffie.ui.main.orders.ongoing.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.shaderboi.koffie.R
import id.shaderboi.koffie.databinding.ItemCouponBinding

class OngoingOrderShimmerAdapter() :
    RecyclerView.Adapter<OngoingOrderShimmerAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCouponBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCouponBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.binding.root.context
        holder.binding.apply {
            textViewDescription.apply {
                background = ContextCompat.getDrawable(context, R.color.placeholder)
                minLines = 3
            }
            textViewTitle.background =
                ContextCompat.getDrawable(context, R.color.placeholder)
        }
    }

    override fun getItemCount(): Int = 10
}