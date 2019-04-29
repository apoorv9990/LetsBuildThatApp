package com.letsbuildthatapp.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.letsbuildthatapp.BuildConfig
import com.letsbuildthatapp.model.HomeFeed
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object LetsBuildThatAppServiceFactory {
  val letsBuildThatAppService by lazy { makeLetsBuildThatAppService() }

  private fun makeLetsBuildThatAppService(): LetsBuildThatAppService {

    val loggingInterceptor = makeLoggingInterceptor()
    val okHttpClient = makeOkHttpClient(loggingInterceptor)
    val gson = makeGson()

    val retrofit = Retrofit.Builder()
      .baseUrl("https://api.letsbuildthatapp.com/youtube/")
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .build()

    return retrofit.create(LetsBuildThatAppService::class.java)
  }

  private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(httpLoggingInterceptor)
      .connectTimeout(120, TimeUnit.SECONDS)
      .readTimeout(120, TimeUnit.SECONDS)
      .build()
  }

  private fun makeGson(): Gson {
    return GsonBuilder()
      .setLenient()
      .registerTypeAdapter(HomeFeed::class.java, HomeFeedDeserializer())
//      .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .create()
  }

  private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = if (BuildConfig.DEBUG)
      HttpLoggingInterceptor.Level.BODY
    else
      HttpLoggingInterceptor.Level.NONE
    return logging
  }
}