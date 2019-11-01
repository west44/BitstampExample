package pavelschreiner.eu.mvvmtest.model.engine

import dagger.Component
import pavelschreiner.eu.mvvmtest.viewmodel.MainViewModel

@Component
interface PusherWebSocketEngineComponent {

    fun inject(mainViewModel: MainViewModel)
}