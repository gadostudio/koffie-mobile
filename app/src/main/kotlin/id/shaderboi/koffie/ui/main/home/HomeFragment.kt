package id.shaderboi.koffie.ui.main.home

import Permission
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.databinding.FragmentHomeBinding
import id.shaderboi.koffie.ui.main.home.view_model.StoreFrontViewModel
import id.shaderboi.koffie.ui.coupons.CouponsActivity
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    private val homeViewModel by viewModels<StoreFrontViewModel>()

    private val takePromoCoupon = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        askPermission()
        setupView()

        return binding.root
    }

    private fun setupView() {
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