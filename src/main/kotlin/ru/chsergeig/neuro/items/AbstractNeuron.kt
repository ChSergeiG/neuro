package ru.chsergeig.neuro.items

abstract class AbstractNeuron {

    val inputs: MutableMap<AbstractNeuron, Link> = mutableMapOf()
    val outputs: MutableMap<AbstractNeuron, Link> = mutableMapOf()

    var value: Double = 0.0

}
