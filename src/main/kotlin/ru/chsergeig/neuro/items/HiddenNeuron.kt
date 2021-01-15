package ru.chsergeig.neuro.items

import ru.chsergeig.neuro.E
import ru.chsergeig.neuro.MathUtils.Companion.sigmoid
import ru.chsergeig.neuro.MathUtils.Companion.sigmoidDerivative

class HiddenNeuron : AbstractNeuron() {

    override fun updateForward() {
        value = sigmoid(inputs.map { it.key.value * it.value.weight }.sum())
    }

    override fun updateBackwardDelta() {
        inputs.forEach { it.key.delta = this.delta * it.value.weight }
    }

    override fun updateBackwardOnGradient() {
        inputs.forEach { it.value.weight = it.value.weight + E * delta + sigmoidDerivative(value) * it.key.value }
        delta = 0.0
    }

}
