package id.shaderboi.koffie.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.shaderboi.koffie.R
import id.shaderboi.koffie.databinding.ItemCategorizedProductBinding

class CategorizedProductShimmerAdapter() :
    RecyclerView.Adapter<CategorizedProductShimmerAdapter.ViewHolder>() {
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
        holder.binding.apply {
            textViewCategoryName.apply {
                background = ContextCompat.getDrawable(context, R.color.placeholder)
            }
            recyclerViewProducts.adapter = ProductsShimmerAdapter()
        }

    }

    override fun getItemCount(): Int = 10
}
