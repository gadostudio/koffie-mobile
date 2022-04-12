package id.shaderboi.koffie.ui.main.misc

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.core.data.auth.Auth
import id.shaderboi.koffie.databinding.FragmentMiscBinding
import id.shaderboi.koffie.ui.auth.AuthActivity
import javax.inject.Inject

@AndroidEntryPoint
class MiscFragment : Fragment() {
    private var _binding: FragmentMiscBinding? = null
    val binding get() = _binding!!

    @Inject
    lateinit var auth: Auth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMiscBinding.inflate(inflater, container, false)

        setupView()

        return binding.root
    }

    private fun setupView() {
        val user = auth.userInfo
        binding.textViewPhoneNumber.text = user.phoneNumber.ifBlank { "Phone number not set" }
        binding.textViewName.text =
            user.displayName?.ifBlank { "Display name not set" } ?: "Display name not set"
//        binding.textViewEmail.isVisible = user.email.isBlank()
//        binding.textViewEmail.text = user.email


        val signOutDialog = AlertDialog.Builder(requireContext())
            .setPositiveButton("Yes") { dialog, id ->
                auth.signOut()
            }
            .setNegativeButton("No") { dialog, id -> }
            .create()
        binding.buttonSignOut.setOnClickListener {
            signOutDialog.show()
        }
    }
}