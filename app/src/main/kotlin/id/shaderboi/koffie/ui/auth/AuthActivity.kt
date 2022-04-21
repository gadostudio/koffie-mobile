package id.shaderboi.koffie.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.databinding.ActivityAuthBinding
import id.shaderboi.koffie.ui.auth.signin.SignInFragmentDirections
import id.shaderboi.koffie.ui.common.view_model.AuthViewModel
import id.shaderboi.koffie.ui.main.MainActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private var _binding: ActivityAuthBinding? = null
    val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAuthBinding.inflate(layoutInflater)

        setupAuthentication()

        setContentView(binding.root)
    }

    private fun setupAuthentication() {
        val navHostFragment = binding.fragmentContainerViewMain.getFragment<NavHostFragment>()
        val navController = navHostFragment.navController

        authViewModel.authenticationFlow.onEach { user ->
            if (user !== null && user.isRegistered) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }.launchIn(lifecycleScope)
    }
}