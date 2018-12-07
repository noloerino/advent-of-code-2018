
import java.io.File
import java.util.PriorityQueue

fun main() {
    val lines = File("input.txt").readLines()
    val re = "\\b[A-Z]\\b".toRegex()
    // maps label to children
    val dependents = mutableMapOf<Char, MutableList<Char>>()
    val requirements = mutableMapOf<Char, MutableList<Char>>()
    for (ln in lines) {
        val results = re.findAll(ln)
        val from = results.elementAt(0).value[0]
        val to = results.elementAt(1).value[0]
        val dep = dependents.getOrPut(from, { mutableListOf() })
        dep.add(to)
        val req = requirements.getOrPut(to, { mutableListOf() })
        req.add(from)
    }
    val fringe = PriorityQueue<Char>(object : Comparator<Char> {
        override fun compare(a: Char, b: Char): Int {
            if (requirements[a]?.size ?: 0 == 0) {
                if (requirements[b]?.size ?: 0 != 0) {
                    return Integer.MIN_VALUE;
                } else {
                    return a.toInt() - b.toInt()
                }
            } else {
                return Integer.MAX_VALUE;
            }
        }
    })
    fringe.addAll(dependents.keys - requirements.keys)
    val order = StringBuilder(dependents.size + 1)
    val seen = mutableSetOf<Char>()
    while (!fringe.isEmpty()) {
        val n = fringe.poll()
        if (n in seen) {
            continue
        }
        order.append(n)
        seen.add(n)
        // update priorities and prio queue
        if (dependents[n] == null) {
            continue
        }
        for (d in dependents[n]!!) {
            requirements[d]!!.remove(n)
            fringe.remove(d)
            fringe.add(d)
        }
    }
    println(order.toString())
}

