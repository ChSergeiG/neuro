package ru.chsergeig.neuro

import kotlin.math.exp

class MathUtils private constructor() {

    init {
        throw IllegalArgumentException("Cant instantiate util class")
    }

    companion object {

        @JvmStatic
        fun sigmoid(input: Double): Double {
            return 1 / (1 + exp(-input))
        }
        
        @JvmStatic
        fun sigmoidDerivative (input: Double) : Double {
            return sigmoid(input) * (1 - sigmoid(input))
        }
    }

}
