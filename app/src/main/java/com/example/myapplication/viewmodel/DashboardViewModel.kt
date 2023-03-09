package com.example.myapplication.viewmodel

import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.datamanager.PreferenceManager
import com.example.myapplication.uimodules.DashboardNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    val preferenceManager: PreferenceManager
) : BaseViewModel<DashboardNavigator>()  {


}