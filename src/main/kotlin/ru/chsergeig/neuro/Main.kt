package ru.chsergeig.neuro

import ru.chsergeig.neuro.MathUtils.Companion.normalize
import ru.chsergeig.neuro.MathUtils.Companion.sigmoid
import ru.chsergeig.neuro.exception.ParseValueException
import ru.chsergeig.neuro.items.*
import ru.chsergeig.neuro.model.Entry
import java.lang.Math.random
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.IntStream
import kotlin.streams.toList

class Main

// скорость обучения
const val E: Double = 0.7

// момент обучения
const val A: Double = 0.3

fun main() {

    // входной слой - 4 нейрона
    val inputLayer = addInputsLayer(4)

    // 1 промежуточный слой. 3 нейрона
    val firstLayer = addLayer(inputLayer, 3)

    // 2 промежуточный слой. 10 нейронов
    val secondLayer = addLayer(firstLayer, 10)

    // 3 промежуточный слой. 4 нейрона
    val thirdLayer = addLayer(secondLayer, 4)

    // один выходной нейрон
    val outputNeuron = addOutputsLayer(thirdLayer, 1)[0]

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
    val (dShift, dNorm) = normalize(entries.map { it.date.toDouble() })
    val (tbShift, tbNorm) = normalize(entries.map { it.transmittedBytes.toDouble() })
    val (rbShift, rbNorm) = normalize(entries.map { it.receivedBytes.toDouble() })

    Entry.normDate = dNorm
    Entry.shiftDate = dShift
    Entry.normTransmittedBytes = tbNorm
    Entry.shiftTransmittedBytes = tbShift
    Entry.normReceivedBytes = rbNorm
    Entry.shiftReceivedBytes = rbShift

    for (entry in entries) {
        inputLayer[0].value = sigmoid(Entry.normalizeDate(entry.date.toDouble()))
        inputLayer[1].value = sigmoid(Entry.normalizeService(entry.service.toDouble()))
        inputLayer[2].value = sigmoid(Entry.normalizeReceivedBytes((entry.receivedBytes.toDouble())))
        inputLayer[3].value = sigmoid(Entry.normalizeTransmittedBytes(entry.transmittedBytes.toDouble()))

        firstLayer.forEach(AbstractNeuron::updateForward)
        secondLayer.forEach(AbstractNeuron::updateForward)
        thirdLayer.forEach(AbstractNeuron::updateForward)
        outputNeuron.updateForward()

        // заполняем дельты
        outputNeuron.updateBackwardDelta(Entry.deNormalizeCpu(outputNeuron.value) - entry.cpu)
        thirdLayer.forEach(AbstractNeuron::updateBackwardDelta)
        secondLayer.forEach(AbstractNeuron::updateBackwardDelta)
        firstLayer.forEach(AbstractNeuron::updateBackwardDelta)

        // обновляем веса связей
        firstLayer.forEach(AbstractNeuron::updateBackwardOnGradient)
        secondLayer.forEach(AbstractNeuron::updateBackwardOnGradient)
        thirdLayer.forEach(AbstractNeuron::updateBackwardOnGradient)
        outputNeuron.updateBackwardOnGradient()


    }






    println(123)


}

fun addLayer(inputLayer: List<AbstractNeuron>, count: Int): MutableList<AbstractNeuron> {
    return IntStream.range(0, count)
        .boxed()
        .map {
            val neuron = HiddenNeuron()
            for (leftNeuron in inputLayer) {
                val link = Synapse(random(), leftNeuron, neuron)
                leftNeuron.outputs[neuron] = link
                neuron.inputs[leftNeuron] = link
            }
            neuron
        }
        .toList().toMutableList()
}

fun addInputsLayer(count: Int): MutableList<AbstractNeuron> {
    return IntStream.range(0, count)
        .boxed()
        .map { InputNeuron() }
        .toList().toMutableList()
}

fun addOutputsLayer(inputLayer: List<AbstractNeuron>, count: Int): MutableList<OutputNeuron> {
    return IntStream.range(0, count)
        .boxed()
        .map {
            val neuron = OutputNeuron()
            for (leftNeuron in inputLayer) {
                val link = Synapse(random(), leftNeuron, neuron)
                leftNeuron.outputs[neuron] = link
                neuron.inputs[leftNeuron] = link
            }
            neuron
        }
        .toList().toMutableList()
}
