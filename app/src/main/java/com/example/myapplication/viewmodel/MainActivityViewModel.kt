package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.data.db.repo.RatesRepository
import com.example.myapplication.data.response.ApiResponse
import com.example.myapplication.data.response.Rates
import com.example.myapplication.datamanager.PreferenceManager
import com.example.myapplication.helper.Resource
import com.example.myapplication.helper.SingleLiveEvent
import com.example.myapplication.repo.MainRepo
import com.example.myapplication.uimodules.MainActivityNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val preferenceManager: PreferenceManager,
    private val mainRepo: MainRepo
) :
    BaseViewModel<MainActivityNavigator>() {

    //cached
    private val _data = SingleLiveEvent<Resource<ApiResponse>>()


    //public
    val data = _data

    fun saveRatesDataToRoom(repository: RatesRepository, rates: Rates?, baseCode: String) =
        viewModelScope.launch {
            if (rates != null) {
                repository.saveRatesDataToRoom(rates, baseCode)
            }
        }

    fun fetchRateInfoFromRoom(repository: RatesRepository, selectedItem1: String?) =
        viewModelScope.launch {
            if (navigator != null) {
                navigator?.showOfflineRateInfo(repository.getRateInfoFromRoom(selectedItem1))
            }
        }

    //Public function to get the result of conversion
    fun getConvertedData(to: String) {
        viewModelScope.launch {
            mainRepo.getConvertedData(to).collect {
                data.value = it
            }
        }
    }

    fun getResultAmount(selectedCurrency: String?, rates: Rates?): Double? {
        return when (selectedCurrency) {
            "USD" -> rates?.USD
            "AED" -> rates?.AED
            "AFN" -> rates?.AFN
            "ALL" -> rates?.ALL
            "AMD" -> rates?.AMD
            "ANG" -> rates?.ANG
            "AOA" -> rates?.AOA
            "ARS" -> rates?.ARS
            "AUD" -> rates?.AUD
            "AWG" -> rates?.AWG
            "AZN" -> rates?.AZN
            "BAM" -> rates?.BAM
            "BBD" -> rates?.BBD
            "BDT" -> rates?.BDT
            "BGN" -> rates?.BGN
            "BHD" -> rates?.BHD
            "BIF" -> rates?.BIF
            "BMD" -> rates?.BMD
            "BND" -> rates?.BND
            "BOB" -> rates?.BOB
            "BRL" -> rates?.BRL
            "BSD" -> rates?.BSD
            "BTN" -> rates?.BTN
            "BWP" -> rates?.BWP
            "BYN" -> rates?.BYN
            "BZD" -> rates?.BZD
            "CAD" -> rates?.CAD
            "CDF" -> rates?.CDF
            "CHF" -> rates?.CHF
            "CLP" -> rates?.CLP
            "CNY" -> rates?.CNY
            "COP" -> rates?.COP
            "CRC" -> rates?.CRC
            "CUP" -> rates?.CUP
            "CVE" -> rates?.CVE
            "CZK" -> rates?.CZK
            "DJF" -> rates?.DJF
            "DKK" -> rates?.DKK
            "DOP" -> rates?.DOP
            "DZD" -> rates?.DZD
            "EGP" -> rates?.EGP
            "ERN" -> rates?.ERN
            "ETB" -> rates?.ETB
            "EUR" -> rates?.EUR
            "FJD" -> rates?.FJD
            "FKP" -> rates?.FKP
            "FOK" -> rates?.FOK
            "GBP" -> rates?.GBP
            "GEL" -> rates?.GEL
            "GGP" -> rates?.GGP
            "GHS" -> rates?.GHS
            "GIP" -> rates?.GIP
            "GMD" -> rates?.GMD
            "GNF" -> rates?.GNF
            "GTQ" -> rates?.GTQ
            "GYD" -> rates?.GYD
            "HKD" -> rates?.HKD
            "HNL" -> rates?.HNL
            "HRK" -> rates?.HRK
            "HTG" -> rates?.HTG
            "HUF" -> rates?.HUF
            "IDR" -> rates?.IDR
            "ILS" -> rates?.ILS
            "IMP" -> rates?.IMP
            "INR" -> rates?.INR
            "IQD" -> rates?.IQD
            "IRR" -> rates?.IRR
            "ISK" -> rates?.ISK
            "JEP" -> rates?.JEP
            "JMD" -> rates?.JMD
            "JOD" -> rates?.JOD
            "JPY" -> rates?.JPY
            "KES" -> rates?.KES
            "KGS" -> rates?.KGS
            "KHR" -> rates?.KHR
            "KID" -> rates?.KID
            "KMF" -> rates?.KMF
            "KRW" -> rates?.KRW
            "KWD" -> rates?.KWD
            "KYD" -> rates?.KYD
            "KZT" -> rates?.KZT
            "LAK" -> rates?.LAK
            "LBP" -> rates?.LBP
            "LKR" -> rates?.LKR
            "LRD" -> rates?.LRD
            "LSL" -> rates?.LSL
            "LYD" -> rates?.LYD
            "MAD" -> rates?.MAD
            "MDL" -> rates?.MDL
            "MGA" -> rates?.MGA
            "MKD" -> rates?.MKD
            "MMK" -> rates?.MMK
            "MNT" -> rates?.MNT
            "MOP" -> rates?.MOP
            "MRU" -> rates?.MRU
            "MUR" -> rates?.MUR
            "MVR" -> rates?.MVR
            "MWK" -> rates?.MWK
            "MXN" -> rates?.MXN
            "MYR" -> rates?.MYR
            "MZN" -> rates?.MZN
            "NAD" -> rates?.NAD
            "NGN" -> rates?.NGN
            "NIO" -> rates?.NIO
            "NOK" -> rates?.NOK
            "NPR" -> rates?.NPR
            "NZD" -> rates?.NZD
            "OMR" -> rates?.OMR
            "PAB" -> rates?.PAB
            "PEN" -> rates?.PEN
            "PGK" -> rates?.PGK
            "PHP" -> rates?.PHP
            "PKR" -> rates?.PKR
            "PLN" -> rates?.PLN
            "PYG" -> rates?.PYG
            "QAR" -> rates?.QAR
            "RON" -> rates?.RON
            "RSD" -> rates?.RSD
            "RUB" -> rates?.RUB
            "RWF" -> rates?.RWF
            "SAR" -> rates?.SAR
            "SBD" -> rates?.SBD
            "SCR" -> rates?.SCR
            "SDG" -> rates?.SDG
            "SEK" -> rates?.SEK
            "SGD" -> rates?.SGD
            "SHP" -> rates?.SHP
            "SLE" -> rates?.SLE
            "SLL" -> rates?.SLL
            "SOS" -> rates?.SOS
            "SRD" -> rates?.SRD
            "SSP" -> rates?.SSP
            "STN" -> rates?.STN
            "SYP" -> rates?.SYP
            "SZL" -> rates?.SZL
            "THB" -> rates?.THB
            "TJS" -> rates?.TJS
            "TMT" -> rates?.TMT
            "TND" -> rates?.TND
            "TOP" -> rates?.TOP
            "TRY" -> rates?.TRY
            "TTD" -> rates?.TTD
            "TVD" -> rates?.TVD
            "TWD" -> rates?.TWD
            "TZS" -> rates?.TZS
            "UAH" -> rates?.UAH
            "UGX" -> rates?.UGX
            "UYU" -> rates?.UYU
            "UZS" -> rates?.UZS
            "VES" -> rates?.VES
            "VND" -> rates?.VND
            "VUV" -> rates?.VUV
            "WST" -> rates?.WST
            "XAF" -> rates?.XAF
            "XCD" -> rates?.XCD
            "XDR" -> rates?.XDR
            "XOF" -> rates?.XOF
            "XPF" -> rates?.XPF
            "YER" -> rates?.YER
            "ZAR" -> rates?.ZAR
            "ZMW" -> rates?.ZMW
            "ZWL" -> rates?.ZWL
            else -> 0.0
        }

    }


}