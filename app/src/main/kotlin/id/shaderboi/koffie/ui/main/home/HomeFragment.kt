package id.shaderboi.koffie.ui.main.home

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
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.R
import id.shaderboi.koffie.core.util.Resource
import id.shaderboi.koffie.databinding.FragmentHomeBinding
import id.shaderboi.koffie.ui.coupons.CouponsActivity
import id.shaderboi.koffie.ui.main.home.adapter.CategorizedProductAdapter
import id.shaderboi.koffie.ui.main.home.adapter.CategorizedProductShimmerAdapter
import id.shaderboi.koffie.ui.main.home.view_model.HomeEvent
import id.shaderboi.koffie.ui.main.home.view_model.HomeViewModel
import id.shaderboi.koffie.ui.main.orders.history.adapter.HistoryOrderShimmerAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.EasyPermissions
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var numberFormatter: DecimalFormat

    private val takePromoCoupon = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel.onEvent(HomeEvent.Load(1))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

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
                                binding.shimmerFrameLayoutMain.stopShimmer()
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
                            is Resource.Loaded -> {}
                            is Resource.Loading -> {}
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