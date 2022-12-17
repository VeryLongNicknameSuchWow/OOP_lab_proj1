package pl.rynbou.ooplab.gui

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.Spinner
import javafx.scene.layout.GridPane
import javafx.stage.Stage
import pl.rynbou.ooplab.*

class MainGui : Application() {
    override fun start(primaryStage: Stage) {
        val mapModeComboBox = ComboBox<MapMode>().apply {
            items.addAll(MapMode.values())
            selectionModel.select(MapMode.values().first())
        }

        val mapWidthSpinner = Spinner<Int>(2, 100, 50)
        val mapHeightSpinner = Spinner<Int>(2, 100, 50)

        val plantGrowthModeComboBox = ComboBox<PlantGrowthMode>().apply {
            items.addAll(PlantGrowthMode.values())
            selectionModel.select(PlantGrowthMode.values().first())
        }

        val initialPlantsSpinner = Spinner<Int>(2, 100, 50)
        val plantEnergySpinner = Spinner<Int>(2, 100, 50)
        val dailyPlantsSpinner = Spinner<Int>(2, 100, 50)

        val animalBehaviourModeComboBox = ComboBox<AnimalBehaviourMode>().apply {
            items.addAll(AnimalBehaviourMode.values())
            selectionModel.select(AnimalBehaviourMode.values().first())
        }

        val initialAnimalsSpinner = Spinner<Int>(2, 100, 50)
        val initialAnimalEnergySpinner = Spinner<Int>(2, 100, 50)
        val fullEnergyThresholdSpinner = Spinner<Int>(2, 100, 50)
        val moveEnergyCostSpinner = Spinner<Int>(2, 100, 50)
        val copulationEnergyCostSpinner = Spinner<Int>(2, 100, 50)

        val animalMutationModeComboBox = ComboBox<AnimalMutationMode>().apply {
            items.addAll(AnimalMutationMode.values())
            selectionModel.select(AnimalMutationMode.values().first())
        }

        val mutationAmountLowerBoundSpinner = Spinner<Int>(2, 100, 50)
        val mutationAmountUpperBoundSpinner = Spinner<Int>(2, 100, 50)
        val genomeLengthSpinner = Spinner<Int>(2, 100, 50)

        val startSimulationButton = Button("Start simulation").apply {
            setOnAction {
                val simulationProperties = SimulationProperties(
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
                    genomeLengthSpinner.value
                )
                println(simulationProperties)
                TODO("Create and start a simulation")
            }
        }

        val root = GridPane().apply {
            //TODO better layout
            var rowIndex = 0

            add(Label("Map mode: "), 0, rowIndex)
            add(mapModeComboBox, 1, rowIndex)

            rowIndex++
            add(Label("Map width: "), 0, rowIndex)
            add(mapWidthSpinner, 1, rowIndex)

            rowIndex++
            add(Label("Map height: "), 0, rowIndex)
            add(mapHeightSpinner, 1, rowIndex)

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
            add(Label("Start simulation: "), 0, rowIndex)
            add(startSimulationButton, 1, rowIndex)
        }

        val scene = Scene(root)
        primaryStage.scene = scene
        primaryStage.show()
    }
}
