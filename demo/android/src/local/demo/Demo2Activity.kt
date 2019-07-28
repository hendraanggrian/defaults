package local.demo

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.EditTextPreference
import androidx.preference.Preference.OnPreferenceChangeListener
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import kotlinx.android.synthetic.main.activity_demo2.*
import local.BindLocal
import local.Local
import local.adapter.android.bind

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
        @BindLocal("name") @JvmField var name: String? = null
        lateinit var preferenceName: EditTextPreference
        lateinit var preferenceMarried: SwitchPreferenceCompat

        override fun onAttach(context: Context) {
            super.onAttach(context)
            Local.bind(this)
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