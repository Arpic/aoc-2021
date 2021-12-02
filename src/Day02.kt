fun main() {
    fun parseCommand(command: String): Command {
        val commandArray = command.split(" ")
        return Command(commandArray[0], commandArray[1].toInt())
    }

    fun moveSubmarine(input: List<String>): Int {
        val submarine = Submarine()
        input.stream()
            .map(::parseCommand).forEach(submarine::executeCommand)
        return submarine.result
    }

    val testInput = readInput("Day02_test")
    check(moveSubmarine(testInput) == 150)

    val input = readInput("Day02")
    println(moveSubmarine(input))
}

data class Command(val direction: String, val value: Int)

class Submarine {
    private var depth = 0
    private var position = 0
    val result: Int
        get() = depth * position

    fun executeCommand(command: Command) {
        when {
            command.direction.equals("forward", true) -> {
                position += command.value
            }
            command.direction.equals("down", true) -> {
                depth += command.value
            }
            command.direction.equals("up", true) -> {
                depth -= command.value
            }
        }
    }
}