package pl.rynbou.ooplab.element.plant

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.map.MapDeadAnimalStorage
import pl.rynbou.ooplab.map.MapPlantStorage
import kotlin.math.abs
import kotlin.random.Random

sealed class PlantGrowthProvider(
    simulationProperties: SimulationProperties, private val deadAnimalStorage: MapDeadAnimalStorage?
) {
    protected val width = simulationProperties.mapWidth
    protected val height = simulationProperties.mapHeight
    private val dailyPlants = simulationProperties.dailyPlants

    protected abstract fun getRandomFreePosition(
        mapPlantStorage: MapPlantStorage, mapDeadAnimalStorage: MapDeadAnimalStorage?
    ): MapVector2D

    fun growNewPlants(mapPlantStorage: MapPlantStorage): List<Plant> {
        val newPlants: MutableList<Plant> = mutableListOf()

        for (i in 1..dailyPlants) {
            val newPlant = Plant(getRandomFreePosition(mapPlantStorage, deadAnimalStorage))
            mapPlantStorage.addPlant(newPlant)
            newPlants.add(newPlant)
        }

        return newPlants
    }

    class EquatorPreference(simulationProperties: SimulationProperties) :
        PlantGrowthProvider(simulationProperties, null) {
        private val equator: Double = height.toDouble() / 2

        override fun getRandomFreePosition(
            mapPlantStorage: MapPlantStorage, mapDeadAnimalStorage: MapDeadAnimalStorage?
        ): MapVector2D {
            val x = abs(Random.nextInt()) % width
            val y = abs(java.util.Random().nextGaussian(equator, height / 5.toDouble()).toInt()) % height
            val position = MapVector2D(x, y)

            return if (mapPlantStorage.getPlant(position) == null) position
            else getRandomFreePosition(mapPlantStorage, null)
        }

    }

    class ToxicFields(simulationProperties: SimulationProperties, mapDeadAnimalStorage: MapDeadAnimalStorage?) :
        PlantGrowthProvider(simulationProperties, mapDeadAnimalStorage) {

        override fun getRandomFreePosition(
            mapPlantStorage: MapPlantStorage, mapDeadAnimalStorage: MapDeadAnimalStorage?
        ): MapVector2D {
            val position = mapDeadAnimalStorage!!.getStatsLocalMinimum()

            return if (mapPlantStorage.getPlant(position) == null) position
            else getRandomFreePosition(mapPlantStorage, mapDeadAnimalStorage)
        }

    }

}
