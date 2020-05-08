package com.example.prefy

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.hendraanggrian.prefy.BindPreference
import com.hendraanggrian.prefy.PreferencesSaver
import com.hendraanggrian.prefy.android.bindPreferences
import kotlinx.android.synthetic.main.activity_option1.*

class Option1Activity : AppCompatActivity(), View.OnClickListener {
    @BindPreference("name") @JvmField var name: String? = null
    @BindPreference("married") @JvmField var married: Boolean = false
    @BindPreference("int") @JvmField var mInt: Int = 0
    @BindPreference("long") @JvmField var mLong: Long = 0L
    @BindPreference("float") @JvmField var mFloat: Float = 0f

    private lateinit var saver: PreferencesSaver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option1)
        setSupportActionBar(toolbar)
        saver = bindPreferences()

        nameEditText.setText(name)
        marriedEditText.setText(married.toString())
        intEditText.setText(mInt.toString())
        longEditText.setText(mLong.toString())
        floatEditText.setText(mFloat.toString())
    }

    override fun onClick(v: View) {
        name = nameEditText.text.toString()
        married = marriedEditText.text.toString().toBoolean()
        mInt = intEditText.text.toString().toInt()
        mLong = longEditText.text.toString().toLong()
        mFloat = floatEditText.text.toString().toFloat()
        saver.save()
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_option1, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.reset -> PreferenceManager.getDefaultSharedPreferences(this).edit { clear() }
        }
        return true
    }
}