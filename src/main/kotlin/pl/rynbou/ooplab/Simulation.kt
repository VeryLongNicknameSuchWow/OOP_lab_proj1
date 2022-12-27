package pl.rynbou.ooplab

import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.animal.Animal
import pl.rynbou.ooplab.element.plant.Plant
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

    fun nextEpoch() {
        statisticsProvider.saveCurrentStatistics()
        removeDeadAnimals()
        growNewPlants()
        moveAnimals()
        rotateAnimals()
        eatPlants()
        breedAnimals()
        advanceTime() // w którym miejscu to powinno być?
        // Koniec epoki
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
        val deadAnimals = mutableListOf<Animal>()

        for (animal in worldMap.animalStorage.getAllAnimals().filter { it.energy <= 0 }) {
            deadAnimals.add(animal)
        }

        for (animal in deadAnimals) {
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

            val newAnimal: Animal? = animalsAtPosition
                .lower(
                    worldMap.animalStorage
                        .getAnimalsAt(position)
                        .first()
                )
                ?.createChild(
                    worldMap.animalStorage
                        .getAnimalsAt(position)
                        .first()
                )
                ?.apply {
                    worldMap.animalStorage.addAnimal(this)
                    worldMap.animalMutationProvider.mutate(this)
                }

            // Inform GUI
        }
    }

    override fun run() {
        try {
            do {
                nextEpoch()
                Thread.sleep(1000)
            } while (worldMap.animalStorage.getAllAnimals().isEmpty().not())
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
