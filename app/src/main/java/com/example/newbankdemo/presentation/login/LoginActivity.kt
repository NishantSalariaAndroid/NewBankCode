package com.example.newbankdemo.presentation.login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.newbankdemo.R
import com.example.newbankdemo.core.bases.BaseActivity
import com.example.newbankdemo.core.models.Output
import com.example.newbankdemo.data.util.afterTextChanged
import kotlinx.android.synthetic.main.activity_login.loading
import kotlinx.android.synthetic.main.activity_login.login
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.username
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun mainLayout(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {

            when (it) {

                is Output.Success -> {

                    login.isEnabled = it.output.isDataValid

                    if (it.output.usernameError != null) {
                        username.error = getString(it.output.usernameError)
                    }
                    if (it.output.passwordError != null) {
                        password.error = getString(it.output.passwordError)
                    }

                }

                is Output.Error -> TODO()
                is Output.Loading -> TODO()
            }
        })

        loginViewModel.login.observe(this@LoginActivity, Observer {

            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE

            when (loginResult) {

                is Output.Success -> {

                    Toast.makeText(
                        applicationContext,
                        loginResult.output.message,
                        Toast.LENGTH_SHORT).show()

                }

                is Output.Error -> {

                    Toast.makeText(applicationContext, loginResult.error, Toast.LENGTH_SHORT).show()

                }

                else -> {}
            }
        })

    }

    override fun initView() {

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }
    }

}