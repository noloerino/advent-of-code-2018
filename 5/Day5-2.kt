import java.io.File

val stacks = ('a'..'z').associateWith { mutableListOf<Char>() }
fun main() {
    val line = File("input.txt").readLines()[0]
    for (c in line) {
        for ((k, v) in stacks) {
            if (c.toLowerCase() != k) {
                react(c, v)
            }
        }
    }
    println(stacks.minBy { (_, s) -> s.size }!!.value.size)
}

fun react(c: Char, s: MutableList<Char>){
    val d = s.lastOrNull()
    if (d != null && c != d && c.toLowerCase() == d.toLowerCase()) {
        s.removeAt(s.lastIndex)
    } else {
        s.add(c)
    }
}
