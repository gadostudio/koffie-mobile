package id.shaderboi.koffie.ui.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.databinding.ActivityAuthBinding
import id.shaderboi.koffie.ui.auth.view_model.SignInViewModel

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private var _binding: ActivityAuthBinding? = null
    val binding get() = _binding!!

    private val signInViewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAuthBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}