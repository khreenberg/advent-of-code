import java.io.File

private fun String.toRange() = split("-").map(String::toInt).let { (a, b) -> (a..b) }
private fun String.toRangePair() = split(",").let { (a, b) -> a.toRange() to b.toRange() }
private fun IntRange.fullyContains(other: IntRange) = first <= other.first && last >= other.last
private fun IntRange.partiallyContains(other: IntRange) = toSet().intersect(other.toSet()).isNotEmpty()

class Day04 : Kotlin2022Template(4) {
    private fun File.countOverlap(overlapPredicate: (Pair<IntRange, IntRange>) -> Boolean) = readLines()
        .map(String::toRangePair)
        .count(overlapPredicate)
        .toLong()

    override fun part1(file: File) = file.countOverlap { (a, b) -> a.fullyContains(b) || b.fullyContains(a) }
    override fun part2(file: File) = file.countOverlap { (a, b) -> a.partiallyContains(b) }
}

fun main() = Day04().run()
