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
        fun sigmoidDerivative(input: Double): Double {
            return sigmoid(input) * (1 - sigmoid(input))
        }

        /**
         * Вычисление нормировки на массиве значений
         *
         * Слева - нормировка сдвига, справа - нормировка разброса значений
         *
         */
        fun normalize(items: Iterable<Double>): Pair<Double, Double> {
            val min = items.minOrNull()!!
            val max = items.maxOrNull()!!
            val range = max.minus(min)
            val average = items.average()
            return average to if (range > 50.0) 50.0.div(range) else 1.0
        }

    }

}
