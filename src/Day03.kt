import java.util.stream.Collectors
import kotlin.streams.toList

fun main() {
    fun part1(input: List<String>) : Int {
        val inputSize = input.size
        val valueLength = input[0].length
        val gammaRateList : MutableList<Int> = MutableList(valueLength) { 0 }
        val bitList = input.stream().flatMap { i -> i.asIterable().map(Char::digitToInt).stream() }.toList()
        bitList.withIndex().forEach { enum -> gammaRateList[enum.index.rem(valueLength)] += enum.value }
        println(gammaRateList)
        val gammaRate = gammaRateList.stream().map { bit -> if (bit > (inputSize / 2)) "1" else "0" }.collect(Collectors.joining()).toInt(2)
        val epsilonRate = gammaRateList.stream().map { bit -> if (bit > (inputSize / 2)) "0" else "1" }.collect(Collectors.joining()).toInt(2)
        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>) : Int {
        return input.size
    }

    val testInput = readInput("Day03_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 198)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}