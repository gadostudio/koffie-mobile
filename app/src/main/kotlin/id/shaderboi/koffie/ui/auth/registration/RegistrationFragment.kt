package id.shaderboi.koffie.ui.auth.registration

import IntentExtra
import android.content.Intent
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
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.databinding.FragmentRegistrationBinding
import id.shaderboi.koffie.ui.auth.registration.view_model.RegistrationEvent
import id.shaderboi.koffie.ui.auth.registration.view_model.RegistrationUIEvent
import id.shaderboi.koffie.ui.auth.registration.view_model.RegistrationViewModel
import id.shaderboi.koffie.ui.main.MainActivity
import id.shaderboi.koffie.util.StringDisplay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    val binding get() = _binding!!

    private val registrationViewModel by viewModels<RegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        collectUIEvent()
        setupView()

        return binding.root
    }

    private fun collectUIEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                registrationViewModel.uiFlow.collectLatest { event ->
                    when (event) {
                        RegistrationUIEvent.Finished -> {
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            intent.putExtra(IntentExtra.WITH_SPLASH_SCREEN, false)
                            startActivity(intent)
                        }
                        is RegistrationUIEvent.ShowErrorMessage -> {
                            binding.textViewErrorMessage.isVisible = true
                            binding.textViewErrorMessage.text = when (event.message) {
                                is StringDisplay.String -> event.message.string
                                is StringDisplay.StringRes -> requireContext().getString(event.message.idRes)
                            }
                        }
                        is RegistrationUIEvent.IsEnableInput -> event.enabled.let { enabled ->
                            if (!enabled) {
                                binding.textViewErrorMessage.isVisible = false
                            }

                            binding.editTextName.isEnabled = enabled
                            binding.editTextBirthDate.isEnabled = enabled
                            binding.buttonSubmit.isEnabled = enabled
                        }
                    }
                }
            }
        }
    }

    private fun setupView() {
        val datePicker = MaterialDatePicker.Builder
            .datePicker()
            .build()

        binding.apply {
            editTextBirthDate.setOnClickListener {
                datePicker.show(parentFragmentManager, null)
            }

            buttonSubmit.setOnClickListener {
                val displayName = editTextName.text.toString()
                val birthDate = editTextBirthDate.text.toString()
                val selectedGender = spinnerGender.selectedItemPosition
            }
        }
    }
}