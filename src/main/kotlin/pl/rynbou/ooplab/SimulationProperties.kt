package pl.rynbou.ooplab


data class SimulationProperties(
    val mapWidth: Int,
    val mapHeight: Int,

    val initialPlants: Int,
    val plantEnergy: Int,
    val dailyPlants: Int,

    val initialAnimals: Int,
    val initialAnimalEnergy: Int,
    val fullEnergyThreshold: Int,
    val moveEnergyCost: Int,
    val copulationEnergyCost: Int,

    val mutationAmountLowerBound: Int,
    val mutationAmountUpperBound: Int,
    val genomeLength: Int,
)
