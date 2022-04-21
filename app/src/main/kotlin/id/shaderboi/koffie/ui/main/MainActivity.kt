package id.shaderboi.koffie.ui.main

import IntentExtra
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.R
import id.shaderboi.koffie.core.data.auth.Auth
import id.shaderboi.koffie.databinding.ActivityMainBinding
import id.shaderboi.koffie.ui.auth.AuthActivity
import id.shaderboi.koffie.ui.common.view_model.AuthViewModel
import id.shaderboi.koffie.ui.main.view_model.SplashScreenViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private val splashScreenViewModel by viewModels<SplashScreenViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var auth: Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupSplashScreen()

        setupAuthentication()
    }

    override fun onDestroy() {
        _binding = null

        super.onDestroy()
    }

    private fun setupView() {
        val navHostFragment = binding.fragmentContainerViewMain.getFragment<NavHostFragment>()
        val navController = navHostFragment.navController
        binding.bottomNavigationViewMain.setupWithNavController(navController)
        binding.bottomNavigationViewMain.setOnItemReselectedListener { item ->
            val selectedMenuItemNavGraph = navController.graph.findNode(item.itemId) as NavGraph
            navController.popBackStack(selectedMenuItemNavGraph.startDestinationId, false)
        }
    }

    private fun setupSplashScreen() {
        val isWithSplashScreen = intent.getBooleanExtra(IntentExtra.WITH_SPLASH_SCREEN, true)
        if (isWithSplashScreen) {
            installSplashScreen().apply {
                setKeepOnScreenCondition {
                    splashScreenViewModel.isSplashScreenLoading
                }
            }
        } else {
            setTheme(R.style.Theme_Koffie)
        }
    }

    private fun setupAuthentication() {
        authViewModel.authenticationFlow.onEach { user ->
            user?.isRegistered
            if (user?.isRegistered == false || user == null) {
                onNotAuthenticated()
            } else {
                _binding = ActivityMainBinding.inflate(layoutInflater)

                setContentView(binding.root)

                setupView()
            }
        }.launchIn(lifecycleScope)
    }

    private fun onNotAuthenticated() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}