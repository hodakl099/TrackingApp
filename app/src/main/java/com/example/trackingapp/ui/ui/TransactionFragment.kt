package com.example.trackingapp.ui.ui

import android.annotation.SuppressLint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trackingapp.R
import com.example.trackingapp.databinding.FragmentTransactionBinding
import com.example.trackingapp.ui.adapters.TransactionAdapter
import com.example.trackingapp.ui.adapters.TransactionClickListener
import com.example.trackingapp.ui.data.entity.Transaction
import com.example.trackingapp.ui.viewmodel.TransactionViewModel


class TransactionFragment : Fragment(), TransactionClickListener {


    private lateinit var binding: FragmentTransactionBinding

    private lateinit var recyclerView : RecyclerView

    private lateinit var adapter: TransactionAdapter

    private lateinit var transactionViewModel: TransactionViewModel

    private lateinit var listener : TransactionClickListener




    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val bindingFragmentTransaction = FragmentTransactionBinding.inflate(layoutInflater, container,false)
        binding = bindingFragmentTransaction



        transactionViewModel = ViewModelProvider(requireActivity())[TransactionViewModel::class.java]


        listener = this

        val transactionAdapter = TransactionAdapter()

        recyclerView = binding.rvTransaction

        recyclerView.adapter = transactionAdapter



        transactionViewModel.getAllTransaction.observe(viewLifecycleOwner){transactions->
                 adapter.differ.submitList(transactions)
        }
        adapter.setOnItemClickListener {
            findNavController().navigate(R.id.action_transactionFragment_to_detailTransactionFragment)
        }

        setUpRecyclerView()
        return binding.root
    }

    override fun onTransactionClickListener(view: View, transaction: Transaction) {
        TODO("Not yet implemented")
    }

    private fun setUpRecyclerView() = lifecycleScope.launchWhenCreated {
        adapter = TransactionAdapter()
        binding.rvTransaction.adapter = adapter
        binding.rvTransaction.layoutManager = LinearLayoutManager(activity)
    }



}