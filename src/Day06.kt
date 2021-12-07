import java.util.Optional
import java.util.stream.Collectors
import kotlin.streams.toList

fun main() {

    class LanternFish(cycle: Long) {
        var cycle = cycle
            private set

        fun next(): Optional<LanternFish> {
            if (cycle == 0L) {
                cycle = 6L
                return Optional.of(LanternFish(8L))
            } else {
                cycle--
            }
            return Optional.empty()
        }

        override fun toString(): String {
            return cycle.toString()
        }
    }


    fun simulateFishes(fishes: MutableList<LanternFish>, days: Long) {
        for (i in 0 until days) {
            val newFish = mutableListOf<LanternFish>()
            for (fish in fishes) {
                fish.next().ifPresent { newFish.add(it) }
            }
            fishes.addAll(newFish)
//            println("Day $i: ${fishes.size}")
        }
    }

    fun simulateFishes2(fishes: MutableMap<Long, Long>, days: Long) {
        for (i in 0..days) {
            val period = 7L
            val dayInTheCycle = i % period
            val fishOfTheDay = fishes[dayInTheCycle] ?: 0L
            val childrenGeneration = (dayInTheCycle + period - 2) % period + period
            val grandChildrenGeneration = childrenGeneration + period
            if (i > 8) {
                //add children
                val children = fishOfTheDay + (fishes[childrenGeneration] ?: 0L)
                fishes[dayInTheCycle] = children
                if (fishes.containsKey(grandChildrenGeneration)) {
                    //move 2nd generation
                    fishes[childrenGeneration] = (fishes[grandChildrenGeneration] ?: 0L)
                    //clear 2nd generation
                    fishes.remove(grandChildrenGeneration)
                }
                if (fishes.containsKey(dayInTheCycle + period)) {
                    fishes[dayInTheCycle + 2 * period] = fishes[dayInTheCycle] ?: 0
                }
            } else {
                if (!fishes.containsKey(dayInTheCycle + period)) {
                    fishes[dayInTheCycle + period] = fishes[dayInTheCycle] ?: 0L
                } else if (!fishes.containsKey(dayInTheCycle + 2 * period)) {
                    fishes[dayInTheCycle + 2 * period] = fishes[dayInTheCycle] ?: 0L
                }
            }
//            println("Day $i: ${fishes.values.stream().reduce { a, b -> a + b }.get()} $fishes")
        }
    }

    fun readInitialCycle(input: String) =
        input.split(",").stream().mapToLong(String::toLong).mapToObj(::LanternFish).toList().toMutableList()

    fun part1(input: List<String>): Long {
        val fishes = readInitialCycle(input[0])
        simulateFishes(fishes, 80)
        return fishes.size.toLong()
    }

    fun part2(input: List<String>): Long {
        val fishes = readInitialCycle(input[0])
        val fishMap = fishes.stream().collect(Collectors.toMap(LanternFish::cycle, { _ -> 1L }, { a, _ -> a + 1L }))
        simulateFishes2(fishMap, 255)
        return fishMap.values.stream().reduce { a, b -> a + b }.get()
    }

    val testInput = readInput("Day06_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 5934L)
    val part2 = part2(testInput)
    println(part2)
    check(part2 == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}