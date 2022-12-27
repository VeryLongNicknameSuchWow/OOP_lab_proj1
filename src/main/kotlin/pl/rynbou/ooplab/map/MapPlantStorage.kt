package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.plant.Plant
import java.util.*

class MapPlantStorage(val simulationProperties: SimulationProperties) {
    private val plantMap: MutableMap<MapVector2D, Plant> = HashMap()

    fun addPlant(plant: Plant) {
        if (plant.position.x < 0 || plant.position.y < 0) {
            throw RuntimeException("Plant has negative position")
        }
        if (plant.position.x >= simulationProperties.mapWidth || plant.position.y >= simulationProperties.mapHeight) {
            throw RuntimeException("Plant has negative position")
        }

        plantMap[plant.position] = plant
    }

    fun removePlant(position: MapVector2D): Plant? {
        return plantMap.remove(position)
    }

    fun getPlant(vector2D: MapVector2D): Plant? {
        return plantMap[vector2D]
    }

    fun getPlantsCount(): Int {
        return plantMap.size
    }

    fun getOccupiedPositions(): Set<MapVector2D> {
        return Collections.unmodifiableSet(plantMap.keys)
    }
}
