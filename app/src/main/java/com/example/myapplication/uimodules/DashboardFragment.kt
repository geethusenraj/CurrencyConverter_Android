package com.example.myapplication.uimodules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentDashboardBinding
import com.example.myapplication.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>(),
    DashboardNavigator {


    private val dashboardViewModel: DashboardViewModel by viewModels()
    private lateinit var fragmentDashboardBinding: FragmentDashboardBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentDashboardBinding =
            DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        fragmentDashboardBinding.lifecycleOwner = this.viewLifecycleOwner
        fragmentDashboardBinding.viewModel = dashboardViewModel
        dashboardViewModel.setNavigator(this)
        return fragmentDashboardBinding.root

    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_dashboard
    }

    override fun getViewModel(): DashboardViewModel {
        return dashboardViewModel
    }


}