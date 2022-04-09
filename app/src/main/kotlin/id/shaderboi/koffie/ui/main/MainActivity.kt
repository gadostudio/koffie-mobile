package id.shaderboi.koffie.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.databinding.ActivityMainBinding
import id.shaderboi.koffie.ui.auth.AuthActivity
import id.shaderboi.koffie.ui.main.view_model.MainViewModel
import javax.inject.Inject
import id.shaderboi.koffie.core.data.auth.Auth

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var auth: Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupSplashScreen()

        if (!checkAuthentication()) return

        _binding = ActivityMainBinding.inflate(layoutInflater)

        val navHostFragment = binding.fragmentContainerViewMain.getFragment<NavHostFragment>()
        val navController = navHostFragment.navController
        binding.bottomNavigationViewMain.setupWithNavController(navController)
        binding.bottomNavigationViewMain.setOnItemReselectedListener { item ->
            val selectedMenuItemNavGraph = navController.graph.findNode(item.itemId) as NavGraph
            navController.popBackStack(selectedMenuItemNavGraph.startDestinationId, false)
        }

        setContentView(binding.root)
    }

    override fun onDestroy() {
        _binding = null

        super.onDestroy()
    }

    private fun setupSplashScreen() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainViewModel.isSplashScreenLoading
            }
        }
    }

    private fun checkAuthentication(): Boolean {
        val isAuthenticated = auth.isAuthenticated
        if (!isAuthenticated) {
            val intent = Intent(this, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        return isAuthenticated
    }
}