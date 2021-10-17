package com.example.heads_up_game

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Celeb : Serializable{
    @SerializedName("data")
    var data: List<Details>? = null
    data class Details(
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("taboo1")
        val taboo1: String? = null,
        @SerializedName("taboo2")
        val taboo2: String? = null,
        @SerializedName("taboo3")
        val taboo3: String? = null,
        //val pk: Int? = null
    ) : Serializable
}