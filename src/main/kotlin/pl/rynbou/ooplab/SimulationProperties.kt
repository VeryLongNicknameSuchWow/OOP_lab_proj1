package pl.rynbou.ooplab

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.io.File


@Serializable
data class SimulationProperties(
    val mapMode: MapMode,
    val mapWidth: Int,
    val mapHeight: Int,

    val plantGrowthMode: PlantGrowthMode,
    val initialPlants: Int,
    val plantEnergy: Int,
    val dailyPlants: Int,

    val animalBehaviourMode: AnimalBehaviourMode,
    val initialAnimals: Int,
    val initialAnimalEnergy: Int,
    val fullEnergyThreshold: Int,
    val moveEnergyCost: Int,
    val copulationEnergyCost: Int,

    val animalMutationMode: AnimalMutationMode,
    val mutationAmountLowerBound: Int,
    val mutationAmountUpperBound: Int,
    val genomeLength: Int,

    @Transient
    val statisticsFile: File? = null
)
