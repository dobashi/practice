stdin

### Kotlin

* readLine

* Scanner

```kotlin
fun main(args: Array<String>){
    val scn = java.util.Scanner(System.in)

    val s: String = scn.nextLine()  //入力を行で取得
    val i: Int = Integer.parseInt(s)//Int型に変換

    val j: Int = scn.nextInt()

    println(i + j)
}

```


### Scala

```scala
object Main extends App{
    println(io.Source.stdin.getLines().filter(_.toInt>=30).size)
}
```