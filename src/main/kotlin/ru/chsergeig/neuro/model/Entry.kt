package ru.chsergeig.neuro.model

import ru.chsergeig.neuro.exception.ParseValueException
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt
import java.lang.Long.parseLong
import kotlin.math.pow

class Entry(
    iDate: String,
    iServiceName: String,
    iCpu: String,
    iReceivedBytes: String,
    iTransmittedBytes: String
) {

    val date: Long
    val service: Int
    val cpu: Double
    val receivedBytes: Long
    val transmittedBytes: Long

    init {
        try {
            date = parseLong(iDate)
        } catch (ex: Exception) {
            throw ParseValueException("Cant parse $iDate as Long", ex)
        }
        service = parseService(iServiceName)
        cpu = parseScientific(iCpu)
        receivedBytes = parseScientific(iReceivedBytes).toLong()
        transmittedBytes = parseScientific(iTransmittedBytes).toLong()
    }

    constructor(line: String) : this(line.split(","))

    constructor(chunk: List<String>) : this(chunk[0], chunk[1], chunk[2], chunk[3], chunk[4])

    private fun parseService(iServiceName: String): Int {
        try {
            return parseInt(iServiceName.replace("srv", ""))
        } catch (ex: Exception) {
            throw ParseValueException("Cant parse $iServiceName as Service number", ex)
        }
    }

    private fun parseScientific(input: String): Double {
        val chunk: List<String> = input.split("e", "E")
        when (chunk.size) {
            0 -> throw ParseValueException("Cant parse $input as scientific number")
            1 -> {
                try {
                    return parseDouble(chunk[0])
                } catch (ex: Exception) {
                    throw ParseValueException("Cant parse $input as scientific number", ex)
                }
            }
            2 -> {
                val mantis: Double
                val power: Int
                try {
                    mantis = parseDouble(chunk[0])
                } catch (ex: Exception) {
                    throw ParseValueException("Cant parse $input as scientific number", ex)
                }
                try {
                    power = parseInt(chunk[1])
                } catch (ex: Exception) {
                    throw ParseValueException("Cant parse $input as scientific number", ex)
                }
                return mantis * 10.0.pow(power.toDouble())
            }
            else -> throw ParseValueException("Cant parse $input as scientific number")
        }
    }


}