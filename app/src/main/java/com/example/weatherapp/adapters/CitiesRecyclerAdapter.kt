package com.example.weatherapp.adapters

import android.content.Context
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.example.weatherapp.R
import com.example.weatherapp.models.City


class CitiesRecyclerAdapter(var context: Context, var onListItemSelectedListener: OnListItemSelectedListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var cities: List<City> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_city_list_item, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CityViewHolder).bind(cities[position])
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun setCities(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tvTitle: TextView
        var favoriteBtn: ImageView

        fun bind(city: City) {
            tvTitle.text = "${city.name}/ ${city.country}"

            if(city.isFavorite == 0){
                favoriteBtn.tag = "NOT_FAVORITE"
                favoriteBtn.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.outline_star_border_white_24, null))
            }
            else{
                favoriteBtn.tag = "FAVORITE"
                favoriteBtn.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.outline_star_white_24, null))
            }

        }

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            favoriteBtn = itemView.findViewById(R.id.favoriteBtn)
            itemView.setOnClickListener(this)
            favoriteBtn.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {

            if(p0?.id == R.id.favoriteBtn){
                var city = cities[layoutPosition]
                if(p0.tag.equals("FAVORITE")){
                    city.isFavorite = 0
                    onListItemSelectedListener.onListItemUnFavoriteClicked(city)
                    favoriteBtn.tag = "NOT_FAVORITE"
                    favoriteBtn.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.outline_star_border_white_24, null))
                }

                else{
                    city.isFavorite = 1
                    onListItemSelectedListener.onListItemFavoriteClicked(city)
                    favoriteBtn.tag = "FAVORITE"
                    favoriteBtn.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.outline_star_white_24, null))
                }



            }

            else
                onListItemSelectedListener.onListItemSelected(cities[layoutPosition])
        }
    }

    interface OnListItemSelectedListener{
        fun onListItemSelected(city: City)
        fun onListItemFavoriteClicked(city: City)
        fun onListItemUnFavoriteClicked(city: City)
    }
}