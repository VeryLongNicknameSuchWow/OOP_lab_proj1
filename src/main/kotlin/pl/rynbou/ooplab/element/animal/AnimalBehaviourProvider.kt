package pl.rynbou.ooplab.element.animal

import pl.rynbou.ooplab.SimulationProperties
import kotlin.random.Random

sealed class AnimalBehaviourProvider(simulationProperties: SimulationProperties) {
    protected val maxHeight = simulationProperties.genomeLength

    abstract fun getNextMoveHeight(currentHeight: Int): Int

    class PredeterminedAnimalBehaviourProvider(simulationProperties: SimulationProperties) :
        AnimalBehaviourProvider(simulationProperties) {

        override fun getNextMoveHeight(currentHeight: Int): Int {
            return (currentHeight + 1) % maxHeight
        }
    }

    class ChaoticAnimalBehaviourProvider(simulationProperties: SimulationProperties) :
        AnimalBehaviourProvider(simulationProperties) {

        override fun getNextMoveHeight(currentHeight: Int): Int {
            return if (Random.nextDouble() < 0.2)
                Random.nextInt(maxHeight)
            else
                (currentHeight + 1) % maxHeight
        }
    }
}
