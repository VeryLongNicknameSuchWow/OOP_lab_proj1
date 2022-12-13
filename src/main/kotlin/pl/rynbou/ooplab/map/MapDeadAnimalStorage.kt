package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.animal.Animal

class MapDeadAnimalStorage : MapAnimalStorage() {

    private var deathStats: MutableMap<MapVector2D, Int> = HashMap()

    override fun addAnimal(animal: Animal){
        super.addAnimal(animal)
        deathStats[animal.position] = deathStats.getOrDefault(animal.position, 0) + 1
    }

    fun calculateAvgAge(): Float {
        return animals.sumOf{it.age }.toFloat() / getAnimalsCount()
    }

}