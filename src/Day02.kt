fun main() {
    fun parseCommand(command: String): Command {
        val commandArray = command.split(" ")
        return Command(Direction.valueOf(commandArray[0].uppercase()), commandArray[1].toInt())
    }

    fun moveSubmarine(input: List<String>): Int {
        val submarine = Submarine()
        input.stream()
            .map(::parseCommand).forEach(submarine::executeCommand)
        return submarine.result
    }

    val testInput = readInput("Day02_test")
    check(moveSubmarine(testInput) == 900)

    val input = readInput("Day02")
    println(moveSubmarine(input))
}

data class Command(val direction: Direction, val value: Int)

enum class Direction {
    FORWARD,
    DOWN,
    UP
}

class Submarine {
    private var depth = 0
    private var position = 0
    private var aim = 0
    val result: Int
        get() = depth * position

    fun executeCommand(command: Command) {
        when (command.direction) {
            Direction.FORWARD -> {
                position += command.value
                depth += aim * command.value
            }
            Direction.DOWN  -> {
                aim += command.value
            }
            Direction.UP  -> {
                aim -= command.value
            }
        }
    }

}