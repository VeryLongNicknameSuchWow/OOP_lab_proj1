package pl.rynbou.ooplab.gui

import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.Stage
import pl.rynbou.ooplab.Simulation
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.map.WorldMap

class MapGui(
    val worldMap: WorldMap,
    val simulation: Simulation
) : Application() {
    val root = GridPane().apply {
        hgap = 10.0
        vgap = 10.0
        padding = Insets(10.0)
        //fill with empty rectangles so that the size adjusts itself
        for (x in 0 until worldMap.simulationProperties.mapWidth) {
            for (y in 0 until worldMap.simulationProperties.mapHeight) {
                val rect = Rectangle(50.0, 50.0)
                add(rect, x, y)
            }
        }

        val pauseSimulationButton = Button("Pause simulation").apply {
            font = Font.font(null, FontWeight.BOLD, 20.0)

            setOnAction {
                simulation.paused = !simulation.paused
                text = if (simulation.paused) "Unpause simulation" else "Pause simulation"
            }
        }
        add(pauseSimulationButton, worldMap.simulationProperties.mapWidth, 0)

        val endSimulationButton = Button("End simulation").apply {
            font = Font.font(null, FontWeight.BOLD, 20.0)

            setOnAction {
                closeWindow()
            }
        }
        add(endSimulationButton, worldMap.simulationProperties.mapWidth, 1)
    }
    val scene = Scene(root)

    override fun start(primaryStage: Stage) {
        primaryStage.setOnCloseRequest {
            closeWindow()
        }

        primaryStage.scene = scene
        primaryStage.show()
    }

    fun drawAnimals() {
        for (occupiedPosition in worldMap.animalStorage.getOccupiedPositions()) {
            val bestAnimal = worldMap.animalStorage.getAnimalsAt(occupiedPosition).first()
            val label = Label().apply {
                text = bestAnimal?.energy.toString()
            }
            root.add(label, occupiedPosition.x, occupiedPosition.y)
        }
    }

    fun drawGrass() {
        val occupiedPositions = worldMap.plantStorage.getOccupiedPositions()
        for (x in 0 until worldMap.simulationProperties.mapWidth) {
            for (y in 0 until worldMap.simulationProperties.mapHeight) {
                val position = MapVector2D(x, y)
                val rect = Rectangle(50.0, 50.0)
                if (position in occupiedPositions) { // dobra stała nie jest zła
                    rect.fill = Color.GREEN
                } else {
                    rect.fill = Color.LIGHTGREY
                }
                root.add(rect, x, y)
            }
        }
    }

    fun clear() {
        root.children.removeIf { it !is Button }
    }

    fun closeWindow() {
        simulation.ended = true
        val stage = scene.window as Stage
        stage.close()
    }
}
