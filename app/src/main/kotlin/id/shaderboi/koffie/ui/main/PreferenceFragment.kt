package id.shaderboi.koffie.ui.main

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.koffie.R

@AndroidEntryPoint
class PreferenceFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.profile_preference, "profile")
    }
}