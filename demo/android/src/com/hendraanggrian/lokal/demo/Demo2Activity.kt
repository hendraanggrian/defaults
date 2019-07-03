package com.hendraanggrian.lokal.demo

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.EditTextPreference
import androidx.preference.Preference.OnPreferenceChangeListener
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.hendraanggrian.lokal.BindLokal
import com.hendraanggrian.lokal.bindLokal
import kotlinx.android.synthetic.main.activity_demo2.*

class Demo2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo2)
        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction()
            .add(
                R.id.viewgroup_example2,
                SettingsFragment()
            )
            .commitNow()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        @BindLokal("name") @JvmField var name: String? = null
        lateinit var preferenceName: EditTextPreference
        lateinit var preferenceMarried: SwitchPreferenceCompat

        override fun onAttach(context: Context) {
            super.onAttach(context)
            bindLokal()
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.settings)
            preferenceName = findPreference<EditTextPreference>("name")!!
            preferenceMarried = findPreference<SwitchPreferenceCompat>("married")!!
            preferenceName.summary = name.orEmpty()
            preferenceName.onPreferenceChangeListener = OnPreferenceChangeListener { _, newValue ->
                preferenceName.summary = newValue as String
                true
            }
        }
    }
}