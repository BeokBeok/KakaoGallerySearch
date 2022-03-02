package com.beok.kakaogallerysearch.data.di

import com.beok.kakaogallerysearch.data.interceptor.KakaoInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
// moshi 사용한 이유는 한번 정리하고 가시면 좋을것 같아요. 저도 사용한적 없고 여기도 깊게 아시는 분이 별로 없을거라 질문하기 좋아보여요

import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
// 규격에 대해 확실하게 아는건 아니지만 API 문서상 iso8601 규격이여서, 질문하기 좋아보여요.
// 이것도 왜 문제 없는지 정리해두면 답변하기 좋을것 같아요
// https://ijmacd.github.io/rfc3339-iso8601/

import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Date
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://dapi.kakao.com/"

    @Provides
    @Singleton
    fun providesKotlinJsonAdapterFactory(): KotlinJsonAdapterFactory = KotlinJsonAdapterFactory()

    @Provides
    @Singleton
    fun providesRfc3339DateJsonAdapter(): Rfc3339DateJsonAdapter = Rfc3339DateJsonAdapter()

    @Provides
    @Singleton
    fun providesMoshi(
        jsonAdapter: KotlinJsonAdapterFactory,
        dateJsonAdapter: Rfc3339DateJsonAdapter
    ): Moshi = Moshi
        .Builder()
        .add(jsonAdapter)
        .add(Date::class.java, dateJsonAdapter)
        .build()

    @Provides
    @Singleton
    fun providesMoshiConverter(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun providesStethoInterceptor(): StethoInterceptor = StethoInterceptor()

    @Provides
    @Singleton
    fun providesOkHttpClient(stethoInterceptor: StethoInterceptor): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(KakaoInterceptor::intercept)
        .addNetworkInterceptor(stethoInterceptor)
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(
        converterFactory: MoshiConverterFactory,
        client: OkHttpClient
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(converterFactory)
        .client(client)
        .build()
}
