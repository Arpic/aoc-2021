import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import java.util.stream.Collectors
import kotlin.math.abs
import kotlin.streams.toList

fun main() {
    fun <K, V> Map<K, V>.toBiMap() = HashBiMap.create(this)
    fun String.sorted() = this.toSortedSet().joinToString("")
    

    data class Screen(val patterns: List<String>, val output: List<String>)

    fun readScreens(input: List<String>) =
        input.stream().map { it.split("|") }.map { Screen(it[0].trim().split(" "), it[1].trim().split(" ")) }.toList()

    fun getMapping(
        screen: Screen
    ): BiMap<String, String> {
        val patternBySize = screen.patterns.stream().collect(Collectors.groupingBy({ it.length }, Collectors.toList()))
        val mapping = mutableMapOf<String, String>()
        //Unique patterns
        mapping["1"] = patternBySize[2]!![0].sorted()
        mapping["7"] = patternBySize[3]!![0].sorted()
        mapping["4"] = patternBySize[4]!![0].sorted()
        mapping["8"] = patternBySize[7]!![0].sorted()

        val cf = mapping["1"]!!.toSortedSet()
        val bd = mapping["4"]!!.toSortedSet() - cf

        for (pattern in patternBySize[6]!!) {
            val patternChars = pattern.toSortedSet()
            if (!patternChars.containsAll(bd)) {
                mapping["0"] = pattern.sorted()
            } else if (patternChars.containsAll(cf)) {
                mapping["9"] = pattern.sorted()
            } else {
                mapping["6"] = pattern.sorted()
            }
        }
        val chars0 = mapping["0"]!!.toSortedSet()
        val chars9 = mapping["9"]!!.toSortedSet()
        val e = chars0 - chars9
        for (pattern in patternBySize[5]!!) {
            val patternChars = pattern.toSortedSet()
            if (patternChars.containsAll(cf)) {
                mapping["3"] = pattern.sorted()
            } else if (patternChars.containsAll(e)) {
                mapping["2"] = pattern.sorted()
            } else {
                mapping["5"] = pattern.sorted()
            }
        }
        return mapping.toBiMap()
    }

    fun readScreen(screen: Screen): Int {
        val mapping = getMapping(screen).inverse()
        val result = screen.output.stream().map{mapping[it.sorted()]}.collect(Collectors.joining())
        return result.toString().toInt()
    }

    fun countUniqueDigits(screen: Screen): Int {
        return screen.output.stream().map { it.length }
            .filter { it == 2 || it == 3 || it == 4 || it == 7 }
            .count().toInt()
    }

    fun part1(input: List<String>): Int {
        val screens = readScreens(input)
        return screens.stream().collect(Collectors.summingInt { countUniqueDigits(it) })
    }

    fun part2(input: List<String>): Int {
        val screens = readScreens(input)
        return screens.sumOf { readScreen(it) }
    }

    val testInput = readInput("Day08_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 26)
    val part2 = part2(testInput)
    println(part2)
    check(part2 == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
