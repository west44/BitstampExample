package pavelschreiner.eu.mvvmtest.model.engine

import dagger.Component
import pavelschreiner.eu.mvvmtest.viewmodel.MainViewModel

@Component
interface BitstampEngineComponent {

    fun inject(mainViewModel: MainViewModel)
}