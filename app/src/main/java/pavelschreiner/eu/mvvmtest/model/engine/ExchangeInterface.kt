package pavelschreiner.eu.mvvmtest.model.engine

interface ExchangeInterface {

    fun parseOrderBookData(type: EngineDataType, data: String)
    fun parseLiveTradesData(type: EngineDataType, data: String)

    companion object {
        val CHANNEL_ORDER_BOOK_XRP_USD = "order_book_xrpusd"
        val CHANNEL_ORDER_BOOK_BTC_USD = "order_book" // btcusd
        val CHANNEL_LIVE_TRADES_BTC_USD = "live_trades" // btcusd
        val CHANNEL_LIVE_TRADES_XRP_USD = "live_trades_xrpusd"
    }
}
