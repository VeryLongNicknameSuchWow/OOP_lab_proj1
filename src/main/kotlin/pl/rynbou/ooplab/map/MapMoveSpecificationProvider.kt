package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.animal.Animal

sealed class MapMoveSpecificationProvider(simulationProperties: SimulationProperties) {
    abstract fun calculateNewPosition(animal: Animal)

    class GlobeMapMoveSpecificationProvider(simulationProperties: SimulationProperties): MapMoveSpecificationProvider(simulationProperties) {

        override fun calculateNewPosition(animal: Animal) {
            val newPosition: MapVector2D = animal.cardinalDirection.toMapVector2D()
        }

    }

    
}
