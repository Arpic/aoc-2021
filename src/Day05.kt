fun main() {

    fun part1(input: List<String>) : Int {

    }

    fun part2(input: List<String>) : Int {
        return input.size
    }

    val testInput = readInput("Day03_test")
    val part1 = part1(testInput)
    println(part1)
    val part2 = part2(testInput)
    println(part2)
    check(part1 == 198)
//    check(part2 == 230)

    val input = readInput("Day03")
    println(part1(input))
//    println(part2(input))
}