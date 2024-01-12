package com.example.newbankdemo.domain.repository

import com.example.newbankdemo.core.models.Output
import com.example.newbankdemo.domain.model.LoginEntity


interface LoginRepository {

     fun login(username: String, password: String) : Output<LoginEntity>
}