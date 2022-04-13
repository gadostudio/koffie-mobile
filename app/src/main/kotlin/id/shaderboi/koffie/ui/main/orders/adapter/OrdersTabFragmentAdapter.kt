package id.shaderboi.koffie.ui.main.orders.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.shaderboi.koffie.ui.main.orders.history.HistoryOrderFragment
import id.shaderboi.koffie.ui.main.orders.ongoing.OngoingOrderFragment

class OrdersTabFragmentAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    private val fragments = arrayOf<Fragment>(
        HistoryOrderFragment(),
        OngoingOrderFragment(),
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}