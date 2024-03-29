package com.example.newbankdemo.core.di

import com.example.newbankdemo.core.Configuration
import com.example.newbankdemo.core.source.network.RemoteSourceManager
import com.example.newbankdemo.data.repository.LoginRepositoryImpl
import com.example.newbankdemo.domain.repository.LoginRepository
import com.example.newbankdemo.domain.usecase.LoginUseCase
import com.example.newbankdemo.presentation.login.LoginViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val repoModule = module {

    single<LoginRepository> { LoginRepositoryImpl() }

}


val viewModelModule = module {

    //        viewModel { NewsViewModel(get()) }

    viewModel { LoginViewModel(get()) }
   // viewModel { LoginViewModel(get()) }

}

val useCaseModule: Module = module {
    factory { LoginUseCase(loginRepository = get()) }
}


val networkModule = module {

    fun provideRetrofit(client: OkHttpClient): Retrofit {

        return Retrofit.Builder().client(client)
            .baseUrl(Configuration.REMOTE_ENDPOINT)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    fun provideClient(interceptor: Interceptor , httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

        return OkHttpClient().newBuilder().addInterceptor(interceptor).addInterceptor(httpLoggingInterceptor)
            .build()
    }


    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {

        val httpLoggingInterceptor = HttpLoggingInterceptor()

        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return httpLoggingInterceptor
    }


    fun provideInterceptor(): Interceptor {

        return Interceptor.invoke {

            val url =
                it.request().url.newBuilder().build()
            val request = it.request()
                .newBuilder()
                .url(url)
                .build()
            it.proceed(request)

        }
    }

    single { provideRetrofit(get()) }
    single { provideClient(get(), get()) }
    single { provideInterceptor() }
    single { provideHttpLoggingInterceptor() }
    single { RemoteSourceManager(get()) }
}

