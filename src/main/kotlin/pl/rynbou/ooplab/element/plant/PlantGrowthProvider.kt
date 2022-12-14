package pl.rynbou.ooplab.element.plant

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.map.MapDeadAnimalStorage
import pl.rynbou.ooplab.map.MapPlantStorage
import kotlin.random.Random

sealed class PlantGrowthProvider(simulationProperties: SimulationProperties) {
    protected val width = simulationProperties.mapWidth
    protected val height = simulationProperties.mapHeight
    protected val dailyPlants = simulationProperties.dailyPlants

    abstract fun growNewPlants(
        mapPlantStorage: MapPlantStorage,
        mapDeadAnimalStorage: MapDeadAnimalStorage? = null
    ): List<Plant>

    class EquatorPreference(simulationProperties: SimulationProperties) : PlantGrowthProvider(simulationProperties) {
        private val equator = height / 2

        override fun growNewPlants(
            mapPlantStorage: MapPlantStorage,
            mapDeadAnimalStorage: MapDeadAnimalStorage?
        ): List<Plant> {
            val newPlants: MutableList<Plant> = mutableListOf()

            for (i in 1..dailyPlants) {
                val newPlant = Plant(getRandomFreePosition(mapPlantStorage))
                mapPlantStorage.addPlant(newPlant)
                newPlants.add(newPlant)
            }

            return newPlants
        }

        private fun getRandomFreePosition(mapPlantStorage: MapPlantStorage): MapVector2D {
            val x = Random.nextInt() % width
            val y = java.util.Random().nextGaussian(equator.toDouble(), height / 5.toDouble()).toInt() % height
            val position = MapVector2D(x, y)

            return if (mapPlantStorage.getPlant(position) == null)
                position
            else getRandomFreePosition(mapPlantStorage)
        }

    }

    class ToxicFields(simulationProperties: SimulationProperties) : PlantGrowthProvider(simulationProperties) {
        override fun growNewPlants(
            mapPlantStorage: MapPlantStorage,
            mapDeadAnimalStorage: MapDeadAnimalStorage?
        ): List<Plant> {
            val newPlants: MutableList<Plant> = mutableListOf()

            for (i in 1..dailyPlants) {
                val newPlant = Plant(getRandomFreePosition(mapPlantStorage, mapDeadAnimalStorage!!))
                mapPlantStorage.addPlant(newPlant)
                newPlants.add(newPlant)
            }

            return newPlants
        }

        private fun getRandomFreePosition(
            mapPlantStorage: MapPlantStorage,
            mapDeadAnimalStorage: MapDeadAnimalStorage
        ): MapVector2D {
            val position = mapDeadAnimalStorage.getStatsLocalMinimum()

            return if (mapPlantStorage.getPlant(position) == null)
                position
            else getRandomFreePosition(mapPlantStorage, mapDeadAnimalStorage)
        }

    }

}
