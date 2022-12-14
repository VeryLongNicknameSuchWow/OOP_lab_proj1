package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.MapVector2D
import kotlin.random.Random

sealed class MapGeometryProvider(simulationProperties: SimulationProperties) {

    val width = simulationProperties.mapWidth
    val height = simulationProperties.mapHeight

    abstract fun transformCoordinates(position: MapVector2D): MapVector2D

    class GlobeMapGeometryProvider(simulationProperties: SimulationProperties) :
        MapGeometryProvider(simulationProperties) {

        override fun transformCoordinates(position: MapVector2D): MapVector2D {
            if (position.y < 0)
                return position

            if (position.y > height)
                return position

            return MapVector2D(position.x % width, position.y)
        }

    }

    class PortalMapGeometryProvider(simulationProperties: SimulationProperties) :
        MapGeometryProvider(simulationProperties) {

        override fun transformCoordinates(position: MapVector2D): MapVector2D {
            if (position.x in 0 until width && position.y in 0 until height)
                return position

            return MapVector2D(Random.nextInt() % width, Random.nextInt() % height)
        }

    }

}