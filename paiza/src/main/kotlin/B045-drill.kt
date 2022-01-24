import java.util.*

fun main(args: Array<String>) {
    tailrec fun findNext(l: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
        val MAX = 99
        fun pair(x: Int) = Pair(x, Random().nextInt(MAX - x))
        val p = pair(Random().nextInt(MAX))
        return if(!l.contains(p)) l.plus(p) else findNext(l)
    }
    tailrec fun makeSet(count: Int, l: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> =
            if (l.size == count) l else makeSet(count, findNext(l))

    val s = Scanner(System.`in`)
    val add = s.nextInt()
    val sub = s.nextInt()
    makeSet(add, setOf()).forEach({p -> println("${p.first} + ${p.second} =")})
    makeSet(sub, setOf()).forEach({p -> println("${p.first} - ${p.second} =")})
}