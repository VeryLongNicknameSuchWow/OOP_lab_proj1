package pl.rynbou.ooplab

import javafx.application.Platform
import javafx.stage.Stage
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.animal.Animal
import pl.rynbou.ooplab.element.plant.Plant
import pl.rynbou.ooplab.gui.MapGui
import pl.rynbou.ooplab.map.WorldMap
import pl.rynbou.ooplab.statistics.StatisticsProvider

class Simulation(private val simulationProperties: SimulationProperties) : Runnable { // "implements Runnable"

    private val worldMap: WorldMap = WorldMap(
        simulationProperties,
        simulationProperties.mapMode,
        simulationProperties.plantGrowthMode,
        simulationProperties.animalMutationMode,
        simulationProperties.animalBehaviourMode,
    )

    var trackedAnimal: Animal? = null
    var currentEpoch = 0

    val statisticsProvider = StatisticsProvider(worldMap)
    val thread = Thread.currentThread()
    val mapGui = MapGui(worldMap, this)

    var paused = false
        @Synchronized get
        @Synchronized set

    var ended = false
        @Synchronized get
        @Synchronized set

    fun nextEpoch() {
        statisticsProvider.saveCurrentStatistics()
        removeDeadAnimals()
        growNewPlants()
        moveAnimals()
        rotateAnimals()
        eatPlants()
        breedAnimals()
        advanceTime() // w którym miejscu to powinno być?
        removeDeadAnimals()
        // Koniec epoki
        Platform.runLater {
            mapGui.clear()
            mapGui.drawGrass()
            mapGui.drawAnimals()
            mapGui.updateTracking()
        }
    }

    private fun advanceTime() {
        for (animal in worldMap.animalStorage.getAllAnimals()) {
            animal.age += 1
        }
        currentEpoch += 1
    }

    private fun growNewPlants() {
        val newPlants: List<Plant> = worldMap.plantGrowthProvider.growNewPlants(worldMap.plantStorage)

        // Powiedz gui, że ma nowe rośliny w liście i niech se przeczyta
    }

    private fun removeDeadAnimals() {
        val toRemove = worldMap.animalStorage.getAllAnimals().filter { it.energy <= 0 }

        for (animal in toRemove) {
            worldMap.deadAnimalStorage.addAnimal(animal)
            worldMap.animalStorage.removeAnimal(animal)
        }
    }

    private fun moveAnimals() {
        val newPositions: MutableList<MapVector2D> = mutableListOf()
        val newPositionsMap = HashMap<Animal, MapVector2D>()

        for (animal in worldMap.animalStorage.getAllAnimals()) {
            val newPosition: MapVector2D = worldMap.mapMoveProvider.calculateNewPosition(animal)

            if (animal.position != newPosition) {
                newPositions.add(newPosition)
                newPositionsMap[animal] = newPosition
            }
        }

        //remove
        for (animal in newPositionsMap.keys) {
            worldMap.animalStorage.removeAnimal(animal)
        }

        //modify
        val modifiedAnimals = mutableListOf<Animal>()
        newPositionsMap.forEach { (animal, newPos) ->
            run {
                animal.position = newPos
                animal.energy -= simulationProperties.moveEnergyCost
                modifiedAnimals.add(animal)
            }
        }

        //add
        for (animal in modifiedAnimals) {
            worldMap.animalStorage.addAnimal(animal)
        }

        // Zmień pozycje, poinformuj gui?
    }

    private fun rotateAnimals() {
        worldMap.animalStorage.getAllAnimals()
            .forEach {
                apply {
                    it.cardinalDirection = it.cardinalDirection.rotate(it.genome[it.currentGeneIndex].rotation)
                    worldMap.animalBehaviourProvider.setNextGene(it)
                }
            }
    }

    private fun eatPlants() {
        for (position in worldMap.animalStorage.getOccupiedPositions())
            if (worldMap.plantStorage.getPlant(position) != null) {
                val animalsAtPosition = worldMap.animalStorage.getAnimalsAt(position)
                if (animalsAtPosition.isEmpty()) {
                    continue
                }

                animalsAtPosition.first().energy += simulationProperties.plantEnergy
                worldMap.plantStorage.removePlant(position)
            }

        // Inform GUI
    }

    private fun breedAnimals() {
        for (position in worldMap.animalStorage.getOccupiedPositions()) {
            val animalsAtPosition = worldMap.animalStorage.getAnimalsAt(position)
            if (animalsAtPosition.size <= 1) {
                continue
            }

            val dominantParent = animalsAtPosition.first()

            val secondParent = animalsAtPosition.higher(dominantParent)

            if (secondParent == null || secondParent.energy < simulationProperties.fullEnergyThreshold) {
                continue
            }

            val newAnimal: Animal = secondParent.createChild(dominantParent)

            newAnimal.apply {
                worldMap.animalStorage.addAnimal(this)
                worldMap.animalMutationProvider.mutate(this)
            }

            // Inform GUI
        }
    }

    override fun run() {
        Platform.runLater {
            val stage = Stage()
            mapGui.start(stage)
        }


        try {
            do {
                if (!paused) {
                    nextEpoch()
                }
                Thread.sleep(1000)
            } while (worldMap.animalStorage.getAllAnimals().isEmpty().not() && !ended)
        } catch (e: InterruptedException) {
            //TODO simulation loop interrupted
        } finally {
            if (simulationProperties.statisticsFile != null) {
                statisticsProvider.saveToFile(simulationProperties.statisticsFile)
            }
            println("Simulation ended")
        }
    }
}
