package nl.jolanrensen.aoc25.day6

// TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val input = (object {}).javaClass.classLoader.getResource("input.txt")!!.readText()
    println(input.parse())
}

enum class Operation(val char: Char, val apply: (Int, Int) -> Int) {
    TIMES('*', Int::times),
    PLUS('+', Int::plus),
    ;

    companion object {
        fun of(char: Char) = entries.find { it.char == char }!!
    }
}

data class Problem(val numbers: List<Int>, val operation: Operation)

// copied here to I can use the debugger
fun String.parse(): List<Problem> {
    val lines = lines()
    val width = lines.maxOf { it.length }
    var operationLine = lines.last().padEnd(width) // important! some lines end early

    // calculate the operation and the width of their column
    val operationColWidths: List<Pair<Char, Int>> =
        operationLine.split('*', '+').zipWithNext().map { (operation, space) ->
            val operation = operationLine.take(operation.length + 1)
            operationLine = operationLine.drop(operation.length)

            operation.trim().single() to space.length + 1
        }

    val numberLines: MutableList<String> = lines.dropLast(1).toMutableList()
    return operationColWidths.map { (operation, width) ->
        Problem(
            numbers = buildList {
                for (j in numberLines.indices) {
                    this += numberLines[j].take(width).trim().toInt()
                    numberLines[j] = numberLines[j].drop(width)
                }
            },
            operation = Operation.of(operation),
        )
    }
}
