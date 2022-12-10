package pl.rynbou.ooplab.element.animal

import pl.rynbou.ooplab.element.AbstractMapElement
import pl.rynbou.ooplab.element.MapElementCardinalDirection
import pl.rynbou.ooplab.element.MapVector2D

class Animal(
    position: MapVector2D,
    var cardinalDirection: MapElementCardinalDirection,
    var birthEpoch: Int,
    var age: Int,
    var energy: Int
) : AbstractMapElement(position) {

}
