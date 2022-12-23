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

    val statisticsProvider = StatisticsProvider()

    fun nextEpoch() {
        statisticsProvider.saveCurrentStatistics(worldMap)
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
        for (animal in worldMap.animalStorage.getAllAnimals().filter { it.energy <= 0 }) {
            worldMap.deadAnimalStorage.addAnimal(animal)
            worldMap.animalStorage.removeAnimal(animal)
        }
    }

    private fun moveAnimals() {
        val newPositions: MutableList<MapVector2D> = mutableListOf()

        for (animal in worldMap.animalStorage.getAllAnimals()) {
            val newPosition: MapVector2D = worldMap.mapMoveProvider.calculateNewPosition(animal)

            newPositions.add(newPosition)

            worldMap.animalStorage.removeAnimal(animal)

            animal.position = newPosition
            animal.energy -= simulationProperties.moveEnergyCost

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
                worldMap.animalStorage.getAnimalsAt(position).first().energy += simulationProperties.plantEnergy
                worldMap.plantStorage.removePlant(position)
            }

        // Inform GUI
    }

    private fun breedAnimals() {
        for (position in worldMap.animalStorage.getOccupiedPositions()) {
            val newAnimal: Animal? = worldMap.animalStorage
                .getAnimalsAt(position)
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
        }
    }

}
