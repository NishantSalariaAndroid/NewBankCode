package com.example.newbankdemo.domain.usecase

import com.example.newbankdemo.core.bases.BaseUseCase
import com.example.newbankdemo.core.models.Output
import com.example.newbankdemo.domain.model.LoginEntity
import com.example.newbankdemo.domain.repository.LoginRepository


class LoginUseCase(var loginRepository: LoginRepository) : BaseUseCase<LoginEntity, LoginUseCase.Params>() {

    override suspend fun run(params: Params): Output<LoginEntity> {

        return loginRepository.login(params.username, params.password)
    }

    data class Params(val username: String, val password: String)

}