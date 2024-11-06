package com.example.testapppayments.viewmodel

import android.app.AlertDialog
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testapppayments.model.constant.MAIN
import com.example.testapppayments.model.repository.Repository
import com.example.testapppayments.model.responseModel.ModelPaymentsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PaymentsViewModel(application: Application): AndroidViewModel(application) {

    private val repository = Repository(application)
    val payments: MutableLiveData<Response<ModelPaymentsData>> = MutableLiveData()

    /** функция получения платежей */
    fun getPayments(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getPayments(token)
            withContext(Dispatchers.Main){
                payments.value = response
            }
        }
    }

    /** функция сохранения токена */
    private fun deleteToken() = repository.saveToken("")

    /** функция показа диалогового сообщения о выходе */
    fun showExitDialog() {
        val options = arrayOf("exit", "cancel")
        val builder = AlertDialog.Builder(getApplication())
        builder.setTitle("do you want to get out?")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> {
                    deleteToken()
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