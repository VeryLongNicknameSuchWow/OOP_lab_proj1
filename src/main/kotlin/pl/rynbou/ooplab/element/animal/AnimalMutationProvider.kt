package pl.rynbou.ooplab.element.animal

import pl.rynbou.ooplab.SimulationProperties


sealed class AnimalMutationProvider(
    protected val simulationProperties: SimulationProperties
) {
    abstract fun mutate(animal: Animal)

    class RandomAnimalMutationProvider(simulationProperties: SimulationProperties) :
        AnimalMutationProvider(simulationProperties) {
        override fun mutate(animal: Animal) {
            TODO()
        }
    }

    class SubtleAnimalMutationProvider(simulationProperties: SimulationProperties) :
        AnimalMutationProvider(simulationProperties) {
        override fun mutate(animal: Animal) {
            TODO()
        }
    }
}
