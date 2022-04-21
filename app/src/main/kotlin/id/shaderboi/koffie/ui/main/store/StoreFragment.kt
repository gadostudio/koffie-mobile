package id.shaderboi.koffie.ui.main.store

import Permission
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.R
import id.shaderboi.koffie.core.domain.model.common.Coordinate
import id.shaderboi.koffie.core.util.Resource
import id.shaderboi.koffie.databinding.FragmentStoreBinding
import id.shaderboi.koffie.ui.checkout.CheckoutActivity
import id.shaderboi.koffie.ui.coupons.CouponsActivity
import id.shaderboi.koffie.ui.location.LocationActivity
import id.shaderboi.koffie.ui.main.store.adapter.CategorizedProductAdapter
import id.shaderboi.koffie.ui.main.store.adapter.CategorizedProductShimmerAdapter
import id.shaderboi.koffie.ui.main.store.view_model.CartViewModel
import id.shaderboi.koffie.ui.main.store.view_model.StoreEvent
import id.shaderboi.koffie.ui.main.store.view_model.StoreViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.EasyPermissions
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class StoreFragment : Fragment() {
    private var _binding: FragmentStoreBinding? = null
    val binding get() = _binding!!

    private val homeViewModel by viewModels<StoreViewModel>()
    private val cartViewModel by viewModels<CartViewModel>()
    private val args by navArgs<StoreFragmentArgs>()

    @Inject
    lateinit var numberFormatter: DecimalFormat

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }
    private var cancellationTokenSource = CancellationTokenSource()

    private val takePromoCoupon = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel.onEvent(StoreEvent.Load(args.storeId))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)

        args.storeWithDistance?.let { storeWithDistance ->
            binding.apply {
                shimmerFrameLayoutStore.hideShimmer()

                textViewTitle.text = storeWithDistance.store.name
                textViewDistance.text = "${numberFormatter.format(storeWithDistance.distance)} km"
            }
        }

        askPermission()
        collectUIEvent()
        setupView()

        return binding.root
    }

    private fun collectUIEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    homeViewModel.products.collectLatest { res ->
                        when (res) {
                            is Resource.Error -> {
                            }
                            is Resource.Loaded -> {
                                binding.shimmerFrameLayoutProducts.hideShimmer()
                                binding.recyclerViewProducts.adapter =
                                    CategorizedProductAdapter(
                                        res.data,
                                        numberFormatter
                                    ) { product ->
                                        val navController = findNavController()
                                        val action =
                                            StoreFragmentDirections.actionNavigationHomeStoreToNavigationHomeProduct(
                                                product
                                            )
                                        navController.navigate(action)
                                    }
                            }
                            is Resource.Loading -> {
                                binding.recyclerViewProducts.adapter =
                                    CategorizedProductShimmerAdapter()
                            }
                        }
                    }
                }

                launch {
                    homeViewModel.store.collectLatest { res ->
                        when (res) {
                            is Resource.Error -> {}
                            is Resource.Loaded -> {
                                binding.apply {
                                    shimmerFrameLayoutStore.hideShimmer()

                                    textViewTitle.text = res.data.name
                                    fusedLocationClient.getCurrentLocation(
                                        LocationRequest.PRIORITY_HIGH_ACCURACY,
                                        cancellationTokenSource.token
                                    ).addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            val location = task.result
                                            val locationCoordinate =
                                                Coordinate(location.latitude, location.longitude)
                                            val distance =
                                                res.data.coordinate.distanceTo(locationCoordinate)
                                            textViewDistance.text =
                                                "${numberFormatter.format(distance / 1000F)} km"
                                        }
                                    }

                                    includeViewPromo.textViewPromoCaption.background = null
                                    textViewDistance.background = null
                                    textViewTitle.background = null
                                }
                            }
                            is Resource.Loading -> {
                                val context = requireContext()
                                if (args.storeWithDistance == null) {
                                    binding.apply {
                                        includeViewPromo.textViewPromoCaption.background =
                                            ContextCompat.getDrawable(context, R.color.placeholder)
                                        textViewDistance.background =
                                            ContextCompat.getDrawable(context, R.color.placeholder)
                                        textViewTitle.background =
                                            ContextCompat.getDrawable(context, R.color.placeholder)
                                    }
                                }
                            }
                        }
                    }
                }

                launch {
                    cartViewModel.cartFlow.collectLatest { cartItems ->
                        val isShowCheckoutBar = cartItems.isNotEmpty()
                        showCheckoutBar(isShowCheckoutBar)

                        val estimatedTotalPrice =
                            cartItems.sumOf { cartItem ->
                                cartItem.product.price - (cartItem.product.discount ?: 0)
                            }

                        binding.apply {
                            textViewCheckoutItems.text = "${cartItems.size} items"
                            textViewCheckoutPrice.text =
                                "Rp ${numberFormatter.format(estimatedTotalPrice)}"

                            linearLayoutCheckout.setOnClickListener {
                                if (!isShowCheckoutBar) return@setOnClickListener

                                val intent = Intent(requireContext(), CheckoutActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showCheckoutBar(isShowCheckoutBar: Boolean) {
        val constraintSet =
            ConstraintSet().apply {
                clone(binding.constraintLayoutMain)
                if (isShowCheckoutBar) {
                    clear(binding.linearLayoutCheckout.id, ConstraintSet.TOP)
                    connect(
                        binding.linearLayoutCheckout.id,
                        ConstraintSet.BOTTOM,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.BOTTOM
                    )
                } else {
                    clear(binding.linearLayoutCheckout.id, ConstraintSet.BOTTOM)
                    connect(
                        binding.linearLayoutCheckout.id,
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.BOTTOM
                    )
                }
            }

        val transition = ChangeBounds().apply {
            interpolator = AnticipateOvershootInterpolator(1.0f)
            duration = 600
        }
        TransitionManager.beginDelayedTransition(binding.constraintLayoutMain, transition)
        constraintSet.applyTo(binding.constraintLayoutMain)
    }

    private fun setupView() {
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.spacer8dp)!!
        );

        binding.apply {
            recyclerViewProducts.apply {
                addItemDecoration(dividerItemDecoration)
            }

            includeViewPromo.root.setOnClickListener {
                val intent = Intent(requireContext(), CouponsActivity::class.java)
                takePromoCoupon.launch(intent)
            }

            linearLayoutDistance.setOnClickListener {
                startActivity(Intent(requireContext(), LocationActivity::class.java))
            }
        }
    }

    private fun askPermission() {
        if (EasyPermissions.hasPermissions(requireContext(), *Permission.location)) {
            return
        }

        EasyPermissions.requestPermissions(
            this,
            "You need to accept location permission to be able to use delivery service",
            Permission.REQUEST_CODE_LOCATION,
            *Permission.location
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}