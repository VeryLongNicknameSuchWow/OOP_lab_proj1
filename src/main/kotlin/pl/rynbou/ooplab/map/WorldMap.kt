package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.*
import pl.rynbou.ooplab.element.MapElementCardinalDirection
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.animal.Animal
import pl.rynbou.ooplab.element.animal.AnimalBehaviourProvider
import pl.rynbou.ooplab.element.animal.AnimalMoveGene
import pl.rynbou.ooplab.element.animal.AnimalMutationProvider
import pl.rynbou.ooplab.element.plant.PlantGrowthProvider


class WorldMap(
    val simulationProperties: SimulationProperties,
    private val mapMode: MapMode,
    private val plantGrowthMode: PlantGrowthMode,
    private val animalMutationMode: AnimalMutationMode,
    private val animalBehaviourMode: AnimalBehaviourMode
) {
    val animalStorage = MapAnimalStorage(simulationProperties)
    val deadAnimalStorage = MapDeadAnimalStorage(simulationProperties)
    val plantStorage = MapPlantStorage()

    val mapMoveProvider: MapMoveProvider = mapMode.toProvider(simulationProperties, this)
    val plantGrowthProvider: PlantGrowthProvider = plantGrowthMode.toProvider(simulationProperties, deadAnimalStorage)
    val animalMutationProvider: AnimalMutationProvider = animalMutationMode.toProvider(simulationProperties)
    val animalBehaviourProvider: AnimalBehaviourProvider = animalBehaviourMode.toProvider(simulationProperties)

    init {
        for (i in 1..simulationProperties.initialAnimals) {
            animalStorage.addAnimal(
                Animal(
                    simulationProperties = simulationProperties,
                    position = MapVector2D.randomVectorInBounds(simulationProperties),
                    cardinalDirection = MapElementCardinalDirection.randomDirection(),
                    birthEpoch = 0,
                    age = 0,
                    children = 0,
                    energy = simulationProperties.initialAnimalEnergy,
                    genome = MutableList(simulationProperties.genomeLength) { AnimalMoveGene.getRandom() },
                    currentGeneIndex = 0
                )
            )
        }
    }

}
