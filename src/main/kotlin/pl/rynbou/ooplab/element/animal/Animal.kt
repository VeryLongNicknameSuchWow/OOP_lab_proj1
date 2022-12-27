package pl.rynbou.ooplab.element.animal

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.MapElementCardinalDirection
import pl.rynbou.ooplab.element.MapVector2D
import kotlin.random.Random

data class Animal(
    val simulationProperties: SimulationProperties,
    var position: MapVector2D,
    var cardinalDirection: MapElementCardinalDirection,
    var birthEpoch: Int,
    var age: Int,
    var energy: Int,
    var children: Int,
    val genome: MutableList<AnimalMoveGene>,
    var currentGeneIndex: Int
) {

    fun createChild(other: Animal): Animal {
        if (this.simulationProperties != other.simulationProperties) {
            throw RuntimeException("Those animals are not compatible") // XD
        }

        this.energy -= this.simulationProperties.copulationEnergyCost
        other.energy -= this.simulationProperties.copulationEnergyCost

        val genomeSize = this.simulationProperties.genomeLength
        val takeThisFront = Random.nextBoolean()
        val thisShare = this.energy / (this.energy + other.energy)

        val child = Animal(
            simulationProperties = this.simulationProperties,
            position = this.position,
            cardinalDirection = MapElementCardinalDirection.randomDirection(),
            birthEpoch = birthEpoch + age, //TODO: CHECK
            age = 0,
            energy = this.simulationProperties.initialAnimalEnergy,
            children = 0,
            genome = MutableList(genomeSize) {
                val index = if (takeThisFront) it else genomeSize - it
                if (it < thisShare) this.genome[index] else other.genome[index]
            },
            currentGeneIndex = 0
        )

        return child
    }

    fun genomeToString(): String {
        return genome.toString()
    }
}
