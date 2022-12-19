package pl.rynbou.ooplab

import pl.rynbou.ooplab.element.animal.AnimalBehaviourProvider
import pl.rynbou.ooplab.element.animal.AnimalMutationProvider
import pl.rynbou.ooplab.element.plant.PlantGrowthProvider
import pl.rynbou.ooplab.map.MapDeadAnimalStorage
import pl.rynbou.ooplab.map.MapMoveProvider
import pl.rynbou.ooplab.map.WorldMap

enum class MapMode {
    GlobeMap,
    PortalMap;

    fun toProvider(simulationProperties: SimulationProperties, worldMap: WorldMap): MapMoveProvider {
        return when (this) {
            GlobeMap -> MapMoveProvider.GlobeMapMoveProvider(simulationProperties, worldMap)
            PortalMap -> MapMoveProvider.PortalMapMoveProvider(simulationProperties, worldMap)
        }
    }
}

enum class PlantGrowthMode {
    EquatorPreference,
    ToxicFields;

    fun toProvider(
        simulationProperties: SimulationProperties,
        deadAnimalStorage: MapDeadAnimalStorage? = null
    ): PlantGrowthProvider {
        return when (this) {
            EquatorPreference -> PlantGrowthProvider.EquatorPreference(simulationProperties)
            ToxicFields -> PlantGrowthProvider.ToxicFields(simulationProperties, deadAnimalStorage)
        }
    }
}

enum class AnimalMutationMode {
    RandomMutation,
    SubtleMutation;

    fun toProvider(simulationProperties: SimulationProperties): AnimalMutationProvider {
        return when (this) {
            RandomMutation -> AnimalMutationProvider.RandomAnimalMutationProvider(simulationProperties)
            SubtleMutation -> AnimalMutationProvider.SubtleAnimalMutationProvider(simulationProperties)
        }
    }
}

enum class AnimalBehaviourMode {
    PredefinedBehaviour,
    ChaoticBehaviour;

    fun toProvider(simulationProperties: SimulationProperties): AnimalBehaviourProvider {
        return when (this) {
            PredefinedBehaviour -> AnimalBehaviourProvider.PredeterminedAnimalBehaviourProvider(simulationProperties)
            ChaoticBehaviour -> AnimalBehaviourProvider.ChaoticAnimalBehaviourProvider(simulationProperties)
        }
    }
}
