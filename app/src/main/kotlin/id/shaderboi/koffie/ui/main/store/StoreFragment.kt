package id.shaderboi.koffie.ui.main.store

import Permission
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.R
import id.shaderboi.koffie.core.domain.model.common.Coordinate
import id.shaderboi.koffie.core.util.Resource
import id.shaderboi.koffie.databinding.FragmentStoreBinding
import id.shaderboi.koffie.ui.coupons.CouponsActivity
import id.shaderboi.koffie.ui.location.LocationActivity
import id.shaderboi.koffie.ui.main.store.adapter.CategorizedProductAdapter
import id.shaderboi.koffie.ui.main.store.adapter.CategorizedProductShimmerAdapter
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
                                    CategorizedProductAdapter(res.data, numberFormatter)
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
                                                "${numberFormatter.format(distance)} km"
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
            }
        }
    }

    private fun setupView() {
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.divider8dp)!!
        );
        binding.recyclerViewProducts.apply {
            addItemDecoration(dividerItemDecoration)
        }

        binding.includeViewPromo.root.setOnClickListener {
            val intent = Intent(requireContext(), CouponsActivity::class.java)
            takePromoCoupon.launch(intent)
        }

        binding.linearLayoutDistance.setOnClickListener {
            startActivity(Intent(requireContext(), LocationActivity::class.java))
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