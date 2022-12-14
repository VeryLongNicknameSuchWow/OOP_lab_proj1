package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.animal.Animal

sealed class MapMoveSpecificationProvider(simulationProperties: SimulationProperties, worldMap: WorldMap) {

    val mapGeometryProvider: MapGeometryProvider = worldMap.mapGeometryProvider

    abstract fun calculateNewPosition(animal: Animal): MapVector2D

    class GlobeMapMoveSpecificationProvider(simulationProperties: SimulationProperties, worldMap: WorldMap) :
        MapMoveSpecificationProvider(simulationProperties, worldMap) {
        override fun calculateNewPosition(animal: Animal): MapVector2D {
            return mapGeometryProvider.transformCoordinates(animal.cardinalDirection.toMapVector2D())
        }
    }

    class PortalMapMoveSpecificationProvider(
        simulationProperties: SimulationProperties,
        worldMap: WorldMap
    ) : MapMoveSpecificationProvider(simulationProperties, worldMap) {
        override fun calculateNewPosition(animal: Animal): MapVector2D {
            return mapGeometryProvider.transformCoordinates(animal.cardinalDirection.toMapVector2D())
        }
    }

}
