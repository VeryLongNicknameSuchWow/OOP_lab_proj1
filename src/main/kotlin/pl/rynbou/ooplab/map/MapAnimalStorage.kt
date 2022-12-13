package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.animal.Animal
import java.util.*

open class MapAnimalStorage(val simulationProperties: SimulationProperties) {
    protected val animals: MutableSet<Animal> = HashSet()
    protected val animalMap: MutableMap<MapVector2D, NavigableSet<Animal>> = HashMap()

    open fun addAnimal(animal: Animal) {
        animals.add(animal)
        animalMap.getOrPut(animal.position) {
            TreeSet(compareByDescending {
                it.energy
            })
        }.add(animal)
    }

    fun removeAnimal(animal: Animal) {
        animals.remove(animal)
        animalMap[animal.position]?.remove(animal)
    }

    fun getAnimals(vector2D: MapVector2D): NavigableSet<Animal> {
        return Collections.unmodifiableNavigableSet(animalMap[vector2D] ?: TreeSet())
    }

    fun getAnimalsCount(): Int {
        return animals.size;
    }

}
