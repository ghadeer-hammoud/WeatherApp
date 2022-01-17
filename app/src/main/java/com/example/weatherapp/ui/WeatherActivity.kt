package com.example.weatherapp.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.weatherapp.*
import com.example.weatherapp.adapters.DaysRecyclerAdapter
import com.example.weatherapp.models.City
import com.example.weatherapp.models.WeatherReport
import com.example.weatherapp.utils.ViewModelProviderFactory
import com.example.weatherapp.viewmodels.WeatherViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class WeatherActivity : DaggerAppCompatActivity() {

    private val REQUEST_CODE_ACCESS_LOCATION = 100

    private lateinit var tvCityName: TextView
    private lateinit var tvCountryName: TextView
    private lateinit var ivIcon: ImageView
    private lateinit var tvTemperature: TextView
    private lateinit var tvTemperatureUnit: TextView
    private lateinit var tvDescription: TextView

    private lateinit var toolbar:Toolbar
    private lateinit var mainLayout:RelativeLayout
    private lateinit var recyclerView:RecyclerView
    private lateinit var progressBar:ProgressBar
    private lateinit var adapter:DaysRecyclerAdapter

    private lateinit var viewModel: WeatherViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory
    @Inject
    lateinit var glideInstance: RequestManager
    @Inject
    lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        tvCityName = findViewById(R.id.tvCityName)
        tvCountryName = findViewById(R.id.tvCountryName)
        ivIcon = findViewById(R.id.ivIcon)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvTemperatureUnit = findViewById(R.id.tvTemperatureUnit)
        tvDescription = findViewById(R.id.tvDescription)
        mainLayout = findViewById(R.id.mainLayout)
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)

        setSupportActionBar(toolbar)

        if(isLocationPermissionGranted()){
            init()
        }
        else{
            requestLocationPermission()
        }
    }

    private fun init(){

        adapter = DaysRecyclerAdapter(glideInstance, sharedPreference)
        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        var dividerItemDecoration =  DividerItemDecoration(this, layoutManager.orientation)
        dividerItemDecoration.setDrawable(AppCompatResources.getDrawable(this, R.drawable.recycler_divider)!!)
        recyclerView.addItemDecoration(dividerItemDecoration);

        viewModel = ViewModelProviders.of(this, providerFactory).get(WeatherViewModel::class.java)
        subscribeObservers()
    }

    private fun subscribeObservers(){

        viewModel.observeWeatherReport().observe(this, object: Observer<Resource<WeatherReport>> {
            override fun onChanged(resourceWeatherReport: Resource<WeatherReport>?) {

                if(resourceWeatherReport != null){

                    when(resourceWeatherReport.status){

                        Resource.Status.LOADING -> {
                            showProgressBar()
                        }
                        Resource.Status.SUCCESS -> {
                            hideProgressBar()
                            populateUIWithData(resourceWeatherReport.data)
                        }
                        Resource.Status.ERROR -> {
                            hideProgressBar()
                            Toast.makeText(this@WeatherActivity, resourceWeatherReport.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })

        viewModel.observeMessage().observe(this, {
            message ->
            if (message != null)
                Toast.makeText(this@WeatherActivity, message, Toast.LENGTH_SHORT).show()
        })

    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
        mainLayout.visibility = View.GONE
    }

    private fun hideProgressBar(){
        progressBar.visibility = View.GONE
        mainLayout.visibility = View.VISIBLE
    }

    private fun isLocationPermissionGranted(): Boolean{

        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }

        return false;
    }

    private fun requestLocationPermission(){

        ActivityCompat.requestPermissions(
            this,
            arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION),
            REQUEST_CODE_ACCESS_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_CODE_ACCESS_LOCATION){
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                init()
            } else {
                finish()
            }
            return

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun populateUIWithData(weatherReport: WeatherReport?){

        if(weatherReport != null){

            tvCityName.text = weatherReport.timezone
            tvCountryName.text = weatherReport.timezone
            tvTemperature.text = weatherReport.current!!.temp.toInt().toString()
            var unit = sharedPreference.getString("temp_unit", "standard")
            when(unit){
                "standard" -> {tvTemperatureUnit.text = "°K"}
                "metric" -> {tvTemperatureUnit.text = "°C"}
                "imperial" -> {tvTemperatureUnit.text = "°F"}
            }

            tvDescription.text = weatherReport.current!!.weather[0].main
            glideInstance.load(
                "http://openweathermap.org/img/wn/${weatherReport.current!!.weather[0].icon}@2x.png"

            ).into(ivIcon)

            adapter.setDays(weatherReport.daily)

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                navigateFavoriteCitiesActivity()
                return true
            }
            R.id.action_search -> {
                navigateSearchActivity()
                return true
            }
            R.id.action_settings -> {
                navigateSettingsActivity()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun navigateSearchActivity(){

        val intent = Intent(this, SearchCitiesActivity::class.java)
        activityResultLauncher.launch(intent)
    }

    fun navigateFavoriteCitiesActivity(){

        val intent = Intent(this, FavoriteCitiesActivity::class.java)
        activityResultLauncher.launch(intent)
    }

    fun navigateSettingsActivity(){

        val intent = Intent(this, MySettingsActivity::class.java)
        preferencesLauncher.launch(intent)
    }

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val city: City? = it.data?.getParcelableExtra("city")

                if(city != null){
                    viewModel.searchWeatherDataForLocation(city.latitude, city.longitude)
                }
            }
        }

    private val preferencesLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel.refresh()
            }
        }

}