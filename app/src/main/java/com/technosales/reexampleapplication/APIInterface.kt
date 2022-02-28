package com.technosales.reexampleapplication

import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=6fb0e0cb74b94a2387b8a342d689893b
//https://newsapi.org/v2/everything?q=tesla&from=2022-01-28&sortBy=publishedAt&apiKey=6fb0e0cb74b94a2387b8a342d689893b
const val BASE_URL= "https://newsapi.org/"
const val API_KEY = "6fb0e0cb74b94a2387b8a342d689893b"
interface APIInterface {
    @GET("v2/top-headlines?apikey=$API_KEY")
    fun getHeadLines(@Query("country")country:String,@Query("page")page:Int) : retrofit2.Call<News>
    //https://newsapi.org/v2/top-headlines?apikey=$API_KEY&country=in&page=1

}

object  NewsService{
    val  newsInstance : APIInterface
    init {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

            .build()
           newsInstance = retrofitBuilder .create(APIInterface::class.java)

    }
}