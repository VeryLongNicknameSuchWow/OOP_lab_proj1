package pl.rynbou.ooplab.element.animal

import kotlin.random.Random

sealed interface AnimalMutationProvider {
    fun mutate(gene: AnimalMoveGene): AnimalMoveGene

    object RandomAnimalMutationProvider : AnimalMutationProvider {
        override fun mutate(gene: AnimalMoveGene): AnimalMoveGene {
            return AnimalMoveGene.getRandom()
        }
    }

    object SubtleAnimalMutationProvider : AnimalMutationProvider {
        override fun mutate(gene: AnimalMoveGene): AnimalMoveGene {
            return gene.next(Random.nextInt(-1, 2))
        }
    }
}
