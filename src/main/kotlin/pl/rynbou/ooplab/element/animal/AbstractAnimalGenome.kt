package pl.rynbou.ooplab.element.animal

import kotlin.random.Random

abstract class AbstractAnimalGenome {
    private val size: Int
    private val genes: MutableList<AnimalMoveGene>

    constructor(size: Int) {
        this.size = size
        this.genes = MutableList(size) { AnimalMoveGene.getRandom() }
    }

    constructor(
        primaryParentShare: Int,
        primaryParent: AbstractAnimalGenome,
        otherParent: AbstractAnimalGenome
    ) {
        this.size = primaryParent.size
        val takeFront = Random.nextBoolean()

        val dominantPart: List<AnimalMoveGene>
        val otherPart: List<AnimalMoveGene>

        if (takeFront) {
            dominantPart = primaryParent.genes.subList(0, primaryParentShare)
            otherPart = otherParent.genes.subList(size - primaryParentShare, size)
        } else {
            dominantPart = primaryParent.genes.subList(size - primaryParentShare, size)
            otherPart = otherParent.genes.subList(0, size - primaryParentShare)
        }

        val res = dominantPart.plus(otherPart)
        this.genes = res.toMutableList()
    }

    abstract fun nextMove(): AnimalMoveGene
}
