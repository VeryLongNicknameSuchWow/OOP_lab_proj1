package pl.rynbou.ooplab.element

import pl.rynbou.ooplab.SimulationProperties
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

data class MapVector2D(val x: Int, val y: Int) {
    fun add(other: MapVector2D): MapVector2D {
        return MapVector2D(x + other.x, y + other.y)
    }

    fun subtract(other: MapVector2D): MapVector2D {
        return MapVector2D(x - other.x, y - other.y)
    }

    fun opposite(): MapVector2D {
        return MapVector2D(-x, -y)
    }

    fun precedes(other: MapVector2D): Boolean {
        return this.x <= other.x && this.y <= other.y
    }

    fun follows(other: MapVector2D): Boolean {
        return this.x >= other.x && this.y >= other.y
    }

    fun upperRight(other: MapVector2D): MapVector2D {
        return MapVector2D(max(this.x, other.x), max(this.y, other.y))
    }

    fun lowerLeft(other: MapVector2D): MapVector2D {
        return MapVector2D(min(this.x, other.x), min(this.y, other.y))
    }

    companion object {
        fun randomVectorInBounds(simulationProperties: SimulationProperties): MapVector2D {
            return MapVector2D(
                Random.nextInt(simulationProperties.mapWidth),
                Random.nextInt(simulationProperties.mapHeight)
            )
        }
    }
}
