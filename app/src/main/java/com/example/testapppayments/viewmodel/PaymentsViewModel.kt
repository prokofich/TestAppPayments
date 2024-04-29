package com.example.testapppayments.viewmodel

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapppayments.model.constant.MAIN
import com.example.testapppayments.model.repository.Repository
import com.example.testapppayments.model.responsemodel.ModelPaymentsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PaymentsViewModel:ViewModel() {

    private val repository = Repository()
    val payments : MutableLiveData <Response <ModelPaymentsData> > = MutableLiveData()

    // функция получения платежей
    fun getPayments(token : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getPayments(token)
            withContext(Dispatchers.Main){
                payments.value = response
            }
        }
    }

    // сохранение токена
    private fun deleteToken(context : Context) = repository.saveToken("",context)

    // функция показа диалогового сообщения о выходе
    fun showExitDialog(context : Context) {
        val options = arrayOf("exit", "cancel")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("do you want to get out?")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> {
                    deleteToken(context)
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