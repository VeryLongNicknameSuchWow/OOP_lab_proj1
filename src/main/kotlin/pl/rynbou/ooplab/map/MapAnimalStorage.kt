package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.element.animal.Animal
import pl.rynbou.ooplab.element.MapVector2D
import java.util.*

class MapAnimalStorage {
    private val animals: MutableSet<Animal> = HashSet()
    private val animalMap: MutableMap<MapVector2D, NavigableSet<Animal>> = HashMap()

    fun addAnimal(animal: Animal) {
        animals.add(animal)
        animalMap.getOrPut(animal.position) { TreeSet() }.add(animal)
    }

    fun removeAnimal(animal: Animal) {
        animals.remove(animal)
        animalMap[animal.position]?.remove(animal)
    }

    fun getAnimals(vector2D: MapVector2D): NavigableSet<Animal> {
        return Collections.unmodifiableNavigableSet(animalMap[vector2D] ?: TreeSet())
    }
}
