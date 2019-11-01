package pavelschreiner.eu.mvvmtest.model.engine

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pavelschreiner.eu.mvvmtest.databinding.ListItemLayoutBinding

class OrderBookDataAdapter(var items: List<ListItemModel>) :
    RecyclerView.Adapter<OrderBookDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemLayoutBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(var binding: ListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListItemModel) {
            binding.lim = item
            binding.executePendingBindings()
        }
    }

    fun setOrderBookList(askList: ArrayList<ListItemModel>) {
        this.items = askList
        notifyDataSetChanged()
    }
}