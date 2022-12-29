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

    val totalAnimalsLabel = Label("Not available")
    val totalPlantsAmount = Label("Not available")
    val freeFieldsAmountLabel = Label("Not available")
    val topGenotypeLabel = Label("Not available")
    val averageEnergyLabel = Label("Not available")
    val averageLifespanLabel = Label("Not available")

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

        var rowIndex = 0
        val lastColumn = worldMap.simulationProperties.mapWidth
        val pauseSimulationButton = Button("Pause simulation").apply {
            font = Font.font(null, FontWeight.BOLD, 20.0)

            setOnAction {
                simulation.paused = !simulation.paused
                text = if (simulation.paused) "Unpause simulation" else "Pause simulation"
            }
        }
        add(pauseSimulationButton, lastColumn, rowIndex)

        rowIndex++
        val endSimulationButton = Button("End simulation").apply {
            font = Font.font(null, FontWeight.BOLD, 20.0)

            setOnAction {
                closeWindow()
            }
        }
        add(endSimulationButton, lastColumn, rowIndex)

        rowIndex++
        add(Label("Total animals: "), lastColumn, rowIndex)
        add(totalAnimalsLabel, lastColumn + 1, rowIndex)

        rowIndex++
        add(Label("Total plants: "), lastColumn, rowIndex)
        add(totalPlantsAmount, lastColumn + 1, rowIndex)

        rowIndex++
        add(Label("Free fields: "), lastColumn, rowIndex)
        add(freeFieldsAmountLabel, lastColumn + 1, rowIndex)

        rowIndex++
        add(Label("Top genome: "), lastColumn, rowIndex)
        add(topGenotypeLabel, lastColumn + 1, rowIndex)

        rowIndex++
        add(Label("Average energy: "), lastColumn, rowIndex)
        add(averageEnergyLabel, lastColumn + 1, rowIndex)

        rowIndex++
        add(Label("Average lifespan: "), lastColumn, rowIndex)
        add(averageLifespanLabel, lastColumn + 1, rowIndex)
    }
    val scene = Scene(root)
    val trackingGuis = mutableListOf<TrackingGui>()

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

                setOnMouseClicked {
                    if (trackingGuis.any { it.animal == bestAnimal }) {
                        return@setOnMouseClicked
                    }
                    val trackingGui = TrackingGui(bestAnimal)
                    trackingGui.start(Stage())
                    trackingGuis.add(trackingGui)
                }
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

    fun updateTracking() {
        trackingGuis.removeIf { it.closed }
        trackingGuis.forEach { it.update() }
    }

    fun updateDisplayedStats() {
        try {
            val stats = simulation.statisticsProvider.simulationStatistics.last()
            totalAnimalsLabel.text = stats.animalsCount.toString()
            totalPlantsAmount.text = stats.plantsCount.toString()
            freeFieldsAmountLabel.text = stats.freeFieldsCount.toString()
            topGenotypeLabel.text = stats.dominantGenotype
            averageEnergyLabel.text = "%.2f".format(stats.averageEnergy)
            averageLifespanLabel.text = "%.2f".format(stats.averageLifeSpan)
        } catch (_: NoSuchElementException) {
            //just in case
        }
    }

    fun clear() {
        root.children.removeIf {
            GridPane.getColumnIndex(it) < worldMap.simulationProperties.mapWidth
            //dobra stała nie jest zła
        }
    }

    fun closeWindow() {
        trackingGuis.forEach { it.closeWindow() }

        simulation.ended = true
        val stage = scene.window as Stage
        stage.close()
    }
}
