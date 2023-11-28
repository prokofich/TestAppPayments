package com.example.testapppayments.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapppayments.R
import com.example.testapppayments.model.constant.MAIN
import com.example.testapppayments.model.constant.TOKEN
import com.example.testapppayments.model.repository.Repository
import com.example.testapppayments.model.responsemodel.ModelLoginData
import com.example.testapppayments.model.responsemodel.ModelTokenData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class AuthorizationViewModel:ViewModel() {

    private val repository = Repository()
    val token: MutableLiveData<Response<ModelTokenData>> = MutableLiveData()

    // функция проверки ввода логина и пароля
    fun checkLoginData(login:String,password:String): Boolean {
        return login!="" && password!=""
    }

    // функция отправки пароля и логина на сервер для получения токена
    fun sendDataLogin(login:String,password:String){
        val dataLogin = ModelLoginData(login,password)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getTokenForInput(dataLogin)
            withContext(Dispatchers.Main){
                token.value = response
            }
        }
    }

    // функция показа всплывающего сообщения
    fun showToast(context:Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    // функция перехода к следующему фрагменту(просмотру платежей)
    fun goToPaymentsFragment(token:String){
        val bundle = Bundle()
        bundle.putString(TOKEN,token)
        MAIN.navController.navigate(R.id.action_authorizationFragment_to_paymentsFragment,bundle)
    }

    // проверка был ли выход из аккаунта
    fun checkLogout(context: Context){
        if(repository.getToken(context)!=""){
            goToPaymentsFragment(repository.getToken(context))
        }
    }

    // сохранение токена
    fun saveToken(token: String,context: Context){
        repository.saveToken(token,context)
    }


    // функция показа диалогового сообщения о выходе
    fun showExitDialog(context: Context) {
        val options = arrayOf("exit", "cancel")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("do you want to get out?")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> {
                    MAIN.finishAffinity()
                }
                1 -> {
                    dialog.cancel()
                }
            }
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

}