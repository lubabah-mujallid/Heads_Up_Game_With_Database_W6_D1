package com.example.heads_up_game

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @GET("/celebrities/")
    fun getPerson(): Call<ArrayList<Celeb.Details>>
}