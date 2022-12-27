package pl.rynbou.ooplab.map

import pl.rynbou.ooplab.SimulationProperties
import pl.rynbou.ooplab.element.MapVector2D
import pl.rynbou.ooplab.element.animal.Animal
import java.util.*

open class MapAnimalStorage(val simulationProperties: SimulationProperties) {
    private val animals: MutableSet<Animal> = HashSet()
    private val animalMap: MutableMap<MapVector2D, NavigableSet<Animal>> = HashMap()

    open fun addAnimal(animal: Animal) {
        if (animal.position.x < 0 || animal.position.y < 0) {
            throw RuntimeException("Animal has negative position")
        }
        if (animal.position.x >= simulationProperties.mapWidth || animal.position.y >= simulationProperties.mapHeight) {
            throw RuntimeException("Animal has negative position")
        }

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
        if (animalMap[animal.position]?.isEmpty() == true) {
            animalMap.remove(animal.position)
        }
    }

    fun getAnimalsAt(vector2D: MapVector2D): NavigableSet<Animal> {
        return Collections.unmodifiableNavigableSet(animalMap[vector2D] ?: TreeSet())
    }

    fun getAllAnimals(): Set<Animal> {
        return Collections.unmodifiableSet(animals)
    }

    fun getOccupiedPositions(): Set<MapVector2D> {
        return Collections.unmodifiableSet(animalMap.keys)
    }

    fun getAnimalsCount(): Int {
        return animals.size;
    }

}
