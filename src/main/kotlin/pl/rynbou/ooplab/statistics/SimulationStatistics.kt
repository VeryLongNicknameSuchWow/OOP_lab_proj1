package pl.rynbou.ooplab.statistics

data class SimulationStatistics(
    val animalsCount: Int,
    val plantsCount: Int,
    val freeFieldsCount: Int,
    val dominantGenotype: String,
    val averageEnergy: Double,
    val averageLifeSpan: Double,
) {
}
