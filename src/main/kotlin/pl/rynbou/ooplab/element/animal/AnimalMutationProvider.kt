package pl.rynbou.ooplab.element.animal

import pl.rynbou.ooplab.SimulationProperties
import kotlin.random.Random


sealed class AnimalMutationProvider(
    protected val simulationProperties: SimulationProperties
) {
    fun mutate(animal: Animal) {
        val mutationChance = Random.nextDouble()
        animal.genome.replaceAll { if (Random.nextDouble() <= mutationChance) it else changeGene(it) }
    }

    protected abstract fun changeGene(animalMoveGene: AnimalMoveGene): AnimalMoveGene

    class RandomAnimalMutationProvider(simulationProperties: SimulationProperties) :
        AnimalMutationProvider(simulationProperties) {
        override fun changeGene(animalMoveGene: AnimalMoveGene): AnimalMoveGene {
            return AnimalMoveGene.getRandom()
        }
    }

    class SubtleAnimalMutationProvider(simulationProperties: SimulationProperties) :
        AnimalMutationProvider(simulationProperties) {
        override fun changeGene(animalMoveGene: AnimalMoveGene): AnimalMoveGene {
            return if (Random.nextBoolean()) animalMoveGene.inc() else animalMoveGene.dec()
        }
    }
}
