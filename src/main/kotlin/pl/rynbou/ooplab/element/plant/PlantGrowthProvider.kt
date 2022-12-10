package pl.rynbou.ooplab.element.plant

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.map.MapPlantStorage

sealed class PlantGrowthProvider(simulationProperties: SimulationProperties) {
    protected val width = simulationProperties.mapWidth
    protected val height = simulationProperties.mapHeight
    protected val dailyPlants = simulationProperties.dailyPlants

    abstract fun growNewPlants(mapPlantStorage: MapPlantStorage)

    class EquatorPreference(simulationProperties: SimulationProperties) : PlantGrowthProvider(simulationProperties) {
        private val equator = height / 2

        override fun growNewPlants(mapPlantStorage: MapPlantStorage) {
            TODO()
        }
    }

    class ToxicFields(simulationProperties: SimulationProperties) : PlantGrowthProvider(simulationProperties) {
        override fun growNewPlants(mapPlantStorage: MapPlantStorage) {
            TODO("Not yet implemented")
        }
    }
}
