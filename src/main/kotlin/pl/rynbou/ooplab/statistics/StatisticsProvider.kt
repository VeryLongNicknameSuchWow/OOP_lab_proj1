package pl.rynbou.ooplab.statistics

import pl.rynbou.ooplab.map.WorldMap
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class StatisticsProvider(val worldMap: WorldMap) {
    private val simulationStatistics: MutableList<SimulationStatistics> = mutableListOf()

    fun saveCurrentStatistics() {
        simulationStatistics.add(
            SimulationStatistics(
                countAnimals(),
                countPlants(),
                countFreeFields(),
                findDominantGenotype(),
                countAverageEnergy(),
                countAverageLifespan()
            )
        )
    }

    fun saveToFile(file: File) {

        fun OutputStream.writeCsv(simulationStatisticsList: List<SimulationStatistics>) {
            val writer = bufferedWriter()
            writer.write("Animals;Plants;Free Fields;Dominant Genotype;Avg. Energy;Avg. Lifespan")
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

    private fun countAnimals(): Int {
        return worldMap.animalStorage.getAnimalsCount()
    }

    private fun countPlants(): Int {
        return worldMap.plantStorage.getPlantsCount()
    }

    private fun countFreeFields(): Int {
        return worldMap.animalStorage
            .getOccupiedPositions()
            .union(
                worldMap.plantStorage
                    .getOccupiedPositions()
            )
            .size
    }

    private fun findDominantGenotype(): String {
        return worldMap.animalStorage
            .getAllAnimals()
            .map { it.genomeToString() }
            .groupBy { it }
            .maxBy { it.value.size }
            .key
    }

    private fun countAverageEnergy(): Double {
        return worldMap.animalStorage
            .getAllAnimals()
            .map { it.energy }
            .average()
    }

    private fun countAverageLifespan(): Double {
        return worldMap.deadAnimalStorage
            .getAllAnimals()
            .map { it.age }
            .average()
    }
}
