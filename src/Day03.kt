import java.util.stream.Collectors
import kotlin.streams.toList

fun main() {
    fun getMostCommonBits(
        input: List<String>
    ): List<String> {
        val valueLength = input[0].length
        val inputSize = input.size
        val gammaRateList: MutableList<Int> = MutableList(valueLength) { 0 }
        val bitList = input.stream().flatMap { i -> i.asIterable().map(Char::digitToInt).stream() }.toList()
        bitList.withIndex().forEach { enum -> gammaRateList[enum.index.rem(valueLength)] += enum.value }
        return gammaRateList.stream().map { bit -> if (bit >= (inputSize.toDouble() / 2)) "1" else "0" }.toList()
    }

    fun extractRating(input: List<String>, filter: (a: Int, b: Int) -> Boolean) : Int {
        var filteredInput: List<String> = input
        for (i in input[0].indices) {
            val mostCommonBits = getMostCommonBits(filteredInput)
            filteredInput =
                filteredInput.stream().filter{value -> filter(value[i].digitToInt(), mostCommonBits[i].toInt())}.toList()
            if (filteredInput.size == 1)
                return filteredInput[0].toInt(2)
        }
        return filteredInput[0].toInt(2)
    }

    fun part1(input: List<String>) : Int {
        val gammaRateList = getMostCommonBits(input)
        val gammaRate = gammaRateList.stream().collect(Collectors.joining()).toInt(2)
        val epsilonRate = gammaRateList.stream().map { bit -> if (bit == "1") "0" else "1" }.collect(Collectors.joining()).toInt(2)
        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>) : Int {
        val oxygenRating = extractRating(input) {value, mostCommonBit -> value == mostCommonBit}
        val scrubbingRating = extractRating(input) {value, mostCommonBit -> value == (1 - mostCommonBit)}
        return oxygenRating * scrubbingRating
    }

    val testInput = readInput("Day03_test")
    val part1 = part1(testInput)
    println(part1)
    val part2 = part2(testInput)
    println(part2)
    check(part1 == 198)
    check(part2 == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}