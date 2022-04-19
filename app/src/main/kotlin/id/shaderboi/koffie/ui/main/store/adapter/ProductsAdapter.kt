package id.shaderboi.koffie.ui.main.store.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.shaderboi.koffie.core.domain.model.store.products.Product
import id.shaderboi.koffie.databinding.ItemProductBinding
import java.text.DecimalFormat

class ProductsAdapter(
    private val products: List<Product>,
    private val numberFormatter: DecimalFormat,
    private val onProductClick: (product: Product) -> Unit
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
            textViewTitle.text = product.name
            textViewDescription.text = product.description
            textViewPrice.text = numberFormatter.format(product.price)
            product.discount?.let { discount ->
                textViewDiscounted.text = numberFormatter.format(product.discount)
                textViewPrice.paintFlags = textViewPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }

            Picasso.get()
                .load(product.imageUrl)
                .into(imageViewProduct)

            root.setOnClickListener {
                onProductClick(product)
            }
        }
    }

    override fun getItemCount(): Int = products.size
}