package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.animal.Animal

sealed class MapMoveSpecificationProvider(simulationProperties: SimulationProperties, map: pl.rynbou.ooplab.map.Map) {

    val mapGeometryProvider: MapGeometryProvider = map.mapGeometryProvider

    abstract fun calculateNewPosition(animal: Animal): MapVector2D

    class GlobeMapMoveSpecificationProvider(simulationProperties: SimulationProperties, map: pl.rynbou.ooplab.map.Map): MapMoveSpecificationProvider(simulationProperties, map) {
        override fun calculateNewPosition(animal: Animal): MapVector2D {
            return mapGeometryProvider.transformCoordinates(animal.cardinalDirection.toMapVector2D())
        }
    }

    class PortalMapMoveSpecificationProvider(simulationProperties: SimulationProperties, map: pl.rynbou.ooplab.map.Map): MapMoveSpecificationProvider(simulationProperties, map) {
        override fun calculateNewPosition(animal: Animal): MapVector2D {
            return mapGeometryProvider.transformCoordinates(animal.cardinalDirection.toMapVector2D())
        }
    }
    
}
