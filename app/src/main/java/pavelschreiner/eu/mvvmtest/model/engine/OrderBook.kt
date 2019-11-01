package pavelschreiner.eu.mvvmtest.model.engine

class OrderBook {
    /**
     * timestamp : 1572279392
     * microtimestamp : 1572279392138321
     * bids : [["0.29806","50000.00000000"],["0.29800","22005.60516628"],...
     * asks : [["0.29832","680.00000000"],["0.29833","50000.00000000"],...
     */

    var timestamp: String? = null
    var microtimestamp: String? = null
    lateinit var bids: List<List<String>>
    lateinit var asks: List<List<String>>
}
