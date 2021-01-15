package ru.chsergeig.neuro.items

import ru.chsergeig.neuro.E
import ru.chsergeig.neuro.MathUtils
import ru.chsergeig.neuro.MathUtils.Companion.sigmoid

class OutputNeuron : AbstractNeuron() {

    override fun updateForward() {
        value = sigmoid(inputs.map { it.key.value * it.value.weight }.sum())
    }

    override fun updateBackwardDelta() {
        System.err.println("Need to set delta using updateBackwardDelta(delta)")
    }

    fun updateBackwardDelta(delta: Double) {
        this.delta = delta
        inputs.forEach { it.key.delta = this.delta * it.value.weight }
    }

    override fun updateBackwardOnGradient() {
        inputs.forEach { it.value.weight = it.value.weight + E * delta + MathUtils.sigmoidDerivative(value) * it.key.value }
        delta = 0.0
    }

}
