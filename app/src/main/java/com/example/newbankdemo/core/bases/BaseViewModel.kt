package com.example.newbankdemo.core.bases

import androidx.lifecycle.ViewModel

open class BaseViewModel(var useCaseLifeCycle : UseCaseLifeCycle) : ViewModel() {

    override fun onCleared() {

        super.onCleared()

        useCaseLifeCycle.onCleared()
    }
}