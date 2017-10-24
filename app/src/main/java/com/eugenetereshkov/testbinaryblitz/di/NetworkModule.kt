package com.eugenetereshkov.testbinaryblitz.di

import android.content.Context
import com.eugenetereshkov.testbinaryblitz.API_ENPOINT
import com.eugenetereshkov.testbinaryblitz.model.data.server.BinaryBlitzApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
@Module
class NetworkModule {
    @Singleton
    @Provides
    internal fun provideWalkwaysService(gson: Gson, okHttpClient: OkHttpClient) = Retrofit.Builder()
            .baseUrl(API_ENPOINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(BinaryBlitzApi::class.java)

    @Provides
    internal fun provideOkHttpClient(context: Context, httpLoggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    internal fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.HEADERS
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    internal fun provideGson() = GsonBuilder()
            .excludeFieldsWithModifiers(FINAL, TRANSIENT, STATIC)
            .serializeNulls()
            .create()
}