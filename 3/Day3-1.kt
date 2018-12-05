import java.io.File

typealias Point = Pair<Int, Int>
val Point.x: Int get() = this.first
val Point.y: Int get() = this.second

fun main() {
    val lines = File("input.txt").readLines()
    val claims = mutableMapOf<Point, Int>()
    for (l in lines) {
        val tail = l.split('@')[1]
        val (coords, grid) = tail.split(':')
        val (x, y) = coords.split(',').map { s -> s.trim().toInt() }
        val (w, h) = grid.split('x').map { s -> s.trim().toInt() }
        for (i in 0 until w) {
            for (j in 0 until h) {
                val pt = Point(x + i, y + j)
                val ct = claims.get(pt) ?: 0
                claims.put(pt, ct + 1)
            }
        }
    }
    println(claims.filter { (_, v) -> v > 1 }.size)
}

