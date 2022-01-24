import java.util.*

fun main(args: Array<String>){
    tailrec fun lines(s: Scanner, l: List<String>):List<String> = if (!s.hasNext()) l else lines(s, l.plus(s.next()))
    println(lines(Scanner(System.`in`), listOf()).filter { x -> x.toInt()<=30 }.count())
}