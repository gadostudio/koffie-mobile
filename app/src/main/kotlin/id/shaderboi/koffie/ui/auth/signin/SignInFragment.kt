package id.shaderboi.koffie.ui.auth.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.R
import id.shaderboi.koffie.databinding.FragmentSigninBinding
import id.shaderboi.koffie.ui.auth.signin.view_model.SignInEvent
import id.shaderboi.koffie.ui.auth.signin.view_model.SignInUIEvent
import id.shaderboi.koffie.ui.auth.signin.view_model.SignInViewModel
import id.shaderboi.koffie.ui.common.view_model.AuthViewModel
import id.shaderboi.koffie.util.StringDisplay
import io.noties.markwon.Markwon
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private var _binding: FragmentSigninBinding? = null
    val binding get() = _binding!!

    private val signInViewModel by viewModels<SignInViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var markwon: Markwon

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSigninBinding.inflate(inflater)

        collectUIEvent()
        setupView()

        return binding.root
    }

    private fun collectUIEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    signInViewModel.uiFlow.collectLatest { event ->
                        when (event) {
                            is SignInUIEvent.ShowErrorMessage -> {
                                binding.textViewErrorMessage.isVisible = true
                                binding.textViewErrorMessage.text = when (event.message) {
                                    is StringDisplay.String -> event.message.string
                                    is StringDisplay.StringRes -> requireContext().getString(event.message.idRes)
                                }
                            }
                            is SignInUIEvent.Finished -> {
                                val navController = findNavController()
                                val action = SignInFragmentDirections
                                    .actionNavigationAuthSigninToNavigationAuthVerify(
                                        event.verificationId
                                    )
                                navController.navigate(action)
                            }
                            is SignInUIEvent.IsEnableInput -> event.enable.let { enable ->
                                if (!enable) {
                                    binding.textViewErrorMessage.isVisible = false
                                }

                                binding.buttonSigninSignup.isEnabled = enable
                                binding.editTextPhoneNumber.isEnabled = enable
                            }
                        }
                    }
                }

                launch {
                    authViewModel.authenticationFlow.collectLatest { user ->
                        if (user?.isRegistered == false) {
                            val navController = findNavController()

                            val action = SignInFragmentDirections
                                .actionNavigationAuthSigninToNavigationAuthRegistration()
                            navController.navigate(action)
                        }
                    }
                }
            }
        }
    }

    private fun setupView() {
        binding.apply {
            buttonSigninSignup.setOnClickListener {
                val phoneNumberText = binding.editTextPhoneNumber.text.toString()
                val phoneNumber = "+62${phoneNumberText}"
                signInViewModel.onEvent(SignInEvent.SignIn(phoneNumber, requireActivity()))
            }
            markwon.setMarkdown(textViewPrivacyPolicy, requireContext().getString(R.string.signin_signup_agreement))
        }
    }
}