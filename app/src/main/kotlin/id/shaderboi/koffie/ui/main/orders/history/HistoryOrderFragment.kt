package id.shaderboi.koffie.ui.main.orders.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.R
import id.shaderboi.koffie.core.domain.model.Coupon
import id.shaderboi.koffie.databinding.FragmentOrdersHistoryBinding
import id.shaderboi.koffie.ui.main.orders.history.adapter.HistoryOrderAdapter
import id.shaderboi.koffie.ui.main.orders.history.adapter.HistoryOrderShimmerAdapter
import id.shaderboi.koffie.ui.main.orders.ongoing.adapter.OngoingOrderShimmerAdapter
import id.shaderboi.koffie.ui.order_detail.OrderDetailActivity
import kotlinx.coroutines.delay

@AndroidEntryPoint
class HistoryOrderFragment : Fragment() {
    private var _binding: FragmentOrdersHistoryBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersHistoryBinding.inflate(inflater, container, false)

        collectUIEvent()
        setupView()

        return binding.root
    }

    private fun collectUIEvent() {
        val coupons = mutableListOf<Coupon>()
        for (i in 1..10) {
            coupons.add(
                Coupon(
                    0,
                    "ABC",
                    "asdasdasdasd\nzxczcxzcxzcxc"
                )
            )
            coupons.add(
                Coupon(
                    1,
                    "asdzxzx",
                    "asdasdasdasd\nzxczcxzcxzcxc"
                )
            )
        }
        lifecycleScope.launchWhenCreated {
            delay(3000)
            binding.root.hideShimmer()
            binding.recyclerViewHistoryOrder.adapter =
                HistoryOrderAdapter(coupons) { view, coupon ->
                    val context = requireContext()
                    val intent = Intent(context, OrderDetailActivity::class.java).apply {
                        putExtra("test", 123)
                    }
                    context.startActivity(intent)
                }
        }
    }

    private fun setupView() {
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.divider8dp)!!
        );
        binding.recyclerViewHistoryOrder.apply {
            addItemDecoration(dividerItemDecoration)
            adapter = HistoryOrderShimmerAdapter()
        }
    }
}