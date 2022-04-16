package id.shaderboi.koffie.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.shaderboi.koffie.R
import id.shaderboi.koffie.core.domain.model.store.products.Product
import id.shaderboi.koffie.databinding.ItemProductBinding
import java.text.DecimalFormat

class ProductsShimmerAdapter :
    RecyclerView.Adapter<ProductsShimmerAdapter.ViewHolder>() {
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
        holder.binding.apply {
            textViewTitle.apply {
                background = ContextCompat.getDrawable(context, R.color.placeholder)
            }
            textViewDescription.apply {
                background = ContextCompat.getDrawable(context, R.color.placeholder)
                minLines = 2
            }
            textViewPrice.apply {
                background = ContextCompat.getDrawable(context, R.color.placeholder)
            }
//            imageViewProduct
        }
    }

    override fun getItemCount(): Int = 3
}