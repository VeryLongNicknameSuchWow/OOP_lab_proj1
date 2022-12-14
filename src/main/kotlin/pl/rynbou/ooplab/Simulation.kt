package pl.rynbou.ooplab

import pl.rynbou.ooplab.element.animal.Animal

class Simulation(private val simulationProperties: SimulationProperties) { // "implements Runnable"

    private val map: pl.rynbou.ooplab.map.Map = pl.rynbou.ooplab.map.Map(simulationProperties, null, null, null, null, null)
    var trackedAnimal: Animal? = null

    fun nextEpoch(){
        map.plantGrowthProvider.growNewPlants(map.plantStorage)
        // przemieść zwierzaki na nowe pola (za pomocą MapMoveSpecificationProvider?)
        // Rozmnóż zwierzaki
        // Zapisz statystyki
        // Koniec epoki
    }

}
