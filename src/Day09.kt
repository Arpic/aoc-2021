import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import java.util.stream.Collectors
import kotlin.math.abs
import kotlin.streams.toList

fun main() {

    fun isLowPoint(
        input: List<String>,
        i: Int,
        j: Int,
        height: Int,
        width: Int
    ): Pair<Int, Boolean> {
        val point = input[i][j].digitToInt()
        val up = if (i > 0) point < input[i - 1][j].digitToInt() else true
        val bot = if (i < height - 1) point < input[i + 1][j].digitToInt() else true
        val left = if (j > 0) point < input[i][j - 1].digitToInt() else true
        val right = if (j < width - 1) point < input[i][j + 1].digitToInt() else true
        val lowPoint = left && right && up && bot
        return Pair(point, lowPoint)
    }

    fun part1(input: List<String>): Int {
        val width = input[0].length
        val height = input.size
        var totalRisk = 0
        for (i in 0 until height) {
            for (j in 0 until width) {
                val (point, lowPoint) = isLowPoint(input, i, j, height, width)
                if (lowPoint) {
                    totalRisk += point + 1
                }
            }
        }
        return totalRisk
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day09_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 15)
//    val part2 = part2(testInput)
//    println(part2)
//    check(part2 == 61229)

    val input = readInput("Day09")
    println(part1(input))
//    println(part2(input))
}
