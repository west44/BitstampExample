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
import pavelschreiner.eu.mvvmtest.databinding.TransactionsFragmentBinding
import pavelschreiner.eu.mvvmtest.model.engine.ListItemModel
import pavelschreiner.eu.mvvmtest.model.engine.LiveTradesDataAdapter
import pavelschreiner.eu.mvvmtest.viewmodel.MainViewModel

class TransactionFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var orderBookRecyclerView: RecyclerView
    private lateinit var adapter: LiveTradesDataAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        }

        val binding: TransactionsFragmentBinding = DataBindingUtil.inflate(
            inflater, pavelschreiner.eu.mvvmtest.R.layout.transactions_fragment,
            container, false
        )
        adapter = LiveTradesDataAdapter(ArrayList())
        binding.lifecycleOwner = this
        binding.vm = viewModel
        orderBookRecyclerView = binding.liveTradesList
        orderBookRecyclerView.adapter = adapter
        orderBookRecyclerView.layoutManager = LinearLayoutManager(context)
        getLiveTradesList()

        return binding.root
    }

    private fun getLiveTradesList() {
        viewModel.getLiveTradesList().observe(this, Observer<List<ListItemModel>> { liveTradesList ->
            adapter.setLiveTradesList(liveTradesList as ArrayList<ListItemModel>)
        })
    }
}
