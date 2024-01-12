package com.example.newbankdemo.data.repository

import com.example.newbankdemo.core.bases.BaseRepository
import com.example.newbankdemo.core.models.Output
import com.example.newbankdemo.data.remote.LoginResponse
import com.example.newbankdemo.data.remote.RemoteDataSource
import com.example.newbankdemo.data.wrapper.toLoginResponse
import com.example.newbankdemo.domain.model.LoginEntity
import com.example.newbankdemo.domain.repository.LoginRepository


class LoginRepositoryImpl : BaseRepository(), LoginRepository {

    override  fun login(username: String, password: String): Output<LoginEntity> {

        return safeApiCall(remoteDataSource.makeRemoteCall(RemoteDataSource::class.java)
            .login(username, password), transform = { it.toLoginResponse() }, default = LoginResponse(), error = "")

    }


}

