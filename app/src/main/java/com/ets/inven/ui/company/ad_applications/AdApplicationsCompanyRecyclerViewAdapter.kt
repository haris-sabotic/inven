package com.ets.inven.ui.company.ad_applications

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ets.inven.R
import com.ets.inven.models.AdPreviewModel
import com.ets.inven.models.UserModel
import com.ets.inven.util.setPhoto
import com.ets.inven.util.shorten

class AdApplicationsCompanyRecyclerViewAdapter (
    private val context: Context,
    private val dataset: ArrayList<UserModel>,
    private val onClickAction: (UserModel) -> Unit
) : RecyclerView.Adapter<AdApplicationsCompanyRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root: ConstraintLayout
        val photo: ImageView
        val textName: TextView
        val textAbout: TextView

        init {
            root = view.findViewById(R.id.item_ad_application_root)
            photo = view.findViewById(R.id.item_ad_application_image)
            textName = view.findViewById(R.id.item_ad_application_text_name)
            textAbout = view.findViewById(R.id.item_ad_application_text_about)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ad_application, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = dataset[position]

        viewHolder.textName.text = user.name
        viewHolder.textAbout.text = user.about?.shorten(80)

        setPhoto(context, user.photo, viewHolder.photo)

        viewHolder.root.setOnClickListener {
            onClickAction(user)
        }
    }

    override fun getItemCount() = dataset.size

}