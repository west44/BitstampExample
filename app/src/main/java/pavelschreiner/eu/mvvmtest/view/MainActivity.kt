package pavelschreiner.eu.mvvmtest.view

import android.os.Bundle

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.pusher.client.connection.ConnectionState
import pavelschreiner.eu.mvvmtest.R
import pavelschreiner.eu.mvvmtest.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val fragment: Fragment
        when (item.itemId) {
            R.id.navigation_dashboard -> fragment = DashboardFragment()
            R.id.navigation_order_book -> fragment = OrderBookFragment()
            R.id.navigation_transactions -> fragment = TransactionFragment()
            else -> fragment = DashboardFragment()
        }
        goToFragment(fragment)
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        goToFragment(viewModel.actualFragment ?: DashboardFragment())
    }

    private fun goToFragment(nextFragment: Fragment) {
        viewModel.actualFragment = nextFragment
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, nextFragment).commit()
    }
}
