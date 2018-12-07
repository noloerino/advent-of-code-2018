
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
    val seen = mutableSetOf<Char>()
    var time = 0
    val jobs = mutableListOf(0, 0, 0, 0, 0)
    val ns = mutableListOf('.', '.', '.', '.', '.') // running labels
    // assign job to first available guy; if not then block
    while (!fringe.isEmpty() || jobs.any { it > 0 }) {
        time++
        println(ns)
        for (i in jobs.indices) {
            if (jobs[i] <= 0) {
                // update priorities and prio queue with completed job
                val c = ns[i]
                if (c != '.' && dependents[c] != null) {
                    for (d in dependents[c]!!) {
                        requirements[d]!!.remove(c)
                        fringe.remove(d)
                        fringe.add(d)
                    }
                }
                var n = fringe.poll()
                while (n in seen) {
                    n = fringe.poll()
                }
                if (n != null) {
                    jobs[i] = 61 + n.toInt() - 'A'.toInt()
                    ns[i] = n
                    seen.add(n)
                } else {
                    ns[i] = '.'
                }
            }
            jobs[i]--
        }
    }
    println(time)
}

