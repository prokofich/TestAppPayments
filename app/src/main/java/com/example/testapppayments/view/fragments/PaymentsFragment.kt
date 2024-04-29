package com.example.testapppayments.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.testapppayments.R
import com.example.testapppayments.databinding.FragmentPaymentsBinding
import com.example.testapppayments.model.adapter.PaymentsAdapter
import com.example.testapppayments.model.constant.TOKEN
import com.example.testapppayments.viewmodel.PaymentsViewModel

class PaymentsFragment : Fragment() {

    private var binding : FragmentPaymentsBinding? = null
    private var paymentsViewModel : PaymentsViewModel? = null
    private var recyclerView : RecyclerView? = null
    private var adapterPayments : PaymentsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentsBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paymentsViewModel = ViewModelProvider(this)[PaymentsViewModel::class.java]

        recyclerView = view.findViewById(R.id.id_payments_rv)
        adapterPayments = PaymentsAdapter()
        recyclerView?.adapter = adapterPayments

        paymentsViewModel?.getPayments(requireArguments().getString(TOKEN).toString()) // отправка запроса на получение платежей

        // выход из аккаунта
        binding?.idPaymentsButtonExit?.setOnClickListener {
            paymentsViewModel?.showExitDialog(requireContext())
        }

        // выход из аккаунта
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            paymentsViewModel?.showExitDialog(requireContext())
        }

        // отправка полученных платежей в recyclerview
        paymentsViewModel?.payments?.observe(viewLifecycleOwner){ data ->
            adapterPayments?.setList(data.body()?.response)
        }

    }

    // очистка биндинга при очистке View
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}