package pl.rynbou.ooplab.statistics

import pl.rynbou.ooplab.map.WorldMap
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class StatisticsProvider {
    private val simulationStatistics: MutableList<SimulationStatistics> = mutableListOf()

    fun saveCurrentStatistics(worldMap: WorldMap) {
        simulationStatistics.add(
            SimulationStatistics(
                countAnimals(worldMap),
                countPlants(worldMap),
                countFreeFields(worldMap),
                findDominantGenotype(worldMap),
                countAverageEnergy(worldMap),
                countAverageLifespan(worldMap)
            )
        )
    }

    fun saveToFile(file: File) {

        fun OutputStream.writeCsv(simulationStatisticsList: List<SimulationStatistics>) {
            val writer = bufferedWriter()
            writer.write(""""Animals";"Plants";"Free Fields";"Dominant Genotype";"Avg. Energy";"Avg. Lifespan"""")
            writer.newLine()
            simulationStatisticsList.forEach {
                writer.write(
                    "${it.animalsCount};" +
                            "${it.plantsCount};" +
                            "${it.freeFieldsCount};" +
                            "${it.dominantGenotype};" +
                            "${it.averageEnergy};" +
                            "${it.averageLifeSpan}"
                )
                writer.newLine()
            }
            writer.flush()
        }

        FileOutputStream(file).apply { writeCsv(simulationStatistics) }
    }

    private fun countAnimals(worldMap: WorldMap): Int {
        return worldMap.animalStorage.getAnimalsCount()
    }

    private fun countPlants(worldMap: WorldMap): Int {
        return worldMap.plantStorage.getPlantsCount()
    }

    private fun countFreeFields(worldMap: WorldMap): Int {
        return worldMap.animalStorage
            .getOccupiedPositions()
            .union(
                worldMap.plantStorage
                    .getOccupiedPositions()
            )
            .size
    }

    private fun findDominantGenotype(worldMap: WorldMap) {
        TODO()
    }

    private fun countAverageEnergy(worldMap: WorldMap): Double {
        return worldMap.animalStorage
            .getAllAnimals()
            .map { it.energy }
            .average()
    }

    private fun countAverageLifespan(worldMap: WorldMap): Double {
        return worldMap.deadAnimalStorage
            .getAllAnimals()
            .map { it.age }
            .average()
    }
}
