package com.hendraanggrian.lokal.demo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.hendraanggrian.lokal.BindLokal
import com.hendraanggrian.lokal.Lokal
import com.hendraanggrian.lokal.bindLokal
import kotlinx.android.synthetic.main.activity_demo1.*

class Demo1Activity : AppCompatActivity(), View.OnClickListener {

    @BindLokal("name") @JvmField var name: String? = null
    @BindLokal("married") @JvmField var married: Boolean = false
    @BindLokal("int") @JvmField var mInt: Int = 0
    @BindLokal("long") @JvmField var mLong: Long = 0L
    @BindLokal("float") @JvmField var mFloat: Float = 0f

    private lateinit var saver: Lokal.Saver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo1)
        setSupportActionBar(toolbar)
        saver = bindLokal()

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