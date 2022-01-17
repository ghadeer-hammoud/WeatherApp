package com.example.weatherapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.Resource
import com.example.weatherapp.adapters.CitiesRecyclerAdapter
import com.example.weatherapp.models.City
import com.example.weatherapp.utils.ViewModelProviderFactory
import com.example.weatherapp.viewmodels.SearchCitiesViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SearchCitiesActivity : DaggerAppCompatActivity(), CitiesRecyclerAdapter.OnListItemSelectedListener{

    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvHint: TextView
    private lateinit var recyclerView: RecyclerView
    private var adapter: CitiesRecyclerAdapter = CitiesRecyclerAdapter(this, this)

    private  lateinit var viewModel: SearchCitiesViewModel
    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_cities)

        searchView = findViewById(R.id.searchView)
        progressBar = findViewById(R.id.progressBar)
        tvHint = findViewById(R.id.tvHint)
        recyclerView = findViewById(R.id.recyclerView)

        init()
    }

    private fun init(){

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProviders.of(this, providerFactory).get(SearchCitiesViewModel::class.java)
        subscribeObservers()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                if(query.isEmpty())
                    return false

                showProgressBar()
                hideHintText()
                viewModel.searchCities(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return true
            }
        })
    }

    private fun subscribeObservers(){

        viewModel.observeCitiesList().observe(this,
            { resourceCitiesList ->
                if(resourceCitiesList != null){

                    when(resourceCitiesList.status){

                        Resource.Status.LOADING -> {
                            showProgressBar()
                            hideHintText()
                        }
                        Resource.Status.SUCCESS -> {
                            hideProgressBar()
                            hideHintText()
                            adapter.setCities(resourceCitiesList.data!!)
                        }
                        Resource.Status.ERROR -> {
                            hideProgressBar()
                            showHintText(resourceCitiesList.message!!)
                        }
                    }
                }
            })

        viewModel.observeMessage().observe(this,
            { message ->
                if(message != null)
                    Toast.makeText(this@SearchCitiesActivity, message, Toast.LENGTH_SHORT).show()
            })


    }

    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        progressBar.visibility = View.GONE
    }

    private fun showHintText(string: String){
        tvHint.visibility = View.VISIBLE
        tvHint.text = string
    }

    private fun hideHintText(){
        tvHint.visibility = View.GONE
    }

    override fun onListItemSelected(city: City) {

        var intent = Intent()
        intent.putExtra("city", city)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onListItemFavoriteClicked(city: City) {

        viewModel.addFavoriteCity(city)
    }

    override fun onListItemUnFavoriteClicked(city: City){
        viewModel.removeFavoriteCity(city)
    }
}