package com.hendraanggrian.local.demo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.hendraanggrian.local.Local
import com.hendraanggrian.local.LocalSettings
import com.hendraanggrian.local.android
import com.hendraanggrian.local.bind
import kotlinx.android.synthetic.main.activity_demo1.*

class Demo1Activity : AppCompatActivity(), View.OnClickListener {

    @Local("name") lateinit var name: String
    @Local("married") @JvmField var married: Boolean = false
    @Local("int") @JvmField var mInt: Int = 0
    @Local("long") @JvmField var mLong: Long = 0
    @Local("float") @JvmField var mFloat: Float = 0.0f

    private lateinit var saver: LocalSettings.Saver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo1)
        setSupportActionBar(toolbar)
        saver = LocalSettings android this bind this

        nameEditText.setText(name)
        marriedEditText.setText(married.toString())
        intEditText.setText(mInt.toString())
        longEditText.setText(mLong.toString())
        floatEditText.setText(mFloat.toString())
    }

    override fun onClick(v: View) {
        name = nameEditText.text.toString()
        married = java.lang.Boolean.parseBoolean(marriedEditText.text.toString())
        mInt = Integer.parseInt(intEditText.text.toString())
        mLong = java.lang.Long.parseLong(longEditText.text.toString())
        mFloat = java.lang.Float.parseFloat(floatEditText.text.toString())
        saver.saveAsync()
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.demo1, menu)
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