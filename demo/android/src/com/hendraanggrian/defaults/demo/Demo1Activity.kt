package com.hendraanggrian.defaults.demo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.hendraanggrian.defaults.Default
import com.hendraanggrian.defaults.Defaults
import com.hendraanggrian.defaults.bindDefaults
import com.hendraanggrian.defaults.from
import kotlinx.android.synthetic.main.activity_demo1.*

class Demo1Activity : AppCompatActivity(), View.OnClickListener {

    @Default("name") lateinit var name: String
    @Default("married") @JvmField var married: Boolean = false
    @Default("int") @JvmField var mInt: Int = 0
    @Default("long") @JvmField var mLong: Long = 0
    @Default("float") @JvmField var mFloat: Float = 0.0f

    private lateinit var saver: Defaults.Saver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo1)
        setSupportActionBar(toolbar)
        saver = bindDefaults(Defaults.from(this))

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