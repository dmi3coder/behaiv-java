package de.dmi3y.behaiv.example

import de.dmi3y.behaiv.example.screen.MainScreen
import javafx.scene.text.FontWeight
import tornadofx.App
import tornadofx.Stylesheet
import tornadofx.c
import tornadofx.px

class ExampleApp : App(MainScreen::class, Styles::class)

class Styles : Stylesheet() {
    init {
        label {
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
            backgroundColor += c("#cecece")
        }
    }
}

