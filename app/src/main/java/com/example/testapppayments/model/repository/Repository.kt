package com.example.testapppayments.model.repository

import android.content.Context
import android.preference.PreferenceManager
import com.example.testapppayments.model.api.RetrofitInstance
import com.example.testapppayments.model.constant.TOKEN
import com.example.testapppayments.model.responseModel.ModelLoginData
import com.example.testapppayments.model.responseModel.ModelPaymentsData
import com.example.testapppayments.model.responseModel.ModelTokenData
import retrofit2.Response

class Repository(private val context: Context) {

    /** функция получения токена */
    suspend fun getTokenForInput(dataLoginData: ModelLoginData): Response<ModelTokenData> {
        return RetrofitInstance.api.getTokenForInput(dataLoginData)
    }

    /** функция получения платежей */
    suspend fun getPayments(token: String): Response<ModelPaymentsData> {
        return RetrofitInstance.api.getPayments(token)
    }

    /** функция сохранения токена */
    fun saveToken(token: String?) {
        token?.let {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(TOKEN,it).apply()
        }
    }

    /** функция получения токена */
    fun getToken(): String {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(TOKEN,"").toString()
    }

}