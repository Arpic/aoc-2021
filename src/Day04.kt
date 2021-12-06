import kotlin.streams.toList

class Matrix {
    private val data: MutableList<List<Int>> = mutableListOf()
    private val marked: MutableList<MutableList<Boolean>> = MutableList(5) { MutableList(5) { false } }
    private val size = 5
    var hasWon = false

    fun addRow(row: List<Int>) {
        data.add(row)
    }

    fun markNumber(drawnNumber: Int): Boolean {
        for (i in 0 until size) {
            val row = data[i]
            for (j in 0 until size) {
                if (row[j] == drawnNumber) {
                    marked[i][j] = true
                    if (isRowOrColumnFullyMarked(i, j)) {
                        hasWon = true
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun isRowOrColumnFullyMarked(row: Int, column: Int): Boolean {
        return marked[row].stream().allMatch { it } || marked.stream().map { it[column] }.allMatch { it }
    }

    fun unmarkedNumbers(): List<Int> {
        val unmarkedNumbers = mutableListOf<Int>()
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (!marked[i][j]) {
                    unmarkedNumbers.add(data[i][j])
                }
            }
        }
        return unmarkedNumbers
    }
}

fun main() {
    fun readLine(input: String, separator: Regex) =
        input.split(separator).stream().mapToInt(String::toInt).toList()

    fun readInputMatrices(mutableInput: MutableList<String>): MutableList<Matrix> {
        var currentMatrix: Matrix? = null
        val matrixes: MutableList<Matrix> = mutableListOf()
        for (i in mutableInput.indices) {
            if (mutableInput[i].isNotBlank()) {
                currentMatrix?.addRow(readLine(mutableInput[i].trim(), "\\s+".toRegex()))
                currentMatrix?.let { matrixes.add(it) }
            } else {
                currentMatrix = Matrix()
            }
        }
        return matrixes
    }

    fun part1(input: List<String>): Int {
        val mutableInput = input.toMutableList()
        val drawnNumbers = readLine(mutableInput.removeFirst(), ",".toRegex())
        val matrices: MutableList<Matrix> = readInputMatrices(mutableInput)

        for (i in drawnNumbers) {
            matrices.forEach {
                if (it.markNumber(i)) {
                    return i * it.unmarkedNumbers().stream().reduce { a, b -> a + b }.get()
                }
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val mutableInput = input.toMutableList()
        val drawnNumbers = readLine(mutableInput.removeFirst(), ",".toRegex())
        val matrices: MutableList<Matrix> = readInputMatrices(mutableInput)
        val winningMatrices: MutableList<Pair<Int, Matrix>> = mutableListOf()
        for (i in drawnNumbers) {
            matrices.forEach {
                if (!it.hasWon && it.markNumber(i)) {
                    winningMatrices.add(Pair(i, it))
                }
            }
        }
        val winningMatrix = winningMatrices.last()
        return winningMatrix.first * winningMatrix.second.unmarkedNumbers().stream().reduce { a, b -> a + b }.get()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val part1 = part1(testInput)
    val part2 = part2(testInput)
    check(part1 == 4512)
    check(part2 == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
