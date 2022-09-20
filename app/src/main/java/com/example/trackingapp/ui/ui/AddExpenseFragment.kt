package com.example.trackingapp.ui.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trackingapp.R
import com.example.trackingapp.databinding.FragmentAddExpenseBinding
import com.example.trackingapp.ui.data.Money
import com.example.trackingapp.ui.viewmodel.CategoryViewModel
import java.util.*


class AddExpenseFragment : Fragment() {



    private lateinit var binding : FragmentAddExpenseBinding

    private lateinit var categoryViewModel : CategoryViewModel



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val bindingFragment = FragmentAddExpenseBinding.inflate(layoutInflater,container,false)

        categoryViewModel = ViewModelProvider(requireActivity())[CategoryViewModel::class.java]

         binding = bindingFragment



        val categories = resources.getStringArray(R.array.categories_array)
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_item,
            categories
        )
        binding.tvAutoCompleteCategory.setAdapter(arrayAdapter)

        val paymentMethods = resources.getStringArray(R.array.payment_methods)
        val arrayPaymentAdapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_item,
            paymentMethods
        )
        binding.tvAutoCompletePaymentText.setAdapter(arrayPaymentAdapter)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        binding.tvDateText.setOnClickListener{
            val dpd = DatePickerDialog(requireContext(), { view, mYear, mMonth, mDay ->

                binding.tvDateText.text = "$mDay/${mMonth + 1}/$mYear"

            }, year, month, day)
            dpd.show()
        }


        binding.btnAdd.setOnClickListener {

            if (binding.inputTextAmount.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Please enter the amount", Toast.LENGTH_LONG).show()
            } else {
                if (binding.radioExpense.isChecked) {
                    val expenseText = binding.inputTextAmount.text.toString().toFloat()

                    val bundle = Bundle()

                    bundle.putFloat("expenseText", expenseText)
                    findNavController().navigate(
                        R.id.action_addExpenseFragment_to_homeFragment,
                        bundle
                    )
                }
                if (binding.radioIncome.isChecked) {

                    insertIncomeToDatabase()

//                    val bundle = Bundle()
//                    bundle.putFloat("incomeText", incomeText)
//                    findNavController().navigate(
//                        R.id.action_addExpenseFragment_to_homeFragment,
//                    )
                }


            }
            }

        return binding.root
    }

    private fun insertIncomeToDatabase(){
        val incomeText = binding.inputTextAmount.text.toString()

        val category = Money(null,null,incomeText)

        categoryViewModel.addUser(category)

        findNavController().navigate(
            R.id.action_addExpenseFragment_to_homeFragment,
            )

    }
}

