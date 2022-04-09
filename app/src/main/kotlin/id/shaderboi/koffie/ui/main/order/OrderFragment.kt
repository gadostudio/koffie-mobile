package id.shaderboi.koffie.ui.main.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.databinding.FragmentOrderBinding

@AndroidEntryPoint
class OrderFragment: Fragment() {
    private var _binding: FragmentOrderBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }
}