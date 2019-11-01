package pavelschreiner.eu.mvvmtest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pavelschreiner.eu.mvvmtest.databinding.OrderBookFragmentBinding
import pavelschreiner.eu.mvvmtest.model.engine.OrderBookDataAdapter
import pavelschreiner.eu.mvvmtest.model.engine.ListItemModel
import pavelschreiner.eu.mvvmtest.viewmodel.MainViewModel

class OrderBookFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var orderBookRecyclerView: RecyclerView
    private lateinit var adapter: OrderBookDataAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        }

        val binding: OrderBookFragmentBinding = DataBindingUtil.inflate(
            inflater, pavelschreiner.eu.mvvmtest.R.layout.order_book_fragment,
            container, false
        )
        adapter = OrderBookDataAdapter(ArrayList())
        binding.lifecycleOwner = this
        binding.vm = viewModel
        orderBookRecyclerView = binding.orderList
        orderBookRecyclerView.adapter = adapter
        orderBookRecyclerView.layoutManager = LinearLayoutManager(context)
        getOrderBookList()

        return binding.root
    }

    private fun getOrderBookList() {
        viewModel.getOrderBookList().observe(this, Observer<List<ListItemModel>> { orderBookList ->
            adapter.setOrderBookList(orderBookList as ArrayList<ListItemModel>)
        })
    }
}
