
import java.io.File
import java.util.PriorityQueue

fun main() {
    val lines = File("input.txt").readLines()
    val re = "\\b[A-Z]\\b".toRegex()
    // maps label to children
    val dependents = mutableMapOf<Char, MutableList<Char>>()
    val requirementsCt = mutableMapOf<Char, Int>()
    for (ln in lines) {
        val results = re.findAll(ln)
        val from = results.elementAt(0).value[0]
        val to = results.elementAt(1).value[0]
        val dep = dependents.getOrPut(from, { mutableListOf() })
        dep.add(to)
        val req = requirementsCt.getOrElse(to) { 0 }
        requirementsCt[to] = req + 1
    }
    requirementsCt.putAll(
            (dependents.keys - requirementsCt.keys)
            .associateWith { 0 }
        )
    fun getNextJob(): Char? {
        val candidates = requirementsCt
            .filter { (_, v) -> v == 0 }
            .keys
            .toSortedSet()
        if (candidates.size == 0) {
            return null
        }
        val c = candidates.first()!!
        requirementsCt.remove(c)
        return c
    }
    var time = 0
    val workers = 5
    val jobs = MutableList(workers) { 0 }
    val ns = MutableList(workers) { '.' } // running label 
    // assign job to first available guy; if not then block
    while (requirementsCt.isNotEmpty() || jobs.any { it >= 0 }) {
    //for (j in 0..20) {
        time++
        for (i in jobs.indices) {
            if (jobs[i] <= 0) {
                // update priorities and prio queue with completed job
                val c = ns[i]
                if (c != '.' && dependents[c] != null) {
                    for (d in dependents[c]!!) {
                        requirementsCt[d] = requirementsCt[d]!! - 1
                    }
                }
            }
        }
        // assignment loop must be separate from job updating
        for (i in jobs.indices) {
            if (jobs[i] <= 0) {
                var n = getNextJob()
                if (n != null) {
                    jobs[i] = 61 + n.toInt() - 'A'.toInt()
                    ns[i] = n
                } else {
                    ns[i] = '.'
                }
            }
            jobs[i]--
        }
        println("$ns $jobs")
    }
    println(time - 1)
}

