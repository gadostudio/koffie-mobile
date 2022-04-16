package id.shaderboi.koffie.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.shaderboi.koffie.core.domain.model.store.products.CategorizedProduct
import id.shaderboi.koffie.databinding.ItemCategorizedProductBinding
import java.text.DecimalFormat

class CategorizedProductAdapter(
    private val categorizedProducts: List<CategorizedProduct>,
    private val numberFormatter: DecimalFormat
) :
    RecyclerView.Adapter<CategorizedProductAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemCategorizedProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemCategorizedProductBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categorizedProduct = categorizedProducts[position]
        holder.binding.apply {
            textViewCategoryName.text = categorizedProduct.categoryName
            recyclerViewProducts.adapter =
                ProductsAdapter(categorizedProduct.products, numberFormatter)
        }
    }

    override fun getItemCount(): Int = categorizedProducts.size
}