import kotlin.math.abs
import kotlin.math.min

fun main() {

    data class Coordinate(val x: Int, val y: Int)

    class Line(start: Coordinate, end: Coordinate) {

        val vents: List<Coordinate>

        init {
            when {
                start.x == end.x -> List(abs(start.y - end.y) + 1) { i -> Coordinate(start.x, min(start.y, end.y) + i) }
                start.y == end.y -> List(abs(start.x - end.x) + 1) { i -> Coordinate(min(start.x, end.x) + i, start.y) }
                else -> emptyList()
            }.also { vents = it }
        }

        override fun toString(): String {
            return vents.toString()
        }
    }

    fun part1(input: List<String>): Int {
        val lines = mutableListOf<Line>()
        val singleHit = mutableSetOf<Coordinate>()
        val doubleHit = mutableSetOf<Coordinate>()
        input.forEach { coordinateString ->
            val coordinates = "(\\d+,\\d+) -> (\\d+,\\d+)".toRegex()
                .find(coordinateString)?.destructured?.toList()
                ?.map { it.split(",") }
                ?.map { Coordinate(it[0].toInt(), it[1].toInt()) }
                ?.toList()
            assert(coordinates!!.size == 2)
            lines.add(Line(coordinates[0], coordinates[1]))
        }

        lines
            .flatMap { it.vents }
            .filterNotTo(doubleHit) { singleHit.add(it) }
        return doubleHit.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day05_test")
    val part1 = part1(testInput)
    println(part1)
//    val part2 = part2(testInput)
//    println(part2)
    check(part1 == 5)
//    check(part2 == 230)

    val input = readInput("Day05")
    println(part1(input))
//    println(part2(input))
}