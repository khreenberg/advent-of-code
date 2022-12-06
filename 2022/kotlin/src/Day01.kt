import java.io.File

class Day01 : Kotlin2022Template<Long>(1) {
    private fun calories(file: File) =
        file.readLines().fold(mutableListOf<MutableList<Long>>(mutableListOf())) { acc, line ->
            acc.apply {
                if (line.isNotBlank()) last().add(line.toLong())
                else add(mutableListOf())
            }
        }.map(Iterable<Long>::sum)

    override fun part1(file: File) = calories(file).max()
    override fun part2(file: File) = calories(file).sortedDescending().take(3).sum()
}

fun main() = Day01().run()