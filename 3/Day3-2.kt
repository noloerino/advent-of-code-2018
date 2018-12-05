import java.io.File

typealias Point = Pair<Int, Int>
val Point.x: Int get() = this.first
val Point.y: Int get() = this.second

typealias Id = Int

fun main() {
    val lines = File("input.txt").readLines()
    // unlike last time, where this mapped coord to count,
    // this now maps coord to claim id
    val claims = mutableMapOf<Point, Id>()
    var valid = mutableSetOf<Id>()
    for (l in lines) {
        val (idStr, tail) = l.split('@')
        val id = idStr.split('#')[1].trim().toInt()
        val (coords, grid) = tail.split(':')
        val (x, y) = coords.split(',').map { s -> s.trim().toInt() }
        val (w, h) = grid.split('x').map { s -> s.trim().toInt() }
        valid.add(id)
        for (i in 0 until w) {
            for (j in 0 until h) {
                val pt = Point(x + i, y + j)
                if (claims.containsKey(pt)) {
                    valid.remove(claims.get(pt)!!)
                    valid.remove(id)
                } else {
                    claims.put(pt, id)
                }
            }
        }
    }
    println(valid)
}

