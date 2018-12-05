import java.io.File
import java.text.SimpleDateFormat

fun main() {
    val lines: MutableList<String> = File("input.txt")
        .readLines()
        .toMutableList()
    lines.sortWith(dateSorter)
    val sleepTimes = mutableMapOf<Int, Int>()
    var guardId = 0
    var sleepMin = 0
    // first pass to find most asleep
    for (l in lines) {
        val minute = l.substring(15, 17).toInt()
        val msg = l.substring(19)
        when (msg) {
            "falls asleep" -> sleepMin = minute
            "wakes up" -> {
                val old = sleepTimes.get(guardId) ?: 0
                sleepTimes.put(guardId, old + minute - sleepMin)
            }
            else -> {
                sleepMin = 0
                guardId = l.substring(26).split(' ')[0].toInt()
            }
        }
    }
    val maxId = sleepTimes.maxBy { (_, v) -> v }!!.key
    println("The slacker is ${maxId}")
    // second pass to find best
    val sleepMins = mutableMapOf<Int, Int>()
    guardId = 0
    for (l in lines) {
        val minute = l.substring(15, 17).toInt()
        val msg = l.substring(19)
        when (msg) {
            "falls asleep" -> sleepMin = minute
            "wakes up" -> {
                if (guardId == maxId) {
                    for (min in sleepMin until minute) {
                        val old = sleepMins.get(min) ?: 0
                        sleepMins.put(min, old + 1)
                    }
                }
            }
            else -> {
                sleepMin = 0
                guardId = l.substring(26).split(' ')[0].toInt()
            }
        }
    }
    val maxMin = sleepMins.maxBy { (_, v) -> v }!!.key
    println("Max minute is ${maxMin}")
    println(maxMin * maxId)
}

val dateSorter = object : Comparator<String> {
    override fun compare(s1: String, s2: String): Int {
        val fmt = SimpleDateFormat("[yyyy-MM-dd HH:mm")
        return fmt.parse(s1.split(']')[0]).compareTo(fmt.parse(s2.split(']')[0]))
    }
}
