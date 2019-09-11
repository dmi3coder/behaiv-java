package de.dmi3y.behaiv.example.screen

import de.dmi3y.behaiv.example.HEADPHONES_ON
import de.dmi3y.behaiv.example.TIME_HOURS
import de.dmi3y.behaiv.example.TIME_MINUTES
import javafx.geometry.Pos
import javafx.scene.control.ToggleGroup
import tornadofx.*

class ControlScreen : View() {
    val vbox = vbox(alignment = Pos.CENTER, spacing = 10) {
        hbox {
            label("Set time to:")
            textfield("10") {
                TIME_HOURS = this.text.toLong()
            }
            label(" hours")
            textfield("30") {
                TIME_MINUTES = this.text.toLong()
            }
            label(" minutes")
        }
        val group = ToggleGroup()
        hbox {
            radiobutton("Location 1", group)
            radiobutton("Location 2", group)
            radiobutton("Location 3", group)
        }
        checkbox("Headphones connected") {
            HEADPHONES_ON = this.isSelected
        }
        button("Open chat app") {
            action {
                find<MainScreen>().openModal()
            }
        }
    }
    override val root = vbox.apply {
        paddingHorizontal = 10
        paddingVertical = 20
    }
}