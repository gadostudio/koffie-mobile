package id.shaderboi.koffie.ui.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.databinding.FragmentProductBinding
import id.shaderboi.koffie.ui.product.view_model.ProductEvent
import id.shaderboi.koffie.ui.product.view_model.ProductViewModel
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class ProductFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentProductBinding? = null
    val binding get() = _binding!!

    val args by navArgs<ProductFragmentArgs>()

    private val productViewModel by viewModels<ProductViewModel>()

    @Inject
    lateinit var numberFormatter: DecimalFormat

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)

        setupView()

        return binding.root
    }

    private fun setupView() {
        binding.apply {
            (dialog as BottomSheetDialog?)?.let { bottomSheetDialog ->
                bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                bottomSheetDialog.behavior.skipCollapsed = true
            }

            textViewName.text = args.product.name
            textViewPrice.text = numberFormatter.format(args.product.price)

            buttonAddToCart.setOnClickListener {
                productViewModel.onEvent(ProductEvent.AddProductToCart(args.product))
            }

            Picasso
                .get()
                .load(args.product.imageUrl)
                .into(imageViewProduct)
        }
    }
}