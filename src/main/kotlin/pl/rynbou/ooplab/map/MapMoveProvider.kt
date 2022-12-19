package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.animal.Animal
import kotlin.random.Random

sealed class MapMoveProvider(simulationProperties: SimulationProperties, worldMap: WorldMap) {

    val height = simulationProperties.mapHeight
    val width = simulationProperties.mapWidth

    abstract fun transformCoordinates(position: MapVector2D): MapVector2D

    fun calculateNewPosition(animal: Animal): MapVector2D {
        return transformCoordinates(animal.cardinalDirection.moveVector)
    }

    class GlobeMapMoveProvider(simulationProperties: SimulationProperties, worldMap: WorldMap) :
        MapMoveProvider(simulationProperties, worldMap) {
        override fun transformCoordinates(position: MapVector2D): MapVector2D {
            if (position.y < 0)
                return position

            if (position.y > height)
                return position

            return MapVector2D(position.x % width, position.y)
        }
    }

    class PortalMapMoveProvider(
        simulationProperties: SimulationProperties,
        worldMap: WorldMap
    ) : MapMoveProvider(simulationProperties, worldMap) {

        override fun transformCoordinates(position: MapVector2D): MapVector2D {
            if (position.x in 0 until width && position.y in 0 until height)
                return position

            return MapVector2D(Random.nextInt() % width, Random.nextInt() % height)
        }

    }

}
