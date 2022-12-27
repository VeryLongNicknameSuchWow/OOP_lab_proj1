package pl.rynbou.ooplab.element.plant

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.map.MapDeadAnimalStorage
import pl.rynbou.ooplab.map.MapPlantStorage
import kotlin.math.abs
import kotlin.math.max
import kotlin.random.Random

sealed class PlantGrowthProvider(
    simulationProperties: SimulationProperties, private val deadAnimalStorage: MapDeadAnimalStorage?
) {
    protected val width = simulationProperties.mapWidth
    protected val height = simulationProperties.mapHeight
    private val dailyPlants = simulationProperties.dailyPlants

    protected abstract fun getRandomFreePosition(
        mapPlantStorage: MapPlantStorage, mapDeadAnimalStorage: MapDeadAnimalStorage?, depth: Int = 0
    ): MapVector2D?

    fun growNewPlants(mapPlantStorage: MapPlantStorage): List<Plant> {
        val newPlants: MutableList<Plant> = mutableListOf()

        for (i in 1..dailyPlants) {
            try {
                val newPlant = getRandomFreePosition(mapPlantStorage, deadAnimalStorage)?.let { Plant(it) }
                if (newPlant != null) {
                    mapPlantStorage.addPlant(newPlant)
                    newPlants.add(newPlant)
                }
            } catch (e: StackOverflowError) {
                apply {}
            }
        }

        return newPlants
    }

    class EquatorPreference(simulationProperties: SimulationProperties) :
        PlantGrowthProvider(simulationProperties, null) {
        private val equator: Double = height.toDouble() / 2

        override fun getRandomFreePosition(
            mapPlantStorage: MapPlantStorage, mapDeadAnimalStorage: MapDeadAnimalStorage?, depth: Int
        ): MapVector2D? {
            val x = abs(Random.nextInt()) % width
            val y = abs(java.util.Random().nextGaussian(equator, height / 5.toDouble()).toInt()) % height
            val position = MapVector2D(x, y)

            return if (mapPlantStorage.getPlant(position) == null) position
            else if (depth < max(width, height)) getRandomFreePosition(mapPlantStorage, null, depth + 1)
            else null
        }

    }

    class ToxicFields(simulationProperties: SimulationProperties, mapDeadAnimalStorage: MapDeadAnimalStorage?) :
        PlantGrowthProvider(simulationProperties, mapDeadAnimalStorage) {

        override fun getRandomFreePosition(
            mapPlantStorage: MapPlantStorage, mapDeadAnimalStorage: MapDeadAnimalStorage?, depth: Int
        ): MapVector2D? {
            val position = mapDeadAnimalStorage!!.getStatsLocalMinimum()

            return if (mapPlantStorage.getPlant(position) == null) position
            else if (depth < max(width, height)) getRandomFreePosition(mapPlantStorage, mapDeadAnimalStorage, depth + 1)
            else null
        }

    }

}
