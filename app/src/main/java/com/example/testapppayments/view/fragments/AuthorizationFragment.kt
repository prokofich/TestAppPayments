package com.example.testapppayments.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testapppayments.R
import com.example.testapppayments.databinding.FragmentAuthorizationBinding
import com.example.testapppayments.viewmodel.AuthorizationViewModel

class AuthorizationFragment : Fragment() {

    private var _binding : FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!
    private var authViewModel : AuthorizationViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater,container,false)
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /** проверка был ли выход из аккаунта */
        authViewModel?.checkLogout()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = ViewModelProvider(this)[AuthorizationViewModel::class.java]

        authViewModel?.navToPaymentsFragment = { findNavController().navigate(R.id.paymentsFragment, it) }

        /** обработка нажатия на кнопку отправки логина и пароля */
        binding.idAuthButtonNext.setOnClickListener {
            if(authViewModel?.checkLoginData(binding.idAuthEtLogin.text.toString(),binding.idAuthEtPassword.text.toString()) == true){
                binding.idAuthPb.isIndeterminate = true
                authViewModel?.sendDataLogin(binding.idAuthEtLogin.text.toString(),binding.idAuthEtPassword.text.toString())
            }else{
                authViewModel?.showToast("Input fields must not be empty")
            }
        }

        authViewModel?.token?.observe(viewLifecycleOwner){
            binding.idAuthPb.isVisible = false
            if(it.body()?.success == true){
                authViewModel?.saveToken(it.body()?.response?.token)
                authViewModel?.goToPaymentsFragment(it.body()?.response?.token)
            }else{
                authViewModel?.showToast("Incorrect login or password")
            }
        }

        /** обработка выхода из приложения */
        binding.idAuthButtonExit.setOnClickListener {
            authViewModel?.showExitDialog()
        }

        /** обработка выхода из приложения */
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            authViewModel?.showExitDialog()
        }

    }

    /** очистка биндинга при очистке View */
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}