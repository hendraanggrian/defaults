package com.hendraanggrian.local.demo

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.EditTextPreference
import androidx.preference.Preference.OnPreferenceChangeListener
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.hendraanggrian.local.BindLocal
import com.hendraanggrian.local.Local
import com.hendraanggrian.local.bindLocal
import com.hendraanggrian.local.sharedPreferences
import kotlinx.android.synthetic.main.activity_demo2.*

class Demo2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo2)
        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction()
            .add(R.id.viewgroup_example2, SettingsFragment())
            .commitNow()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        @BindLocal("name") lateinit var name: String
        @BindLocal lateinit var preferenceName: EditTextPreference
        lateinit var preferenceMarried: SwitchPreferenceCompat

        override fun onAttach(context: Context) {
            super.onAttach(context)
            bindLocal(Local sharedPreferences context)
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.settings)
            preferenceName = findPreference("name") as EditTextPreference
            preferenceMarried = findPreference("married") as SwitchPreferenceCompat
            preferenceName.summary = name
            preferenceName.onPreferenceChangeListener = OnPreferenceChangeListener { _, newValue ->
                preferenceName.summary = newValue as String
                true
            }
        }
    }
}