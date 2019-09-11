package de.dmi3y.behaiv.example

import de.dmi3y.behaiv.example.screen.ControlScreen
import javafx.scene.text.FontWeight
import tornadofx.App
import tornadofx.Stylesheet
import tornadofx.c
import tornadofx.px

class ExampleApp : App(ControlScreen::class, Styles::class)

class Styles : Stylesheet() {
    init {
        label {
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
            backgroundColor += c("#cecece")
        }
    }
}

public var HEADPHONES_ON: Boolean = false;
public var TIME_HOURS: Long = 10;
public var TIME_MINUTES: Long = 30;