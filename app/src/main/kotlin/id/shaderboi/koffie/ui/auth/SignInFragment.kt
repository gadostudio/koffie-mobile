package id.shaderboi.koffie.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.databinding.FragmentSigninBinding
import id.shaderboi.koffie.ui.auth.view_model.SignInEvent
import id.shaderboi.koffie.ui.auth.view_model.SignInViewModel
import id.shaderboi.koffie.ui.main.MainActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private var _binding: FragmentSigninBinding? = null
    val binding get() = _binding!!

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private val signInViewModel by activityViewModels<SignInViewModel>()

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
        binding.buttonSignIn.setOnClickListener {
            val phoneNumber = binding.editTextPhone.text.toString()
            signInViewModel.onEvent(
                SignInEvent.SignIn(
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