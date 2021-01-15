package ru.chsergeig.neuro.items

abstract class AbstractNeuron {

    val inputs: MutableMap<AbstractNeuron, Synapse> = mutableMapOf()
    val outputs: MutableMap<AbstractNeuron, Synapse> = mutableMapOf()

    // значение нейрона
    var value: Double = 0.0

    // сдвиг значения для обратного распространения
    var delta: Double = 0.0

    // прямое распространение
    abstract fun updateForward()

    // обратное распространение - расчет дельт
    abstract fun updateBackwardDelta()

    // обратное распространение - нормализация градиентов, обновление весов связей
    abstract fun updateBackwardOnGradient()

}
