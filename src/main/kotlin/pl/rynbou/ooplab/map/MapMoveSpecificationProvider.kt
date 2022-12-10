package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.SimulationProperties

sealed class MapMoveSpecificationProvider(simulationProperties: SimulationProperties) {
    fun calculateNewPosition()
}
