package pavelschreiner.eu.mvvmtest.model.engine

import android.util.Log
import com.pusher.client.Pusher
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import pavelschreiner.eu.mvvmtest.model.engine.EngineDataType.*
import javax.inject.Inject

class PusherWebSocketEngine @Inject constructor(var bitstamp: BitstampExchange) {
    companion object {
        const val PUSHER_KEY = "de504dc5763aeef9ff52"
    }

    private val pusher: Pusher = Pusher(PUSHER_KEY)
    private var subscribedChannels: ArrayList<String> = ArrayList()

    init {
        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
            }

            override fun onError(message: String, code: String?, e: Exception?) {
                Log.d("PUSHER", "Error! " + message + ", code=" + code + ", ex=" + e.toString())
            }
        }, ConnectionState.ALL)
        pusher.unsubscribe(ExchangeInterface.CHANNEL_ORDER_BOOK_BTC_USD)
        pusher.unsubscribe(ExchangeInterface.CHANNEL_LIVE_TRADES_BTC_USD)
    }

    fun subscribeChannel(channelName: String, eventsName: Array<String>) {
        subscribedChannels.add(channelName)
        val channel = pusher.subscribe(channelName)

        for (i in eventsName.indices) {
            channel.bind(eventsName[i]) { event ->
                val data = event.data
                val channel = event.channelName
                if (channel.contains(ORDER_BOOK.value)) {
                    bitstamp.parseOrderBookData(ORDER_BOOK, data)
                } else if (channel.contains(LIVE_TRADES.value)) {
                    bitstamp.parseLiveTradesData(LIVE_TRADES, data)
                }
            }
        }
    }

    fun destroy() {
        subscribedChannels.forEach { pusher.unsubscribe(it) }
        pusher.disconnect()
    }

    fun tryReconnect() {
        Log.d("PUSHER", "I'am trying reconnect...")
        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
            }

            override fun onError(message: String, code: String?, e: Exception?) {
                Log.d("PUSHER", "Error! " + message + ", code=" + code + ", ex=" + e.toString())
            }
        }, ConnectionState.ALL)
    }

    fun getConnectionState(): ConnectionState {
        return pusher.connection.state
    }
}