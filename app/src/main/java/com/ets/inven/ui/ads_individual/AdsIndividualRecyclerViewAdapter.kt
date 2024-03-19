package com.ets.inven.ui.ads_individual

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ets.inven.R
import com.ets.inven.models.AdModel
import com.ets.inven.util.shorten

class AdsIndividualRecyclerViewAdapter (
    private val context: Context,
    private val dataset: ArrayList<AdModel>
) : RecyclerView.Adapter<AdsIndividualRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo: ImageView
        val textName: TextView
        val textDescription: TextView

        init {
            photo = view.findViewById(R.id.item_ad_image)
            textName = view.findViewById(R.id.item_ad_text_name)
            textDescription = view.findViewById(R.id.item_ad_text_description)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ad, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val ad = dataset[position]

        viewHolder.textName.text = ad.name
        viewHolder.textDescription.text = ad.description.shorten(105)
    }

    override fun getItemCount() = dataset.size

}