package com.example.myapplication.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.constant.AppConstant
import com.example.myapplication.databinding.DetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    val mViewModel: DetailsViewModel by hiltNavGraphViewModels(R.id.main_graph)

    lateinit var mDataBinding: DetailsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false)
        return mDataBinding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDataBinding.setVariable(BR.detailsViewModel, mViewModel)
        mDataBinding.setLifecycleOwner(viewLifecycleOwner)
        mDataBinding.executePendingBindings()

        val firstName =
            getString(R.string.first_name) + " : " + arguments?.getString(AppConstant.FIRST_NAME)
        mDataBinding.tvFname.setText(firstName)


        val lastName =
            getString(R.string.last_name) + " : " + arguments?.getString(AppConstant.LAST_NAME)
        mDataBinding.tvLname.setText(lastName)


        mDataBinding.tvDob.setText(getDate(arguments?.getString(AppConstant.DATE_OF_BIRTH)))
    }

    @SuppressLint("NewApi")
    fun getDate(dob: String?): String {
        val date1: Date = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(dob)

        val cal = Calendar.getInstance()
        cal.time = date1
        val today = LocalDate.now()
        val birthday: LocalDate = LocalDate.of(
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH ), cal.get(Calendar.DAY_OF_MONTH)
        )

        val p = Period.between(birthday, today)
        return "Age :" + p.years + " years" + " ," + (p.months - 1) + " months" + " ," + p.days + " days"
    }
}