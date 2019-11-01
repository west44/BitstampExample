package pavelschreiner.eu.mvvmtest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import pavelschreiner.eu.mvvmtest.R
import pavelschreiner.eu.mvvmtest.databinding.DashboardFragmentBinding
import pavelschreiner.eu.mvvmtest.viewmodel.MainViewModel



class DashboardFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = null
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)


            val binding: DashboardFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.dashboard_fragment,
                container, false)
            // get the root view
            binding.vm = viewModel
            binding.lifecycleOwner = this
            view = binding.root
        }

        return view
    }
}
