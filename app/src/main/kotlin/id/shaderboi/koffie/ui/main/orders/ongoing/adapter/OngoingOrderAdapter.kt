package id.shaderboi.koffie.ui.main.orders.ongoing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.shaderboi.koffie.core.domain.model.Coupon
import id.shaderboi.koffie.databinding.ItemCouponBinding

class OngoingOrderAdapter(
    private val coupons: List<Coupon>,
    private val onClick: (view: View, coupon: Coupon) -> Unit
) :
    RecyclerView.Adapter<OngoingOrderAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCouponBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCouponBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coupon = coupons[position]
        holder.binding.apply {
            textViewDescription.text = coupon.description
            textViewTitle.text = coupon.title
            root.setOnClickListener { view ->
                onClick(view, coupon)
            }
        }
    }

    override fun getItemCount(): Int = coupons.size
}