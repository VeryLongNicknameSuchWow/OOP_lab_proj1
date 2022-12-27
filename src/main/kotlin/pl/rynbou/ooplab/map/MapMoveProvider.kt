package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.animal.Animal
import kotlin.math.abs
import kotlin.random.Random

sealed class MapMoveProvider(simulationProperties: SimulationProperties, worldMap: WorldMap) {

    val height = simulationProperties.mapHeight
    val width = simulationProperties.mapWidth

    abstract fun transformCoordinates(position: MapVector2D): MapVector2D?

    fun calculateNewPosition(animal: Animal): MapVector2D {
        return transformCoordinates(animal.position.add(animal.cardinalDirection.moveVector)) ?: animal.position
    }

    class GlobeMapMoveProvider(simulationProperties: SimulationProperties, worldMap: WorldMap) :
        MapMoveProvider(simulationProperties, worldMap) {
        override fun transformCoordinates(position: MapVector2D): MapVector2D? {
            if (position.y < 0)
                return null

            if (position.y >= height)
                return null

            return MapVector2D((position.x + width) % width, position.y)
        }
    }

    class PortalMapMoveProvider(
        simulationProperties: SimulationProperties,
        worldMap: WorldMap
    ) : MapMoveProvider(simulationProperties, worldMap) {

        override fun transformCoordinates(position: MapVector2D): MapVector2D {
            if (position.x in 0 until width && position.y in 0 until height)
                return position

            return MapVector2D(abs(Random.nextInt()) % width, abs(Random.nextInt()) % height)
        }

    }

}
