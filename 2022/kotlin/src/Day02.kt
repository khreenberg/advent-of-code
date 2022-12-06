import java.io.File

class Day02 : Kotlin2022Template<Long>(2) {
    sealed class Shape(val value: Int) : Comparable<Shape> {
        object Rock : Shape(1)
        object Paper : Shape(2)
        object Scissors : Shape(3)

        override fun compareTo(other: Shape) = when (this) {
            +other -> 1
            -other -> -1
            else -> 0
        }

        operator fun unaryPlus() = order[(value) % 3]
        operator fun unaryMinus(): Shape = order[(value + 1) % 3]

        companion object {
            private val order by lazy { arrayOf(Rock, Paper, Scissors) }
        }
    }

    data class Round(val attack: Shape, val counter: Shape) {
        fun score() = when {
            attack > counter -> 0
            attack < counter -> 6
            else -> 3
        } + counter.value
    }

    private fun shapeFromLetter(letter: String) = when (letter) {
        "A", "X" -> Shape.Rock
        "B", "Y" -> Shape.Paper
        "C", "Z" -> Shape.Scissors
        else -> throw IllegalArgumentException("Letter '$letter' is not a valid shape!")
    }

    private fun counterFromLetter(attack: Shape, letter: String) = when (letter) {
        "X" -> -attack
        "Y" -> attack
        "Z" -> +attack
        else -> throw IllegalArgumentException("Letter '$letter' is not a valid counter!")
    }

    private fun strategy1(line: String) = line.split(" ").map(::shapeFromLetter).let { (a, b) -> Round(a, b) }
    private fun strategy2(line: String) = line.split(" ")
        .let { (a, b) -> shapeFromLetter(a) to b }
        .let { (a, b) -> Round(a, counterFromLetter(a, b)) }

    private fun File.scoreBy(strategy: (String) -> Round) = readLines().map(strategy).sumOf { it.score() }.toLong()

    override fun part1(file: File) = file.scoreBy(::strategy1)
    override fun part2(file: File) = file.scoreBy(::strategy2)
}

fun main() = Day02().run()