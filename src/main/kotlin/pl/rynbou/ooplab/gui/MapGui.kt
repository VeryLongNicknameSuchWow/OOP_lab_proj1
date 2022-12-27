package pl.rynbou.ooplab.gui

import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.shape.Rectangle
import javafx.stage.Stage
import pl.rynbou.ooplab.map.WorldMap

class MapGui(
    val worldMap: WorldMap
) : Application() {
    val root = GridPane().apply {
        hgap = 10.0
        vgap = 10.0
        padding = Insets(20.0)
    }
    val scene = Scene(root)

    override fun start(primaryStage: Stage) {
        primaryStage.scene = scene
        primaryStage.show()
    }

    fun drawAnimals() {
        for (occupiedPosition in worldMap.animalStorage.getOccupiedPositions()) {
            val bestAnimal = worldMap.animalStorage.getAnimalsAt(occupiedPosition).first()
            root.add(Label(bestAnimal?.energy.toString()), occupiedPosition.x, occupiedPosition.y)
        }
    }

    fun drawGrass() {
        for (occupiedPosition in worldMap.plantStorage.getOccupiedPositions()) {
            val rect = Rectangle(50.0, 50.0)
            rect.style = "-fx-background-color: #FF0000;"
            root.add(rect, occupiedPosition.x, occupiedPosition.y)
        }
    }

    fun clear() {
        root.children.clear()
    }
}
