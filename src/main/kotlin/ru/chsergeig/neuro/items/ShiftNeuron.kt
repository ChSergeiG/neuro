package ru.chsergeig.neuro.items

class ShiftNeuron() : AbstractNeuron() {

    constructor(shift: Double) : this() {
        value = shift
    }

    override fun updateForward() {
        System.err.println("Dont need to update")
    }

    override fun updateBackwardDelta() {
        System.err.println("Dont need to update delta")
    }

    override fun updateBackwardOnGradient() {
        System.err.println("No inputs")
    }

}
