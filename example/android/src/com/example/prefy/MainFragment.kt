package com.example.prefy

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class MainFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fragment_main)
        findPreference<Preference>("option1")!!.setOnPreferenceClickListener {
            startActivity(Intent(context, Option1Activity::class.java))
            false
        }
        findPreference<Preference>("option2")!!.setOnPreferenceClickListener {
            startActivity(Intent(context, Option2Activity::class.java))
            false
        }
    }
}