package io.github.orizynpx.fivemoviesxml.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import io.github.orizynpx.fivemoviesxml.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val languagePreference = findPreference<ListPreference>("language")
        languagePreference?.setOnPreferenceChangeListener { _, newValue ->
            val code = newValue as String
            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(code)
            AppCompatDelegate.setApplicationLocales(appLocale)
            true
        }

        val themePreference = findPreference<ListPreference>("theme_mode")
        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            val themeMode = when (newValue as String) {
                "light" -> AppCompatDelegate.MODE_NIGHT_NO
                "dark" -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            AppCompatDelegate.setDefaultNightMode(themeMode)
            true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val containerView = view.findViewById<FrameLayout>(R.id.settings_container)

        val settingsView = super.onCreateView(inflater, containerView, savedInstanceState)
        containerView.addView(settingsView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbarSettings)
            .setNavigationOnClickListener {
                findNavController().navigateUp()
            }
    }
}
