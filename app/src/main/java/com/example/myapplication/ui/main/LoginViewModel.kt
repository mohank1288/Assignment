package com.example.myapplication.ui.main

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.constant.AppConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel(), View.OnClickListener {
    private val fname: MutableLiveData<String> = MutableLiveData()
    private val lname: MutableLiveData<String> = MutableLiveData()
    private val dob: MutableLiveData<String> = MutableLiveData()
    private val errorResult: MutableLiveData<Boolean> = MutableLiveData()

    fun getFirstName(): MutableLiveData<String> {
        return fname
    }


    fun getLastName(): MutableLiveData<String> {
        return lname
    }


    fun getDOB(): MutableLiveData<String> {
        return dob
    }

    fun getErrorResult(): MutableLiveData<Boolean> {
        return errorResult
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                if (TextUtils.isEmpty(fname.value) || TextUtils.isEmpty(lname.value) ||
                    TextUtils.isEmpty(dob.value)
                ) {
                    errorResult.postValue(true)
                } else {
                    val bundle = Bundle()
                    bundle.putString(AppConstant.FIRST_NAME,fname.value)
                    bundle.putString(AppConstant.LAST_NAME,lname.value)
                    bundle.putString(AppConstant.DATE_OF_BIRTH,dob.value)
                    val navController = Navigation.findNavController(v)
                    navController.navigate(R.id.action_login_to_details,bundle)
                }
            }


        }
    }
}