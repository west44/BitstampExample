package pavelschreiner.eu.mvvmtest.model.engine

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import javax.inject.Inject

class BitstampExchange @Inject constructor() : ExchangeInterface {

    private val gsonBuilder = GsonBuilder()
    private val gson: Gson = gsonBuilder.create()
    private var allLiveTradesList: ArrayList<ListItemModel> = ArrayList()

    var sellPrice: MutableLiveData<Double> = MutableLiveData()
    var buyPrice: MutableLiveData<Double> = MutableLiveData()
    var orderBookList: MutableLiveData<ArrayList<ListItemModel>> = MutableLiveData()
    var liveTradesList: MutableLiveData<ArrayList<ListItemModel>> = MutableLiveData()
    var orderBookSwitchChecked: Boolean = false


    override fun parseOrderBookData(type: EngineDataType, data: String) {

        val parser = JSONParser()
        try {
            val httpAnswerJson = parser.parse(data) as JSONObject
            val orderBookObject: OrderBook = gson.fromJson(httpAnswerJson.toString(), OrderBook::class.java)
            val finalAsk = java.lang.Double.parseDouble(orderBookObject.asks.get(0).get(0))
            val finalBid = java.lang.Double.parseDouble(orderBookObject.bids.get(0).get(0))

            var bidListObject: ArrayList<ListItemModel> = ArrayList()
            orderBookObject.bids.forEach {
                val bidObject = ListItemModel(it.get(0), it.get(1))
                bidListObject.add(bidObject)
            }

            var askListObject: ArrayList<ListItemModel> = ArrayList()
            orderBookObject.asks.forEach {
                val askObject = ListItemModel(it.get(0), it.get(1))
                askListObject.add(askObject)
            }

            if (orderBookSwitchChecked) {
                orderBookList.postValue(askListObject)
            } else {
                orderBookList.postValue(bidListObject)
            }
            if (finalAsk != buyPrice.value) {
                buyPrice.postValue(finalAsk)
            }
            if (finalBid != sellPrice.value) {
                sellPrice.postValue(finalBid)
            }
            //                Log.d("PRICE_UPDATE", "new buyPrice: $finalAsk, new sellPrice: $finalBid")
        } catch (ex: Exception) {
            Log.e("PARSE_EXCEPTION", "" + ex)
        }
    }

    override fun parseLiveTradesData(type: EngineDataType, data: String) {

        val parser = JSONParser()
        try {
            val httpAnswerJson = parser.parse(data) as JSONObject
            val liveTradesObject: LiveTrades = gson.fromJson(httpAnswerJson.toString(), LiveTrades::class.java)

            val tradeObject = ListItemModel(liveTradesObject.price_str, liveTradesObject.amount_str)
            allLiveTradesList.add(tradeObject)
            liveTradesList.postValue(allLiveTradesList)

        } catch (ex: Exception) {
            Log.e("PARSE_EXCEPTION", "" + ex)
        }
    }
}
