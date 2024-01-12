package com.example.newbankdemo.data.wrapper

import com.example.newbankdemo.data.remote.LoginResponse
import com.example.newbankdemo.domain.model.LoginEntity


fun LoginResponse.toLoginResponse() = LoginEntity(isLoggedIn = loginSuccess, message = rMessage )


