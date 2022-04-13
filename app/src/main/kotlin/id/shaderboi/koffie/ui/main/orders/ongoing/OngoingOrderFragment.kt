package id.shaderboi.koffie.ui.main.orders.ongoing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.databinding.FragmentOrdersBinding

@AndroidEntryPoint
class OngoingOrderFragment: Fragment() {
    private var _binding: FragmentOrdersBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)

        setupView()

        return binding.root
    }

    private fun setupView() {
    }
}