package com.example.newbankdemo.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newbankdemo.R
import com.example.newbankdemo.core.Util.DataValidator
import com.example.newbankdemo.core.bases.BaseViewModel
import com.example.newbankdemo.core.models.Output
import com.example.newbankdemo.domain.model.LoginEntity
import com.example.newbankdemo.domain.usecase.LoginUseCase


class LoginViewModel (var loginUseCase: LoginUseCase) : BaseViewModel(loginUseCase) {

    private val _loginForm = MutableLiveData<Output<LoginFormState>>()

    val loginFormState: LiveData<Output<LoginFormState>> = _loginForm

    private val _login = MutableLiveData<Output<LoginEntity>>()

    val login: LiveData<Output<LoginEntity>> = _login

    fun login(username: String, password: String) {

        loginUseCase(LoginUseCase.Params(username, password)) {

            _login.value = it
        }
    }


    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = Output.Success(
                LoginFormState(usernameError = R.string.invalid_username)
            )
        } else if (!isPasswordValid(password)) {
            _loginForm.value = Output.Success(
                LoginFormState(passwordError = R.string.invalid_password)
            )
        } else {
            _loginForm.value = Output.Success(LoginFormState(isDataValid = true))
        }
    }


    private fun isUserNameValid(username: String): Boolean = DataValidator.isUsernameValid(username)

    private fun isPasswordValid(password: String): Boolean = DataValidator.iPasswordValid(password)

    override fun onCleared() {

        super.onCleared()

        loginUseCase.onCleared()
    }
}