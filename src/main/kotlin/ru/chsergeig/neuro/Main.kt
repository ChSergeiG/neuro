package ru.chsergeig.neuro

import ru.chsergeig.neuro.exception.ParseValueException
import ru.chsergeig.neuro.items.*
import ru.chsergeig.neuro.model.Entry
import java.lang.Math.random
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths

class Main

fun main() {

    // входной слой - 4 нейрона
    val inputLayer: MutableList<AbstractNeuron> = mutableListOf()
    for (i in 0..3) inputLayer.add(InputNeuron())

    // 1 промежуточный слой. 3 нейрона
    val firstLayer: MutableList<AbstractNeuron> = mutableListOf()
    for (i in 0..2) {
        val firstLayerNeuron = HiddenNeuron()
        firstLayer.add(firstLayerNeuron)
        for (leftNeuron in inputLayer) {
            val link = Link(random(), leftNeuron, firstLayerNeuron)
            leftNeuron.outputs[firstLayerNeuron] = link
            firstLayerNeuron.inputs[leftNeuron] = link
        }
    }

    // 2 промежуточный слой. 10 нейронов
    val secondLayer: MutableList<AbstractNeuron> = mutableListOf()
    for (i in 0..9) {
        val secondLayerNeuron = HiddenNeuron()
        secondLayer.add(secondLayerNeuron)
        for (leftNeuron in firstLayer) {
            val link = Link(random(), leftNeuron, secondLayerNeuron)
            leftNeuron.outputs[secondLayerNeuron] = link
            secondLayerNeuron.inputs[leftNeuron] = link
        }
    }

    // 3 промежуточный слой. 4 нейрона
    val thirdLayer: MutableList<AbstractNeuron> = mutableListOf()
    for (i in 0..3) {
        val thirdLayerNeuron = HiddenNeuron()
        thirdLayer.add(thirdLayerNeuron)
        for (leftNeuron in secondLayer) {
            val link = Link(random(), leftNeuron, thirdLayerNeuron)
            leftNeuron.outputs[thirdLayerNeuron] = link
            thirdLayerNeuron.inputs[leftNeuron] = link
        }
    }

    val outputNeuron = OutputNeuron()
    for (leftNeuron in thirdLayer) {
        val link = Link(random(), leftNeuron, outputNeuron)
        leftNeuron.outputs[outputNeuron] = link
        outputNeuron.inputs[leftNeuron] = link
    }

    val train: URL = Main::class.java.classLoader.getResource("train.csv")!!
    val entries: List<Entry> = Files.readAllLines(Paths.get(train.toURI()))
        .mapNotNull { line ->
            try {
                Entry(line)
            } catch (pve: ParseValueException) {
                println(pve.message)
                null
            }
        }
        .toList()







    println(123)


}