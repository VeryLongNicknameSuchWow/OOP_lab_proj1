package pl.rynbou.ooplab.statistics

import pl.rynbou.ooplab.map.WorldMap

data class SimulationStatistics(
    val animalsCount: Int,
    val plantsCount: Int,
    val freeFieldsCount: Int,
    val dominantGenotype: Any,
    val averageEnergy: Float,
    val averageLifeSpan: Float,
) {
    companion object Statistics {
        private val simulationStatistics: MutableList<SimulationStatistics> = mutableListOf()

        fun saveCurrentStatistics(worldMap: WorldMap) {
            simulationStatistics.add(
                SimulationStatistics(
                    countAnimals(worldMap),
                    countPlants(worldMap),
                    countFreeFields(worldMap),
                    findDominantGenotype(worldMap),
                    countAverageEnergy(worldMap),
                    countAverageLifeSpan(worldMap)
                )
            )
        }

        fun saveToFile() {
            TODO()
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

        private fun countAverageEnergy(worldMap: WorldMap): Float {
            return worldMap.animalStorage
                .getAllAnimals()
                .sumOf { it.energy }
                .toFloat() / worldMap.animalStorage.getAnimalsCount()
        }

        private fun countAverageLifeSpan(worldMap: WorldMap): Float {
            return worldMap.deadAnimalStorage
                .getAllAnimals()
                .sumOf { it.age }
                .toFloat() / worldMap.deadAnimalStorage.getAnimalsCount()
        }
    }
}