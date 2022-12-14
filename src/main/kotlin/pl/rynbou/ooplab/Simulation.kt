package pl.rynbou.ooplab

import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.animal.Animal
import pl.rynbou.ooplab.element.animal.AnimalBehaviourProvider
import pl.rynbou.ooplab.element.animal.AnimalMutationProvider
import pl.rynbou.ooplab.element.plant.Plant
import pl.rynbou.ooplab.element.plant.PlantGrowthProvider
import pl.rynbou.ooplab.map.MapMoveProvider
import pl.rynbou.ooplab.map.WorldMap

class Simulation(private val simulationProperties: SimulationProperties) { // "implements Runnable"

    private val worldMap: WorldMap = WorldMap(
        simulationProperties,
        simulationProperties.mapMode.toProvider() as MapMoveProvider,
        simulationProperties.plantGrowthMode.toProvider() as PlantGrowthProvider,
        simulationProperties.animalMutationMode.toProvider() as AnimalMutationProvider,
        simulationProperties.animalBehaviourMode.toProvider() as AnimalBehaviourProvider,
    )

    var trackedAnimal: Animal? = null

    fun nextEpoch() {
        removeDeadAnimals()
        growNewPlants()
        moveAnimals()
        eatPlants()
        breedAnimals()
        saveStats()
        // Koniec epoki
    }

    private fun growNewPlants() {
        val newPlants: List<Plant> = worldMap.plantGrowthProvider.growNewPlants(worldMap.plantStorage)
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

    private fun eatPlants() {

    }

    private fun breedAnimals() {

    }

    private fun saveStats() {
        // Zapisać wszystkie haszmapy do tablicy/pliku? XD
    }

}
