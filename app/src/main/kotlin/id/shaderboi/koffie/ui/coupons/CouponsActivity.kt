package id.shaderboi.koffie.ui.coupons

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.R
import id.shaderboi.koffie.core.domain.model.coupon.Coupon
import id.shaderboi.koffie.databinding.ActivityCouponsBinding
import id.shaderboi.koffie.ui.coupons.adapter.CouponAdapter
import id.shaderboi.koffie.ui.coupons.adapter.CouponShimmerAdapter
import kotlinx.coroutines.delay

@AndroidEntryPoint
class CouponsActivity : AppCompatActivity() {
    private var _binding: ActivityCouponsBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityCouponsBinding.inflate(layoutInflater)

        collectUIEvent()
        setupView()

        setContentView(binding.root)
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
            binding.shimmerFrameLayoutMain.hideShimmer()
            binding.recyclerViewCoupons.adapter = CouponAdapter(coupons) { view, coupon ->
                val intent = Intent()
                intent.putExtra("SELECTED_COUPON", coupon.id)
                setResult(1, intent)
            }
        }
    }

    private fun setupView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(this, R.drawable.divider8dp)!!
        );
        binding.recyclerViewCoupons.apply {
            addItemDecoration(dividerItemDecoration)
            adapter = CouponShimmerAdapter()
        }
    }
}