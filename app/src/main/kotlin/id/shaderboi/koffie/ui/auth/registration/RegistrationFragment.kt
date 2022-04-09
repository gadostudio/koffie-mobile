package id.shaderboi.koffie.ui.auth.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.databinding.FragmentRegistrationBinding
import id.shaderboi.koffie.ui.common.view_model.AuthEvent
import id.shaderboi.koffie.ui.common.view_model.AuthViewModel

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    val binding get() = _binding!!

    private val signInViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        setupView()

        return binding.root
    }

    private fun setupView() {
        val navController = binding.root.findNavController()

        binding.buttonSignInSignUp.setOnClickListener {
            val phoneNumber = binding.editTextVerificationCode1.text.toString()
            signInViewModel.onEvent(AuthEvent.Verify(phoneNumber, requireActivity(), navController))
        }
    }
}