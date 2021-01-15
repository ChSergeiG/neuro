package ru.chsergeig.neuro.items

class InputNeuron : AbstractNeuron() {

    override fun updateForward() {
        throw IllegalArgumentException("No inputs")
    }

    override fun updateBackwardDelta() {
        throw IllegalArgumentException("No inputs")
    }

    override fun updateBackwardOnGradient() {
        TODO("Not implemented")
    }

}
