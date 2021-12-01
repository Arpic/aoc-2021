import java.util.stream.Collectors
import kotlin.streams.toList

fun main() {
    fun part1(input: List<String>): Int {
        return input.zipWithNext{a,b -> if (a.toInt() < b.toInt()) 1 else 0 }.stream().collect(Collectors.summingInt { d -> d.toInt() })
    }

    fun part2(input: List<String>): Int {
        var previousSum = Int.MAX_VALUE
        var increased = 0
        val inputInt = input.stream().mapToInt(String::toInt).toList()
        for (i in 0 until inputInt.size - 2) {
            val sum = inputInt[i] + inputInt[i+1] + inputInt[i+2]
            if (sum > previousSum) {
                increased++
            }
            previousSum = sum
        }
        return increased
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
