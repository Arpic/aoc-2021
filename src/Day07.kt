import java.util.stream.Collectors
import kotlin.math.abs
import kotlin.streams.toList

fun main() {
    fun readInitialCycle(input: String) =
        input.split(",").stream().mapToInt(String::toInt).toList()
    fun median(l: List<Int>) = l.sorted().let { (it[it.size / 2] + it[(it.size - 1) / 2]) / 2 }

    fun part1(input: List<String>): Int {
        val initialPositions = readInitialCycle(input[0])
        val targetPosition = median(initialPositions)
        return initialPositions.stream().collect(Collectors.summingInt { a -> abs(targetPosition - a) })
    }

    fun part2(input: List<String>): Int {
        val initialPositions = readInitialCycle(input[0])
        val minValue = initialPositions.stream().mapToInt{it}.min().asInt
        val maxValue = initialPositions.stream().mapToInt{it}.max().asInt
        var minFuel = Int.MAX_VALUE
        for (i in minValue..maxValue) {
            val fuel = initialPositions.stream().collect(Collectors.summingInt{a -> val n = abs(i -a)
                n*(n+1)/2
            })
            if (fuel < minFuel) {
                minFuel =fuel
            }
        }
        return minFuel
    }

    val testInput = readInput("Day07_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 37)
    val part2 = part2(testInput)
    println(part2)
    check(part2 == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
