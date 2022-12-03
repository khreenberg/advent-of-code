import java.io.File

private fun String.halves() = chunked(length / 2)
private fun Char.priority() = (code - if (isUpperCase()) 38 else 96).toLong()
private fun Iterable<Iterable<String>>.intersectItems() = map {
    it.map(String::toSet)
        .reduce { a, b -> a.intersect(b) }
        .single()
}

private fun File.sumIntersection(chunkingStrategy: Iterable<String>.() -> Iterable<Iterable<String>>) =
    readLines().chunkingStrategy().intersectItems().sumOf(Char::priority)

class Day03 : Kotlin2022Template(3) {
    override fun part1(file: File) = file.sumIntersection { flatMap(String::halves).chunked(2) }
    override fun part2(file: File) = file.sumIntersection { chunked(3) }
}

fun main() = Day03().run()
