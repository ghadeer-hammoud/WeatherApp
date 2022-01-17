package com.example.weatherapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.Resource
import com.example.weatherapp.adapters.CitiesRecyclerAdapter
import com.example.weatherapp.models.City
import com.example.weatherapp.utils.ViewModelProviderFactory
import com.example.weatherapp.viewmodels.FavoriteCitiesViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class FavoriteCitiesActivity : DaggerAppCompatActivity(), CitiesRecyclerAdapter.OnListItemSelectedListener{

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private var adapter: CitiesRecyclerAdapter = CitiesRecyclerAdapter(this, this)

    private var citiesList: ArrayList<City> = ArrayList<City>()

    private  lateinit var viewModel: FavoriteCitiesViewModel
    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_cities)

        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)

        init()
    }

    private fun init(){

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProviders.of(this, providerFactory).get(FavoriteCitiesViewModel::class.java)
        subscribeObservers()

    }

    private fun subscribeObservers(){

        viewModel.observeFavoriteCitiesList().observe(this,
            { resourceCitiesList ->
                if(resourceCitiesList != null){

                    when(resourceCitiesList.status){

                        Resource.Status.LOADING -> {
                            showProgressBar()
                        }
                        Resource.Status.SUCCESS -> {
                            hideProgressBar()
                            citiesList.clear()
                            citiesList.addAll(resourceCitiesList.data!!)
                            adapter.setCities(citiesList)
                            if(citiesList.size == 0)
                                Toast.makeText(this@FavoriteCitiesActivity, "No favorite cities", Toast.LENGTH_SHORT).show()
                        }
                        Resource.Status.ERROR -> {
                            hideProgressBar()
                        }
                    }
                }
            })

        viewModel.observeMessage().observe(this,
            { message ->
                if(message != null)
                    Toast.makeText(this@FavoriteCitiesActivity, message, Toast.LENGTH_SHORT).show()
            })

    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        progressBar.visibility = View.GONE
    }

    override fun onListItemSelected(city: City) {

        var intent = Intent()
        intent.putExtra("city", city)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onListItemFavoriteClicked(city: City) {

    }

    override fun onListItemUnFavoriteClicked(city: City) {
        viewModel.removeFavoriteCity(city)
    }
}