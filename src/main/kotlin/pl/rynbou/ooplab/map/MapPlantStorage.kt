package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.plant.Plant

class MapPlantStorage {
    private val plantMap: MutableMap<MapVector2D, Plant> = HashMap()

    fun addPlant(plant: Plant) {
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
        return plantMap.keys
    }
}
