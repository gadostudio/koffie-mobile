package id.shaderboi.koffie.ui.main.orders.history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.shaderboi.koffie.core.domain.model.Coupon
import id.shaderboi.koffie.databinding.ItemCouponBinding
import id.shaderboi.koffie.databinding.ItemOrderBinding

class HistoryOrderAdapter(
    private val coupons: List<Coupon>,
    private val onClick: (view: View, coupon: Coupon) -> Unit
) :
    RecyclerView.Adapter<HistoryOrderAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemOrderBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coupon = coupons[position]
        holder.binding.apply {
            textViewTotalItem.text = "1"
            textViewTitle.text = coupon.title
            root.setOnClickListener { view ->
                onClick(view, coupon)
            }
        }
    }

    override fun getItemCount(): Int = coupons.size
}