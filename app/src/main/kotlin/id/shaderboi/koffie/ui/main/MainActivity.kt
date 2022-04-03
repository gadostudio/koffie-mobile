package id.shaderboi.koffie.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.databinding.ActivityMainBinding
import id.shaderboi.koffie.ui.auth.AuthActivity
import id.shaderboi.koffie.ui.main.view_model.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
            true
        }

        if (firebaseAuth.currentUser == null) {
            val intent = Intent(this, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            return
        }

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onDestroy() {
        _binding = null

        super.onDestroy()
    }
}