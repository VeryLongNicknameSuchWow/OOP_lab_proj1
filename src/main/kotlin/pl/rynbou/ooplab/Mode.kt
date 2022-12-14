package pl.rynbou.ooplab

import pl.rynbou.ooplab.element.animal.AnimalBehaviourProvider
import pl.rynbou.ooplab.element.animal.AnimalMutationProvider
import pl.rynbou.ooplab.element.plant.PlantGrowthProvider
import pl.rynbou.ooplab.map.MapGeometryProvider

enum class MapMode {
    GlobeMap,
    PortalMap;

    fun toProvider(): Any {
        return when (this) {
            GlobeMap -> MapGeometryProvider.GlobeMapGeometryProvider::class
            PortalMap -> MapGeometryProvider.PortalMapGeometryProvider::class
        }
    }
}

enum class PlantGrowthMode {
    EquatorPreference,
    ToxicFields;

    fun toProvider(): Any {
        return when (this) {
            EquatorPreference -> PlantGrowthProvider.EquatorPreference::class
            ToxicFields -> PlantGrowthProvider.ToxicFields::class
        }
    }
}

enum class GenotypeMutationMode {
    RandomMutation,
    SubtleMutation;

    fun toProvider(): Any {
        return when (this) {
            RandomMutation -> AnimalMutationProvider.RandomAnimalMutationProvider::class
            SubtleMutation -> AnimalMutationProvider.SubtleAnimalMutationProvider::class
        }
    }
}

enum class AnimalBehaviourMode {
    PredefinedBehaviour,
    ChaoticBehaviour;

    fun toProvider(): Any {
        return when (this) {
            PredefinedBehaviour -> AnimalBehaviourProvider.PredeterminedAnimalBehaviourProvider::class
            ChaoticBehaviour -> AnimalBehaviourProvider.ChaoticAnimalBehaviourProvider::class
        }
    }
}