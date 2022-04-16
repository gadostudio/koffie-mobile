package id.shaderboi.koffie.ui.main.orders.history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.shaderboi.koffie.core.domain.model.order.Order
import id.shaderboi.koffie.databinding.ItemOrderBinding
import java.text.DecimalFormat
import javax.inject.Inject

class HistoryOrderAdapter(
    private val orders: List<Order>,
    private val onClick: (view: View, coupon: Order) -> Unit,
    private val numberFormatter: DecimalFormat
) : RecyclerView.Adapter<HistoryOrderAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemOrderBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        holder.binding.apply {
            textViewTitle.text = order.restaurantName
            textViewTotalItem.text = order.itemAmount.toString()
            textViewTotalPrice.text = numberFormatter.format(order.totalPrice)
            textViewDatetime.text = order.createdAt.toString()
            root.setOnClickListener { view ->
                onClick(view, order)
            }
        }
    }

    override fun getItemCount(): Int = orders.size
}