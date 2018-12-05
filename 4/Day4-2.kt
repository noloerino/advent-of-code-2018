import java.io.File
import java.text.SimpleDateFormat

typealias GuardTime = Pair<Int, Int>
val GuardTime.id get() = this.first
val GuardTime.min get() = this.second

fun main() {
    val lines: MutableList<String> = File("input.txt")
        .readLines()
        .toMutableList()
    lines.sortWith(dateSorter)
    val sleepTimes = mutableMapOf<GuardTime, Int>()
    var guardId = 0
    var sleepMin = 0
    for (l in lines) {
        val minute = l.substring(15, 17).toInt()
        val msg = l.substring(19)
        when (msg) {
            "falls asleep" -> sleepMin = minute
            "wakes up" -> {
                for (min in sleepMin until minute) {
                    val key = GuardTime(guardId, min)
                    val old = sleepTimes.get(key) ?: 0
                    sleepTimes.put(key, old + 1)
                }
            }
            else -> {
                sleepMin = 0
                guardId = l.substring(26).split(' ')[0].toInt()
            }
        }
    }
    val (maxId, maxMin) = sleepTimes.maxBy { (_, v) -> v }!!.key
    println(maxMin * maxId)
}

val dateSorter = object : Comparator<String> {
    override fun compare(s1: String, s2: String): Int {
        val fmt = SimpleDateFormat("[yyyy-MM-dd HH:mm")
        return fmt.parse(s1.split(']')[0]).compareTo(fmt.parse(s2.split(']')[0]))
    }
}
