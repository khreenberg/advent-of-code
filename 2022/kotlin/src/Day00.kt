import java.io.File

abstract class  Kotlin2022Template<T>(day: Int) {
    private val data = File("2022/data/day${day.toString().padStart(2, '0')}")
    private val input = File(data, "input.txt")
    private val test = File(data, "test.txt")
    private val result = File(data, "test.result")

    protected abstract fun part1(file: File): T
    protected abstract fun part2(file: File): T

    fun run() {
        test()
        println("Tests passed.")
        part1Result()
        try {
            part2Result()
        } catch (e: NotImplementedError) {
            println("Round2: N/A")
        }
    }

    private fun test() {
        val expected = result.readLines()
        val answer1 = part1(test).toString()
        assert(expected[0], answer1)
        expected.getOrNull(1)?.let { assert(it, part2(test).toString()) }
    }

    private fun part1Result() = println("Part1 result: ${part1(input)}")
    private fun part2Result() = println("Part2 result: ${part2(input)}")


    private fun <T> assert(expected: T, actual: T) =
        if (expected != actual) throw AssertionError("Expected '$expected' but got '$actual'") else Unit
}