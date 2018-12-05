import java.io.File

fun abs(n: Int): Int {
    if (n >= 0) {
        return n
    } else {
        return -n
    }
}

fun main() {
    val line: MutableList<Char> = File("input.txt").readLines()[0].toCharArray().toMutableList()
    // A reaction can occur either with (i-1) and i, or i and (i+1)
    var i = 0
    while (i < line.size) {
        // check against i-1
        if (i > 0 && abs(line[i] - line[i - 1]) == 32) {
            line.removeAt(i - 1)
            line.removeAt(i - 1)
            i--
        } else if (i + 1 < line.size && abs(line[i] - line[i + 1]) == 32) {
            line.removeAt(i)
            line.removeAt(i)
        } else {
            i++
        }
    }
    println(line)
    println(line.size)
}
