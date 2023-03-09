package com.example.myapplication.uimodules

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.data.db.MainDatabase
import com.example.myapplication.data.db.repo.RatesRepository
import com.example.myapplication.data.response.Rates
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.helper.Resource
import com.example.myapplication.helper.Utility
import com.example.myapplication.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() :
    BaseActivity<ActivityMainBinding, MainActivityViewModel>(),
    MainActivityNavigator {


    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private var mainActivityBinding: ActivityMainBinding? = null

    private var selectedItem1: String? = "AFN"
    private var selectedItem2: String? = "AFN"

    private var ratesRepository: RatesRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = getViewDataBinding()
        mainActivityViewModel.setNavigator(this)
        initDb()
        //Initialize both Spinner
        initSpinner()
        setUpClickListener()
    }

    private fun initDb() {
        val appDatabase = this.applicationContext?.let { MainDatabase.getDatabase(it) }!!
        val ratesDao = this.applicationContext?.let { MainDatabase.getDatabase(it).getRatesDao() }!!
        ratesRepository = RatesRepository(appDatabase, ratesDao)
    }

    private fun initSpinner() {

        //get first spinner country reference in view
        val spinner1 = mainActivityBinding?.spnFirstCountry

        //set items in the spinner i.e a list of all countries
        spinner1?.setItems(getAllCountries())

        //hide key board when spinner shows (For some weird reasons, this isn't so effective as I am using a custom Material Spinner)
        spinner1?.setOnClickListener {
            Utility.hideKeyboard(this)
        }

        //Handle selected item, by getting the item and storing the value in a  variable - selectedItem1
        spinner1?.setOnItemSelectedListener { view, position, id, item ->
            //Set the currency code for each country as hint
            val countryCode = getCountryCode(item.toString())
            val currencySymbol = getSymbol(countryCode)
            selectedItem1 = currencySymbol
            mainActivityBinding?.txtFirstCurrencyName?.text = selectedItem1
        }


        //get second spinner country reference in view
        val spinner2 = mainActivityBinding?.spnSecondCountry

        //hide key board when spinner shows
        spinner1?.setOnClickListener {
            Utility.hideKeyboard(this)
        }

        //set items on second spinner i.e - a list of all countries
        spinner2?.setItems(getAllCountries())


        //Handle selected item, by getting the item and storing the value in a  variable - selectedItem2,
        spinner2?.setOnItemSelectedListener { view, position, id, item ->
            //Set the currency code for each country as hint
            val countryCode = getCountryCode(item.toString())
            val currencySymbol = getSymbol(countryCode)
            selectedItem2 = currencySymbol
            mainActivityBinding?.txtSecondCurrencyName?.text = selectedItem2
        }

    }


    /**
     * A method for getting a country's currency symbol from the country's code
     * e.g USA - USD
     */

    private fun getSymbol(countryCode: String?): String? {
        val availableLocales = Locale.getAvailableLocales()
        for (i in availableLocales.indices) {
            if (availableLocales[i].country == countryCode
            ) return Currency.getInstance(availableLocales[i]).currencyCode
        }
        return ""
    }

    /**
     * A method for getting a country's code from the country name
     * e.g Nigeria - NG
     */

    private fun getCountryCode(countryName: String) =
        Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }


    /**
     * A method for getting all countries in the world - about 256 or so
     */

    private fun getAllCountries(): ArrayList<String> {

        val locales = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country = locale.displayCountry
            if (country.trim { it <= ' ' }.isNotEmpty() && !countries.contains(country)) {
                countries.add(country)
            }
        }
        countries.sort()

        return countries
    }

    private fun setUpClickListener() {

        //Convert button clicked - check for empty string and internet then do the conersion
        mainActivityBinding?.btnConvert?.setOnClickListener {

            //check if the input is empty
            val numberToConvert = mainActivityBinding?.etFirstCurrency?.text.toString()

            if (numberToConvert.isEmpty() || numberToConvert == "0") {
                Toast.makeText(
                    this@MainActivity,
                    "Input a value in the first text field, result will be shown in the second text field",
                    Toast.LENGTH_SHORT
                ).show()
            }

            //check if internet is available
            else if (!Utility.isNetworkAvailable(this)) {
                //hide keyboard
                Utility.hideKeyboard(this)
                Toast.makeText(
                    this@MainActivity,
                    "You are not connected to the internet",
                    Toast.LENGTH_SHORT
                ).show()

                ratesRepository?.let { it1 ->
                    mainActivityViewModel.fetchRateInfoFromRoom(
                        it1,
                        selectedItem1
                    )
                }
            }

            //carry on and convert the value
            else {
                doConversion()
            }
        }

    }

    private fun doConversion() {

        //hide keyboard
        Utility.hideKeyboard(this)

        //make progress bar visible
        mainActivityBinding?.prgLoading?.visibility = View.VISIBLE

        //make button invisible
        mainActivityBinding?.btnConvert?.visibility = View.GONE

        val to = selectedItem1
        val amount = mainActivityBinding?.etFirstCurrency?.text.toString().toDouble()

        //do the conversion
        if (to != null) {
            mainActivityViewModel.getConvertedData(to)
        }

        //observe for changes in UI
        observeUi()

    }

    private fun observeUi() {


        mainActivityViewModel.data.observe(this) { result ->

            when (result.status) {
                Resource.Status.SUCCESS -> {
                    if (result.data?.result == "success") {

                        ratesRepository?.let {
                            result.data.baseCode?.let { it1 ->
                                mainActivityViewModel.saveRatesDataToRoom(
                                    it,
                                    result.data.rates, it1
                                )
                            }
                        }
                        updateUiWithResult(result.data.rates)
                    } else if (result.data?.result == "fail") {
                        Toast.makeText(
                            this@MainActivity,
                            "Ooops! something went wrong, Try again",
                            Toast.LENGTH_SHORT
                        ).show()
                        //stop progress bar
                        mainActivityBinding?.prgLoading?.visibility = View.GONE
                        //show button
                        mainActivityBinding?.btnConvert?.visibility = View.VISIBLE
                    }
                }
                Resource.Status.ERROR -> {

                    Toast.makeText(
                        this@MainActivity,
                        "Ooops! something went wrong, Try again",
                        Toast.LENGTH_SHORT
                    ).show()
                    //stop progress bar
                    mainActivityBinding?.prgLoading?.visibility = View.GONE
                    //show button
                    mainActivityBinding?.btnConvert?.visibility = View.VISIBLE
                }

                Resource.Status.LOADING -> {
                    //stop progress bar
                    mainActivityBinding?.prgLoading?.visibility = View.VISIBLE
                    //show button
                    mainActivityBinding?.btnConvert?.visibility = View.GONE
                }
            }
        }

    }

    private fun updateUiWithResult(rates: Rates?) {
        val currencyAmount = mainActivityViewModel.getResultAmount(selectedItem2, rates)
        val inputAmount = mainActivityBinding?.etFirstCurrency?.text.toString().toDouble()
        //set the value in the second edit text field
        val result = currencyAmount?.times(inputAmount)
        val formattedString = String.format("%,.3f", result)
        Log.d("Converted Currency", result.toString())
        Log.d("Converted Currency : formattedString", formattedString)
        mainActivityBinding?.etSecondCurrency?.setText(formattedString)

        //stop progress bar
        mainActivityBinding?.prgLoading?.visibility = View.GONE
        //show button
        mainActivityBinding?.btnConvert?.visibility = View.VISIBLE
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainActivityViewModel {
        return mainActivityViewModel
    }

    override fun showOfflineRateInfo(rateInfoFromRoom: Rates) {
        if (rateInfoFromRoom != null) {
            updateUiWithResult(rateInfoFromRoom)
        }
    }

}