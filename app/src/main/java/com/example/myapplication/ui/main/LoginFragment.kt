package com.example.myapplication.ui.main

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class LoginFragment : Fragment() {
    val mViewModel: LoginViewModel by hiltNavGraphViewModels(R.id.main_graph)

    lateinit var  mDataBinding:LoginFragmentBinding
    val myCalendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        return mDataBinding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDataBinding.setVariable(BR.loginViewModel, mViewModel)
        mDataBinding.setLifecycleOwner(viewLifecycleOwner)
        mDataBinding.executePendingBindings()
        mViewModel.getErrorResult().observe(viewLifecycleOwner, { t ->  if (t){
         Toast.makeText(context,R.string.details_empty,Toast.LENGTH_SHORT).show()
        }
        })
        mDataBinding.textInputDOBEditText.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                hideKeyboard(requireActivity())

                context?.let {
                    DatePickerDialog(
                        it, date, myCalendar[Calendar.YEAR],
                        myCalendar[Calendar.MONTH],
                        myCalendar[Calendar.DAY_OF_MONTH]
                    ).show()
                }
            }
        })
    }

    /**
     * This method hides the Soft KeyBoard.
     *
     * @param activity of the view hosted.
     */
    fun hideKeyboard(activity: Activity) {
        val mInputMethodManager =
            activity.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
        val view = activity.findViewById<View>(android.R.id.content)
        if (view != null) {
            mInputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                0
            )
        }
    }

    var date =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = monthOfYear
            myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            updateLabel()
        }

    private fun updateLabel() {
        val myFormat = "dd/MM/yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        mViewModel.getDOB()
        mDataBinding.textInputDOBEditText.setText(sdf.format(myCalendar.time))
    }

    override fun onDestroy() {
        mViewModel.getErrorResult().removeObservers(this)
        super.onDestroy()
    }
}