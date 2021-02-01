package pakiet.arkadiuszzimny.expenotes_v1.util

import kotlinx.coroutines.CoroutineDispatcher


interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher

}