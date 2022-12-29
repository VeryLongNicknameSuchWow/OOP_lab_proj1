package pl.rynbou.ooplab.gui

import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.stage.Stage
import pl.rynbou.ooplab.element.animal.Animal

class TrackingGui(val animal: Animal) : Application() {
    var closed = false

    val genomeLabel = Label(animal.genomeToString())
    val genomeActiveLabel = Label(animal.currentGeneIndex.toString())
    val energyLabel = Label(animal.energy.toString())
    val plantsEatenLabel = Label(animal.plantsEaten.toString())
    val ageLabel = Label(animal.age.toString())
    val deathEpochLabel = Label((animal.deathEpoch ?: "Alive").toString())

    val root = GridPane().apply {
        hgap = 10.0
        vgap = 10.0
        padding = Insets(20.0)

        var rowIndex = 0
        add(Label("Genome: "), 0, rowIndex)
        add(genomeLabel, 1, rowIndex)

        rowIndex++
        add(Label("Active gene index: "), 0, rowIndex)
        add(genomeActiveLabel, 1, rowIndex)

        rowIndex++
        add(Label("Energy: "), 0, rowIndex)
        add(energyLabel, 1, rowIndex)

        rowIndex++
        add(Label("Plants eaten: "), 0, rowIndex)
        add(plantsEatenLabel, 1, rowIndex)

        rowIndex++
        add(Label("Age: "), 0, rowIndex)
        add(ageLabel, 1, rowIndex)

        rowIndex++
        add(Label("Death epoch: "), 0, rowIndex)
        add(deathEpochLabel, 1, rowIndex)
    }
    val scene = Scene(root)

    override fun start(primaryStage: Stage) {
        primaryStage.setOnCloseRequest {
            closeWindow()
        }

        primaryStage.scene = scene
        primaryStage.show()
    }

    fun update() {
        genomeLabel.text = animal.genomeToString().toString()
        genomeActiveLabel.text = animal.currentGeneIndex.toString()
        energyLabel.text = animal.energy.toString()
        plantsEatenLabel.text = animal.plantsEaten.toString()
        ageLabel.text = animal.age.toString()
        deathEpochLabel.text = (animal.deathEpoch ?: "Alive").toString()
    }

    fun closeWindow() {
        if (!closed) {
            closed = true
            val stage = scene.window as Stage
            stage.close()
        }
    }
}
