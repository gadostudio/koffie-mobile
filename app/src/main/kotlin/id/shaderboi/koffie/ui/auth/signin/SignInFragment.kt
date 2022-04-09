package id.shaderboi.koffie.ui.auth.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.databinding.FragmentSigninBinding
import id.shaderboi.koffie.ui.common.view_model.AuthEvent
import id.shaderboi.koffie.ui.common.view_model.AuthViewModel

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private var _binding: FragmentSigninBinding? = null
    val binding get() = _binding!!

    private val signInViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater)

        setupView()

        return binding.root
    }

    private fun setupView() {
        val navController = findNavController()
        binding.buttonSignInSignUp.setOnClickListener {
            val phoneNumber = binding.editTextPhoneNumber.text.toString()
            signInViewModel.onEvent(
                AuthEvent.Auth(
                    phoneNumber,
                    requireActivity()
                ) {
                    val action =
                        SignInFragmentDirections.actionNavigationAuthSigninToNavigationAuthVerify()
                    navController.navigate(action)
                }
            )
        }
    }
}