package id.shaderboi.koffie.ui.main.misc

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.R
import id.shaderboi.koffie.core.domain.model.theme.Theme
import id.shaderboi.koffie.core.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PreferenceFragment : PreferenceFragmentCompat() {
    @Inject
    lateinit var appSettingsRepository: AppSettingsRepository

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.profile_preference, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val themeList = findPreference<ListPreference>("theme")!!

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                themeList.setOnPreferenceChangeListener { preference, newValue ->
                    launch {
                        appSettingsRepository.setTheme(Theme.valueOf(newValue as String))
                    }
                    true
                }

                launch {
                    appSettingsRepository.flow.collectLatest { appSettings ->
                        themeList.value = appSettings.theme.name
                    }
                }
            }
        }
    }
}