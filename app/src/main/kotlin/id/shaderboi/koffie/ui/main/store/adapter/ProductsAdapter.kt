package id.shaderboi.koffie.ui.main.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.shaderboi.koffie.core.domain.model.store.products.Product
import id.shaderboi.koffie.databinding.ItemProductBinding
import java.text.DecimalFormat

class ProductsAdapter(
    private val products: List<Product>,
    private val numberFormatter: DecimalFormat
) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemProductBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.binding.apply {
//            textViewTitle.text = product.name
//            textViewDescription.text = product.description
//            textViewPrice.text = numberFormatter.format(product.price)
//            product.discount?.let { discount ->
//                textViewDiscount.text = numberFormatter.format(product.discount)
//            }
//            imageViewProduct
        }
    }

    override fun getItemCount(): Int = products.size
}