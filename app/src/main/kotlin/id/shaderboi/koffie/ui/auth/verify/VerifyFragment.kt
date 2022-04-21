package id.shaderboi.koffie.ui.auth.verify

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.R
import id.shaderboi.koffie.databinding.FragmentVerifyBinding
import id.shaderboi.koffie.ui.auth.verify.view_model.VerifyEvent
import id.shaderboi.koffie.ui.auth.verify.view_model.VerifyUIEvent
import id.shaderboi.koffie.ui.auth.verify.view_model.VerifyViewModel
import id.shaderboi.koffie.ui.main.MainActivity
import id.shaderboi.koffie.util.StringDisplay
import io.noties.markwon.Markwon
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class VerifyFragment : Fragment() {
    private var _binding: FragmentVerifyBinding? = null
    val binding get() = _binding!!

    private val verifyViewModel by viewModels<VerifyViewModel>()
    val args by navArgs<VerifyFragmentArgs>()

    @Inject
    lateinit var markwon: Markwon

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyBinding.inflate(inflater, container, false)

        collectUIEvent()
        setupView()

        return binding.root
    }

    private fun collectUIEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                verifyViewModel.uiFlow.collectLatest { event ->
                    when (event) {
                        is VerifyUIEvent.ShownErrorMessage -> {
                            binding.textViewErrorMessage.isVisible = true
                            binding.textViewErrorMessage.text = when (event.message) {
                                is StringDisplay.String -> event.message.string
                                is StringDisplay.StringRes -> requireContext().getString(event.message.idRes)
                            }
                        }
                        is VerifyUIEvent.Finished -> {
                            if (event.user.isRegistered) {
                                val intent = Intent(requireContext(), MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                            } else {
                                val navController = findNavController()
                                val action =
                                    VerifyFragmentDirections.actionNavigationAuthVerifyToNavigationAuthRegister()
                                navController.navigate(action)
                            }
                        }
                        is VerifyUIEvent.IsEnableInput -> event.enable.let { enable ->
                            if (!enable) {
                                binding.textViewErrorMessage.isVisible = false
                            }

                            binding.editTextVerificationCode1.isEnabled = enable
                            binding.editTextVerificationCode2.isEnabled = enable
                            binding.editTextVerificationCode3.isEnabled = enable
                            binding.editTextVerificationCode4.isEnabled = enable
                            binding.editTextVerificationCode5.isEnabled = enable
                            binding.editTextVerificationCode6.isEnabled = enable
                            binding.buttonVerify.isEnabled = enable
                        }
                    }
                }
            }
        }
    }

    private fun setupView() {
        binding.apply {
            editTextVerificationCode1.requestFocus()
            editTextVerificationCode1.doOnTextChanged { text, start, before, count ->
                if (count > 0) {
                    editTextVerificationCode2.requestFocus()
                }
            }
            editTextVerificationCode2.doOnTextChanged { text, start, before, count ->
                if (count == 0) {
                    editTextVerificationCode1.requestFocus()
                } else {
                    editTextVerificationCode3.requestFocus()
                }
            }
            editTextVerificationCode3.doOnTextChanged { text, start, before, count ->
                if (count == 0) {
                    editTextVerificationCode2.requestFocus()
                } else {
                    editTextVerificationCode4.requestFocus()
                }
            }
            editTextVerificationCode4.doOnTextChanged { text, start, before, count ->
                if (count == 0) {
                    editTextVerificationCode3.requestFocus()
                } else {
                    editTextVerificationCode5.requestFocus()
                }
            }
            editTextVerificationCode5.doOnTextChanged { text, start, before, count ->
                if (count == 0) {
                    editTextVerificationCode4.requestFocus()
                } else {
                    editTextVerificationCode6.requestFocus()
                }
            }
            editTextVerificationCode6.doOnTextChanged { text, start, before, count ->
                if (count == 0) {
                    editTextVerificationCode5.requestFocus()
                } else {
                    verify()
                }
            }

            buttonVerify.setOnClickListener {
                verify()
            }

            markwon.setMarkdown(textViewPrivacyPolicy, requireContext().getString(R.string.signin_signup_agreement))
        }
    }

    private fun verify() {
        val verificationCode = binding.editTextVerificationCode1.text.toString() +
                binding.editTextVerificationCode2.text.toString() +
                binding.editTextVerificationCode3.text.toString() +
                binding.editTextVerificationCode4.text.toString() +
                binding.editTextVerificationCode5.text.toString() +
                binding.editTextVerificationCode6.text.toString()

        verifyViewModel.onEvent(
            VerifyEvent.Verify(
                args.verificationId,
                verificationCode
            )
        )
    }
}