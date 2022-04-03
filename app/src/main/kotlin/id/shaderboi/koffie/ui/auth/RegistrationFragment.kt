package id.shaderboi.koffie.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.databinding.FragmentRegistrationBinding
import id.shaderboi.koffie.databinding.FragmentSigninBinding
import id.shaderboi.koffie.databinding.FragmentVerifyBinding
import id.shaderboi.koffie.ui.auth.view_model.SignInEvent
import id.shaderboi.koffie.ui.auth.view_model.SignInViewModel
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    val binding get() = _binding!!

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private val signInViewModel by activityViewModels<SignInViewModel>()

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

        binding.buttonSignIn.setOnClickListener {
            val phoneNumber = binding.editTextPhone.text.toString()
            signInViewModel.onEvent(SignInEvent.Verify(phoneNumber, requireActivity(), navController))
        }
    }
}