import java.io.File

fun abs(n: Int): Int {
    if (n >= 0) {
        return n
    } else {
        return -n
    }
}

fun main() {
    val line = File("input.txt").readLines()[0]
    val m = ('a'..'z').associateWith { c ->
        react(line.filter { it != c && it != c.toUpperCase() })
    }
    println(m.minBy { (_, v) -> v }!!.value)
}

fun react(s: String): Int {
    val line = s.toMutableList()
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
    return line.size
}
