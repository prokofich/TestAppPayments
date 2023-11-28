package com.example.testapppayments.model.responsemodel

import com.google.gson.annotations.SerializedName

data class ModelLoginData(
    @SerializedName("login")
    var login:String,

    @SerializedName("password")
    var password:String
)
