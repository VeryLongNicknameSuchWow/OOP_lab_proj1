package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.MapElementCardinalDirection
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.animal.Animal
import pl.rynbou.ooplab.element.animal.AnimalBehaviourProvider
import pl.rynbou.ooplab.element.animal.AnimalMoveGene
import pl.rynbou.ooplab.element.animal.AnimalMutationProvider
import pl.rynbou.ooplab.element.plant.PlantGrowthProvider


class Map(
    val simulationProperties: SimulationProperties,
    val mapMoveSpecificationProvider: MapMoveSpecificationProvider,
    val plantGrowthProvider: PlantGrowthProvider,
    val animalMutationProvider: AnimalMutationProvider,
    val animalBehaviourProvider: AnimalBehaviourProvider,
    val mapGeometryProvider: MapGeometryProvider
) {
    val animalStorage = MapAnimalStorage(simulationProperties)
    val plantStorage = MapPlantStorage()
    var currentEpoch = 0

    init {
        for (i in 1..simulationProperties.initialAnimals) {
            animalStorage.addAnimal(
                Animal(
                    simulationProperties = simulationProperties,
                    position = MapVector2D.randomVectorInBounds(simulationProperties),
                    cardinalDirection = MapElementCardinalDirection.randomDirection(),
                    birthEpoch = currentEpoch,
                    age = 0,
                    energy = simulationProperties.initialAnimalEnergy,
                    genome = MutableList(simulationProperties.genomeLength) { AnimalMoveGene.getRandom() },
                    currentGeneIndex = 0
                )
            )
        }
    }

}
