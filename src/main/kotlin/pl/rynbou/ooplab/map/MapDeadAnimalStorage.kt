package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.animal.Animal
import kotlin.Double.Companion.POSITIVE_INFINITY
import kotlin.random.Random

class MapDeadAnimalStorage(simulationProperties: SimulationProperties) : MapAnimalStorage(simulationProperties) {

    private var deathStats: MutableMap<MapVector2D, Int> = HashMap()

    override fun addAnimal(animal: Animal) {
        super.addAnimal(animal)
        deathStats[animal.position] = deathStats.getOrDefault(animal.position, 0) + 1
    }

    fun getStatsAtPosition(position: MapVector2D): Int {
        return deathStats.getOrPut(position) { 0 }
    }

    fun getStatsLocalMinimum(): MapVector2D {
        var bestStat: Int = POSITIVE_INFINITY.toInt()
        var bestStatPosition = MapVector2D(0, 0)

        for (i in 1..maxOf(simulationProperties.mapHeight, simulationProperties.mapWidth)) {
            val newPosition = MapVector2D(
                Random.nextInt() % simulationProperties.mapWidth,
                Random.nextInt() % simulationProperties.mapHeight
            )

            if (deathStats.getOrPut(newPosition) { 0 } < bestStat) {
                bestStat = deathStats[newPosition]!!
                bestStatPosition = newPosition
            }
        }

        return bestStatPosition
    }

    fun calculateAvgAge(): Float {
        return animals.sumOf { it.age }.toFloat() / getAnimalsCount()
    }

}