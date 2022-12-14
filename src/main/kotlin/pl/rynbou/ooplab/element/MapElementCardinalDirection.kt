package pl.rynbou.ooplab.element

enum class MapElementCardinalDirection(
    val moveVector: MapVector2D
) {
    NORTH(MapVector2D(0, 1)),
    NORTHEAST(MapVector2D(1, 1)),
    EAST(MapVector2D(1, 0)),
    SOUTHEAST(MapVector2D(1, -1)),
    SOUTH(MapVector2D(0, -1)),
    SOUTHWEST(MapVector2D(-1, -1)),
    WEST(MapVector2D(-1, 0)),
    NORTHWEST(MapVector2D(-1, 1));

    fun rotate(steps: Int): MapElementCardinalDirection {
        return cachedValues[(ordinal + steps) % cachedSize]
    }

    fun reverse(): MapElementCardinalDirection {
        return rotate(4)
    }

    fun toMapVector2D(): MapVector2D {
        return when(this) {
            NORTH -> MapVector2D(0, 1)
            NORTHEAST -> MapVector2D(1, 1)
            EAST -> MapVector2D(1, 0)
            SOUTHEAST -> MapVector2D(1, -1)
            SOUTH -> MapVector2D(0, -1)
            SOUTHWEST -> MapVector2D(-1, -1)
            WEST -> MapVector2D(-1, 0)
            NORTHWEST -> MapVector2D(-1, 1)
        }
    }

    companion object {
        private val cachedValues = values()
        private val cachedSize = values().size

        fun randomDirection(): MapElementCardinalDirection {
            return cachedValues.random()
        }
    }
}
