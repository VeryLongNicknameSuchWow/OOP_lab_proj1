package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.animal.Animal

sealed class MapMoveSpecificationProvider(simulationProperties: SimulationProperties) {
    abstract fun calculateNewPosition(animal: Animal)

    
}
