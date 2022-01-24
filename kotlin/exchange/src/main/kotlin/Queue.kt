import java.math.BigDecimal
import java.util.concurrent.ConcurrentSkipListMap
import java.util.concurrent.LinkedBlockingQueue

fun main(args: Array<String>){
    print("a")
}

enum class BuySell{Buy, Sell}
typealias Price = BigDecimal
typealias OrderId = Long
typealias Quantity = Int
data class Order(val id: OrderId, val quantity: Quantity, val buysell: BuySell, val price: Price)

class OrderQueue: LinkedBlockingQueue<Order>()

class MatchingEngine(){
    val sellMap = ConcurrentSkipListMap<Price, OrderQueue>{o1, o2 -> o1.compareTo(o2)}
    val buyMap = ConcurrentSkipListMap<Price, OrderQueue>{o1, o2 -> o1.compareTo(o2)*-1} // ソート逆順

    fun match(){

    }
}


