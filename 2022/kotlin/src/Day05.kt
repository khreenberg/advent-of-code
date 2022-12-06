import java.io.File

private typealias Crates = List<ArrayDeque<Char>>
private fun Crates.getTop() = map { it.first() }.joinToString("")
private fun parseCrates(input: List<String>) =
    input.flatMap { (1 until it.length step 4).mapIndexed { i, j -> i to it[j] } }
        .groupBy { it.first }
        .map { (_, stack) -> stack.map(Pair<Int, Char>::second).filterNot { it.isWhitespace() || it.isDigit() } }
        .map(::ArrayDeque)

private data class Move(val amount: Int, val src: Int, val dst: Int)
private val movePattern = """move (\d+) from (\d+) to (\d+)""".toRegex()
private fun parseMove(input: String) = movePattern.matchEntire(input)
    ?.destructured
    ?.let { (amount, src, dst) -> Move(amount.toInt(), src.toInt() - 1, dst.toInt() - 1) }

private data class Input(val crates: Crates, val procedure: List<Move>)
private fun File.parse(): Input {
    val lines = readLines()
    val (cargo, instructions) = lines.partition { !it.startsWith("move") }
    val crates = parseCrates(cargo)
    val procedure = instructions.mapNotNull(::parseMove)
    return Input(crates, procedure)
}

private typealias CrateMover = (Crates, Move) -> Unit
private val CrateMover9000: CrateMover = { crates, (amount, src, dst) ->
    repeat(amount) { crates[dst].addFirst(crates[src].removeFirst()) }
}

private val CrateMover9001: CrateMover = { crates, (amount, src, dst) ->
    crates[dst].addAll(0, crates[src].subList(0, amount))
    repeat(amount) { crates[src].removeFirst() }
}

private fun Input.moveWith(mover: CrateMover) = apply { procedure.forEach { mover(crates, it) } }
private fun File.getSolutionUsing(mover: CrateMover) = parse().moveWith(mover).crates.getTop()

class Day05 : Kotlin2022Template<String>(5) {
    override fun part1(file: File) = file.getSolutionUsing(CrateMover9000)
    override fun part2(file: File) = file.getSolutionUsing(CrateMover9001)
}

fun main() = Day05().run()