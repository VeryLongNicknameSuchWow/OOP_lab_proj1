package pl.rynbou.ooplab.element.animal

import pl.rynbou.ooplab.SimulationProperties
import kotlin.random.Random

sealed class AnimalBehaviourProvider(simulationProperties: SimulationProperties) {
    protected val maxHeight = simulationProperties.genomeLength

    abstract fun setNextGene(animal: Animal)

    class PredeterminedAnimalBehaviourProvider(simulationProperties: SimulationProperties) :
        AnimalBehaviourProvider(simulationProperties) {

        override fun setNextGene(animal: Animal) {
            animal.currentGeneIndex = (animal.currentGeneIndex + 1) % maxHeight
        }
    }

    class ChaoticAnimalBehaviourProvider(simulationProperties: SimulationProperties) :
        AnimalBehaviourProvider(simulationProperties) {

        override fun setNextGene(animal: Animal) {
            if (Random.nextDouble() < 0.2)
                animal.currentGeneIndex = (animal.currentGeneIndex + Random.nextInt(maxHeight)) % maxHeight
            else
                animal.currentGeneIndex = (animal.currentGeneIndex + 1) % maxHeight
        }
    }
}
