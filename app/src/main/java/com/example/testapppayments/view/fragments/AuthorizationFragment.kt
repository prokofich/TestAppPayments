package com.example.testapppayments.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.testapppayments.databinding.FragmentAuthorizationBinding
import com.example.testapppayments.viewmodel.AuthorizationViewModel

class AuthorizationFragment : Fragment() {

    private var binding : FragmentAuthorizationBinding? = null
    private var authViewModel : AuthorizationViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthorizationBinding.inflate(inflater,container,false)
        return binding?.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // проверка был ли выход из аккаунта
        authViewModel?.checkLogout(requireContext())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = ViewModelProvider(this)[AuthorizationViewModel::class.java]

        // обработка нажатия на кнопку отправки логина и пароля
        binding!!.idAuthButtonNext.setOnClickListener {
            if(authViewModel?.checkLoginData(binding?.idAuthEtLogin?.text.toString(),binding?.idAuthEtPassword?.text.toString()) == true){
                binding?.idAuthPb?.isIndeterminate = true // показ процесса загрузки
                authViewModel?.sendDataLogin(binding?.idAuthEtLogin?.text.toString(),binding?.idAuthEtPassword?.text.toString())
            }else{
                authViewModel?.showToast(requireContext(),"Input fields must not be empty") // показ сообщения об ошибке
            }
        }

        authViewModel?.token?.observe(viewLifecycleOwner){
            binding!!.idAuthPb.isVisible = false // скрытие процесса загрузки
            if(it.body()?.success == true){
                authViewModel?.saveToken(it.body()?.response?.token,requireContext()) // сохранение токена
                authViewModel?.goToPaymentsFragment(it.body()?.response?.token) // переход к просмотру платежей
            }else{
                authViewModel?.showToast(requireContext(),"Incorrect login or password") // вывод сообщения об ошибке
            }
        }

        // обработка выхода из приложения
        binding!!.idAuthButtonExit.setOnClickListener {
            authViewModel?.showExitDialog(requireContext())
        }

        // обработка выхода из приложения
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            authViewModel?.showExitDialog(requireContext())
        }

    }

    // очистка биндинга при очистке View
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}