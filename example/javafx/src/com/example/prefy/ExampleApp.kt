package com.example.prefy

import com.hendraanggrian.prefy.BindPreference
import com.hendraanggrian.prefy.PreferencesLogger
import com.hendraanggrian.prefy.PreferencesSaver
import com.hendraanggrian.prefy.Prefy
import com.hendraanggrian.prefy.jvm.bindPreferences
import com.hendraanggrian.prefy.jvm.get
import javafx.application.Application
import javafx.scene.control.CheckBox
import javafx.scene.control.TextField
import javafx.stage.Stage
import ktfx.controls.insetsOf
import ktfx.coroutines.onAction
import ktfx.dialogs.infoAlert
import ktfx.launchApplication
import ktfx.layouts.button
import ktfx.layouts.buttonBar
import ktfx.layouts.checkBox
import ktfx.layouts.gridPane
import ktfx.layouts.label
import ktfx.layouts.scene
import ktfx.layouts.textField
import org.apache.commons.lang3.SystemUtils
import java.io.File

class ExampleApp : Application() {

    companion object {
        @JvmStatic fun main(args: Array<String>) = launchApplication<ExampleApp>(*args)
    }

    private lateinit var nameField: TextField
    private lateinit var marriedCheck: CheckBox
    private lateinit var ageField: TextField
    private lateinit var heightField: TextField

    @BindPreference @JvmField var name: String? = null
    @BindPreference @JvmField var married: String = "false"
    @BindPreference @JvmField var age: String = "0"
    @BindPreference @JvmField var height: String = "0.0"

    private lateinit var saver: PreferencesSaver

    override fun init() = Prefy.setLogger(PreferencesLogger.System)

    override fun start(stage: Stage) {
        saver = bindPreferences(
            Prefy[File(SystemUtils.USER_HOME, "Desktop").resolve("test.properties")]
        )
        stage.scene {
            gridPane {
                padding = insetsOf(0)
                hgap = 0.0; vgap = 0.0
                label("Name").grid(0, 0)
                nameField = textField(name.orEmpty()).grid(0, 1)
                label("Married").grid(1, 0)
                marriedCheck = checkBox { isSelected = married.toBoolean() }.grid(1, 1)
                label("Age").grid(2, 0)
                ageField = textField(age).grid(2, 1)
                label("Height").grid(3, 0)
                heightField = textField(this@ExampleApp.height).grid(3, 1)
                buttonBar {
                    button("Save") {
                        onAction {
                            name = nameField.text
                            married = marriedCheck.isSelected.toString()
                            age = ageField.text.toString()
                            this@ExampleApp.height = heightField.text.toString()
                            saver.save()
                            infoAlert("Saved!")
                        }
                    }
                }.grid(4, 0 to 2)
            }
        }
        stage.show()
    }
}