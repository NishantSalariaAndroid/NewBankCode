package com.example.newbankdemo.core.bases

import com.example.newbankdemo.core.Util.EspressoIdlingResource
import com.example.newbankdemo.core.models.Output
import kotlinx.coroutines.*

abstract class BaseUseCase<Type : Any, in Params> : UseCaseLifeCycle {

    lateinit var parentJob: Job

    abstract suspend fun run(params: Params): Output<Type>

    operator fun invoke(params: Params, onResult: (Output<Type>) -> Unit = {}) {

        parentJob = GlobalScope.launch(Dispatchers.Main) {

            EspressoIdlingResource.increment()

            onResult(Output.Loading())

            val remoteJob = withContext(Dispatchers.IO) {

                run(params)
            }

            onResult(remoteJob)

            EspressoIdlingResource.decrement()

        }
    }

    override fun onCleared() {
        parentJob.cancel()
    }
}

interface UseCaseLifeCycle {

    fun onCleared()
}

class None




