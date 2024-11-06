package com.example.testapppayments.viewmodel

import android.app.AlertDialog
import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testapppayments.model.constant.MAIN
import com.example.testapppayments.model.constant.TOKEN
import com.example.testapppayments.model.repository.Repository
import com.example.testapppayments.model.responseModel.ModelLoginData
import com.example.testapppayments.model.responseModel.ModelTokenData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(application)
    val token: MutableLiveData<Response<ModelTokenData>> = MutableLiveData()

    /** переход на экран платежей */
    var navToPaymentsFragment: ((Bundle) -> Unit)? = null

    /** функция проверки ввода логина и пароля */
    fun checkLoginData(login: String, password: String): Boolean = login.isNotEmpty() && password.isNotEmpty()

    /** функция отправки пароля и логина на сервер для получения токена */
    fun sendDataLogin(login: String?, password: String?){
        if(login != null && password != null){
            val dataLogin = ModelLoginData(login, password)
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.getTokenForInput(dataLogin)
                withContext(Dispatchers.Main){
                    token.value = response
                }
            }
        }
    }

    /** функция показа всплывающего сообщения */
    fun showToast(message: String) = Toast.makeText(getApplication(),message,Toast.LENGTH_SHORT).show()

    /** функция перехода к следующему фрагменту (просмотру платежей) */
    fun goToPaymentsFragment(token: String?) {
        token?.let {
            val bundle = Bundle()
            bundle.putString(TOKEN, it)
            navToPaymentsFragment?.invoke(bundle)
        }
    }

    /** проверка был ли выход из аккаунта */
    fun checkLogout() {
        if(repository.getToken().isNotEmpty()) goToPaymentsFragment(repository.getToken())
    }

    /** сохранение токена */
    fun saveToken(token: String?) = repository.saveToken(token)

    // функция показа диалогового сообщения о выходе
    fun showExitDialog() {
        val options = arrayOf("exit", "cancel")
        val builder = AlertDialog.Builder(getApplication())
        builder.setTitle("do you want to get out?")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> { MAIN.finishAffinity() }
                1 -> { dialog.cancel()       }
            }
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

}