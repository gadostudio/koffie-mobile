package id.shaderboi.koffie.ui.main.orders.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.R
import id.shaderboi.koffie.core.domain.model.order.Order
import id.shaderboi.koffie.databinding.FragmentOrdersHistoryBinding
import id.shaderboi.koffie.ui.main.orders.history.adapter.HistoryOrderAdapter
import id.shaderboi.koffie.ui.main.orders.history.adapter.HistoryOrderShimmerAdapter
import id.shaderboi.koffie.ui.order_detail.OrderDetailActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class HistoryOrderFragment : Fragment() {
    private var _binding: FragmentOrdersHistoryBinding? = null
    val binding get() = _binding!!

    @Inject
    lateinit var numberFormatter: DecimalFormat

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
        val orders = mutableListOf<Order>()
        for (i in 1..10) {
            orders.add(
                Order(
                    "Janji jiwa jilid 111",
                    2,
                    38000,
                    Instant.parse("2015-12-31T12:30:00Z")
                )
            )
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(3000)
                binding.root.hideShimmer()
                binding.recyclerViewHistoryOrder.adapter =
                    HistoryOrderAdapter(
                        orders,
                        { view, coupon ->
                            val context = requireContext()
                            val intent = Intent(context, OrderDetailActivity::class.java).apply {
                                putExtra("test", 123)
                            }
                            context.startActivity(intent)
                        },
                        numberFormatter
                    )
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