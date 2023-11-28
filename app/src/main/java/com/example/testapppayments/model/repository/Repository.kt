package com.example.testapppayments.model.repository

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.testapppayments.model.api.RetrofitInstance
import com.example.testapppayments.model.constant.TOKEN
import com.example.testapppayments.model.responsemodel.ModelLoginData
import com.example.testapppayments.model.responsemodel.ModelPaymentsData
import com.example.testapppayments.model.responsemodel.ModelTokenData
import retrofit2.Response

class Repository {

    // функция получения токена
    suspend fun getTokenForInput(dataLoginData: ModelLoginData): Response<ModelTokenData> {
        return RetrofitInstance.api.getTokenForInput(dataLoginData)
    }

    // функция получения платежей
    suspend fun getPayments(token:String):Response<ModelPaymentsData>{
        return RetrofitInstance.api.getPayments(token)
    }

    // функция сохранения токена
    fun saveToken(token:String,context: Context){
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit()
            .putString(TOKEN,token)
            .apply()
    }

    // функция получения токена
    fun getToken(context: Context):String{
        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(TOKEN,"")!!
    }

}