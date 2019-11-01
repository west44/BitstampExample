package pavelschreiner.eu.mvvmtest.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pavelschreiner.eu.mvvmtest.model.engine.DaggerPusherWebSocketEngineComponent
import pavelschreiner.eu.mvvmtest.model.engine.ExchangeInterface
import pavelschreiner.eu.mvvmtest.model.engine.LiveTrades
import pavelschreiner.eu.mvvmtest.model.engine.ListItemModel
import pavelschreiner.eu.mvvmtest.model.engine.PusherWebSocketEngine
import javax.inject.Inject

class MainViewModel : ViewModel() {

    companion object {
        const val ASKS_STRING = "Asks"
        const val BIDS_STRING = "Bids"
    }

    private var buyPrice: MutableLiveData<Double> = MutableLiveData()
    private var sellPrice: MutableLiveData<Double> = MutableLiveData()
    var actualFragment: Fragment? = null
    private var orderBookSwitchChecked: ObservableBoolean = ObservableBoolean()
    var orderBookSwitchText: ObservableField<String> = ObservableField()

    @Inject lateinit var engine: PusherWebSocketEngine

    init {
        buyPrice.value = 0.0
        sellPrice.value = 0.0
        orderBookSwitchChecked.set(false)
        orderBookSwitchText.set(BIDS_STRING)
        DaggerPusherWebSocketEngineComponent.create().inject(this)
        engine.subscribeChannel(ExchangeInterface.CHANNEL_ORDER_BOOK_BTC_USD, arrayOf("data"))
        engine.subscribeChannel(ExchangeInterface.CHANNEL_LIVE_TRADES_BTC_USD, arrayOf("trade"))
    }

    fun getBuyPrice(): MutableLiveData<Double> {
        return engine.bitstamp.buyPrice
    }

    fun getSellPrice(): MutableLiveData<Double> {
        return engine.bitstamp.sellPrice
    }

    fun getOrderBookList(): MutableLiveData<ArrayList<ListItemModel>> {
        return engine.bitstamp.orderBookList
    }

    fun getLiveTradesList(): MutableLiveData<ArrayList<ListItemModel>> {
        return engine.bitstamp.liveTradesList
    }

    fun isOrderBookSwitchChecked(): Boolean {
        return orderBookSwitchChecked.get()
    }

    fun setOrderBookSwitchChecked(checked: Boolean) {
        orderBookSwitchChecked.set(checked)
        engine.bitstamp.orderBookSwitchChecked = checked
        orderBookSwitchText.set(if (checked) ASKS_STRING else BIDS_STRING)
    }
}