package pl.rynbou.ooplab.element.animal

enum class AnimalMoveGene(val rotation: Int) {
    DoNothing(0),
    RotateRight45(1),
    RotateRight90(2),
    RotateRight135(3),
    TurnAround(4),
    RotateLeft135(5),
    RotateLeft90(6),
    RotateLeft45(7);

    fun next(steps: Int): AnimalMoveGene {
        return cachedValues[(ordinal + steps) % cachedSize]
    }

    operator fun inc(): AnimalMoveGene {
        return AnimalMoveGene.values()[(this.ordinal + 1) % 8]
    }

    operator fun dec(): AnimalMoveGene {
        return AnimalMoveGene.values()[(this.ordinal + 7) % 8]
    }

    companion object {
        private val cachedValues = values()
        private val cachedSize = cachedValues.size

        fun getRandom(): AnimalMoveGene {
            return cachedValues.random()
        }
    }

}
