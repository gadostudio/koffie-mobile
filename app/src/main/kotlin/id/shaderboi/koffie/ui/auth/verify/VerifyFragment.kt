package id.shaderboi.koffie.ui.auth.verify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.databinding.FragmentVerifyBinding
import id.shaderboi.koffie.ui.common.view_model.AuthEvent
import id.shaderboi.koffie.ui.common.view_model.AuthViewModel

@AndroidEntryPoint
class VerifyFragment : Fragment() {
    private var _binding: FragmentVerifyBinding? = null
    val binding get() = _binding!!

    private val signInViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyBinding.inflate(inflater, container, false)

        setupView()

        return binding.root
    }

    private fun setupView() {
        binding.editTextVerificationCode1.requestFocus()
        binding.editTextVerificationCode1.doOnTextChanged { text, start, before, count ->
            if (count > 0) {
                binding.editTextVerificationCode2.requestFocus()
            }
        }
        binding.editTextVerificationCode2.doOnTextChanged { text, start, before, count ->
            if (count == 0) {
                binding.editTextVerificationCode1.requestFocus()
            } else {
                binding.editTextVerificationCode3.requestFocus()
            }
        }
        binding.editTextVerificationCode3.doOnTextChanged { text, start, before, count ->
            if (count == 0) {
                binding.editTextVerificationCode2.requestFocus()
            } else {
                binding.editTextVerificationCode4.requestFocus()
            }
        }
        binding.editTextVerificationCode4.doOnTextChanged { text, start, before, count ->
            if (count == 0) {
                binding.editTextVerificationCode3.requestFocus()
            } else {
                binding.editTextVerificationCode5.requestFocus()
            }
        }
        binding.editTextVerificationCode5.doOnTextChanged { text, start, before, count ->
            if (count == 0) {
                binding.editTextVerificationCode4.requestFocus()
            } else {
                binding.editTextVerificationCode6.requestFocus()
            }
        }
        binding.editTextVerificationCode6.doOnTextChanged { text, start, before, count ->
            if (count == 0) {
                binding.editTextVerificationCode5.requestFocus()
            } else {
                verify()
            }
        }

        binding.buttonVerify.setOnClickListener {
            verify()
        }
    }

    private fun verify() {
        val navController = findNavController()

        val verificationCode = binding.editTextVerificationCode1.text.toString() +
                binding.editTextVerificationCode2.text.toString() +
                binding.editTextVerificationCode3.text.toString() +
                binding.editTextVerificationCode4.text.toString() +
                binding.editTextVerificationCode5.text.toString() +
                binding.editTextVerificationCode6.text.toString()
        signInViewModel.onEvent(
            AuthEvent.Verify(
                verificationCode,
                requireActivity(),
                navController
            )
        )
    }
}