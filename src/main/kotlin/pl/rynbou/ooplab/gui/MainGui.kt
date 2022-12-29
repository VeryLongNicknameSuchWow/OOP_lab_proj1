package pl.rynbou.ooplab.gui

import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.Spinner
import javafx.scene.layout.GridPane
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.FileChooser
import javafx.stage.Stage
import kotlinx.serialization.json.Json
import pl.rynbou.ooplab.*
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import kotlin.system.exitProcess

class MainGui : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.setOnCloseRequest {
            exitProcess(0)
        }

        val mapModeComboBox = ComboBox<MapMode>().apply {
            items.addAll(MapMode.values())
            selectionModel.select(MapMode.values().first())
        }

        //TODO adjust default values

        val mapWidthSpinner = Spinner<Int>(2, 40, 10)
        val mapHeightSpinner = Spinner<Int>(2, 40, 10)

        val plantGrowthModeComboBox = ComboBox<PlantGrowthMode>().apply {
            items.addAll(PlantGrowthMode.values())
            selectionModel.select(PlantGrowthMode.values().first())
        }

        val initialPlantsSpinner = Spinner<Int>(2, 100, 5)
        val plantEnergySpinner = Spinner<Int>(2, 100, 50)
        val dailyPlantsSpinner = Spinner<Int>(2, 100, 5)

        val animalBehaviourModeComboBox = ComboBox<AnimalBehaviourMode>().apply {
            items.addAll(AnimalBehaviourMode.values())
            selectionModel.select(AnimalBehaviourMode.values().first())
        }

        val initialAnimalsSpinner = Spinner<Int>(2, 100, 10)
        val initialAnimalEnergySpinner = Spinner<Int>(2, 100, 60)
        val fullEnergyThresholdSpinner = Spinner<Int>(2, 200, 100)
        val moveEnergyCostSpinner = Spinner<Int>(2, 100, 20)
        val copulationEnergyCostSpinner = Spinner<Int>(2, 100, 50)

        val animalMutationModeComboBox = ComboBox<AnimalMutationMode>().apply {
            items.addAll(AnimalMutationMode.values())
            selectionModel.select(AnimalMutationMode.values().first())
        }

        val mutationAmountLowerBoundSpinner = Spinner<Int>(1, 10, 5)
        val mutationAmountUpperBoundSpinner = Spinner<Int>(10, 50, 10)
        val genomeLengthSpinner = Spinner<Int>(3, 30, 10)

        val statisticsFilePath = Label("No file selected")
        val fileChooser = FileChooser()
        var file: File? = null
        val chooseFileButton = Button("Choose statistics file").apply {
            setOnAction {
                file = fileChooser.showSaveDialog(primaryStage)
                if (file != null) {
                    statisticsFilePath.text = "Selected: " + file!!.canonicalPath
                } else {
                    statisticsFilePath.text = "No file selected"
                }
            }
        }

        fun getProperties(): SimulationProperties {
            return SimulationProperties(
                mapModeComboBox.value,
                mapWidthSpinner.value,
                mapHeightSpinner.value,
                plantGrowthModeComboBox.value,
                initialPlantsSpinner.value,
                plantEnergySpinner.value,
                dailyPlantsSpinner.value,
                animalBehaviourModeComboBox.value,
                initialAnimalsSpinner.value,
                initialAnimalEnergySpinner.value,
                fullEnergyThresholdSpinner.value,
                moveEnergyCostSpinner.value,
                copulationEnergyCostSpinner.value,
                animalMutationModeComboBox.value,
                mutationAmountLowerBoundSpinner.value,
                mutationAmountUpperBoundSpinner.value,
                genomeLengthSpinner.value,
                file
            )
        }

        fun startSimulation(simulationProperties: SimulationProperties) {
            val simulation = Simulation(simulationProperties)
            val simulationThread = Thread(simulation)
            simulationThread.start()
        }

        val startSimulationButton = Button("Start simulation").apply {
            font = Font.font(null, FontWeight.BOLD, 20.0)

            setOnAction {
                val simulationProperties = getProperties()
                startSimulation(simulationProperties)
            }
        }

        val savePropertiesButton = Button("Save settings\nto file").apply {
            font = Font.font(null, FontWeight.BOLD, 20.0)
            setOnAction {
                file = fileChooser.showSaveDialog(primaryStage)
                if (file != null) {
                    val fileOutputStream = FileOutputStream(file)
                    val bufferedWriter = fileOutputStream.bufferedWriter()
                    val simulationProperties = getProperties()
                    val propertiesAsStr = Json.encodeToString(SimulationProperties.serializer(), simulationProperties)
                    bufferedWriter.write(propertiesAsStr)
                    bufferedWriter.flush()
                }
            }
        }

        val runPropertiesButton = Button("Run settings\nfrom file").apply {
            font = Font.font(null, FontWeight.BOLD, 20.0)
            setOnAction {
                file = fileChooser.showOpenDialog(primaryStage)
                if (file != null) {
                    val propertiesAsStr = Files.readString(file!!.toPath())
                    val simulationProperties = Json.decodeFromString(SimulationProperties.serializer(), propertiesAsStr)
                    startSimulation(simulationProperties)
                }
            }
        }

        val root = GridPane().apply {
            hgap = 10.0
            vgap = 10.0
            padding = Insets(20.0)

            var rowIndex = 0
            add(Label("Map settings").apply {
                font = Font.font(null, FontWeight.BOLD, 20.0)
            }, 0, rowIndex)

            rowIndex++
            add(Label("Map mode: "), 0, rowIndex)
            add(mapModeComboBox, 1, rowIndex)

            rowIndex++
            add(Label("Map width: "), 0, rowIndex)
            add(mapWidthSpinner, 1, rowIndex)

            rowIndex++
            add(Label("Map height: "), 0, rowIndex)
            add(mapHeightSpinner, 1, rowIndex)

            rowIndex++
            add(Label("Plant settings").apply {
                font = Font.font(null, FontWeight.BOLD, 20.0)
            }, 0, rowIndex)

            rowIndex++
            add(Label("Plant growth mode: "), 0, rowIndex)
            add(plantGrowthModeComboBox, 1, rowIndex)

            rowIndex++
            add(Label("Initial plants: "), 0, rowIndex)
            add(initialPlantsSpinner, 1, rowIndex)

            rowIndex++
            add(Label("Plant energy: "), 0, rowIndex)
            add(plantEnergySpinner, 1, rowIndex)

            rowIndex++
            add(Label("Daily plants: "), 0, rowIndex)
            add(dailyPlantsSpinner, 1, rowIndex)

            rowIndex++
            add(Label("Animal settings").apply {
                font = Font.font(null, FontWeight.BOLD, 20.0)
            }, 0, rowIndex)

            rowIndex++
            add(Label("Animal behaviour mode: "), 0, rowIndex)
            add(animalBehaviourModeComboBox, 1, rowIndex)

            rowIndex++
            add(Label("Initial animals: "), 0, rowIndex)
            add(initialAnimalsSpinner, 1, rowIndex)

            rowIndex++
            add(Label("Initial animal energy: "), 0, rowIndex)
            add(initialAnimalEnergySpinner, 1, rowIndex)

            rowIndex++
            add(Label("Animal full energy threshold: "), 0, rowIndex)
            add(fullEnergyThresholdSpinner, 1, rowIndex)

            rowIndex++
            add(Label("Animal move energy cost: "), 0, rowIndex)
            add(moveEnergyCostSpinner, 1, rowIndex)

            rowIndex++
            add(Label("Animal copulation energy cost: "), 0, rowIndex)
            add(copulationEnergyCostSpinner, 1, rowIndex)

            rowIndex++
            add(Label("Animal mutation mode: "), 0, rowIndex)
            add(animalMutationModeComboBox, 1, rowIndex)

            rowIndex++
            add(Label("Animal min mutation amount: "), 0, rowIndex)
            add(mutationAmountLowerBoundSpinner, 1, rowIndex)

            rowIndex++
            add(Label("Animal max mutation amount: "), 0, rowIndex)
            add(mutationAmountUpperBoundSpinner, 1, rowIndex)

            rowIndex++
            add(Label("Animal genome length: "), 0, rowIndex)
            add(genomeLengthSpinner, 1, rowIndex)

            rowIndex++
            add(Label("Statistics file").apply {
                font = Font.font(null, FontWeight.BOLD, 20.0)
            }, 0, rowIndex)

            rowIndex++
            add(statisticsFilePath, 0, rowIndex)
            add(chooseFileButton, 1, rowIndex)

            rowIndex++
            GridPane.setColumnSpan(startSimulationButton, 2)
            add(startSimulationButton, 0, rowIndex)

            rowIndex++
            add(savePropertiesButton, 0, rowIndex)
            add(runPropertiesButton, 1, rowIndex)
        }

        val scene = Scene(root)
        primaryStage.scene = scene
        primaryStage.show()
    }
}
