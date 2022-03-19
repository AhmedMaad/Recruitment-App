package com.maad.recruitment.jobseeker

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maad.recruitment.R
import com.maad.recruitment.company.CompanyModel

class AvailableCompaniesAdapter(
    private val activity: Activity,
    private val companies: ArrayList<CompanyModel>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<AvailableCompaniesAdapter.CompaniesViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int, image: ImageView, name: TextView)
    }

    class CompaniesViewHolder(view: View, onItemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(view) {
        val logo: ImageView = view.findViewById(R.id.company_logo_iv)
        val about: TextView = view.findViewById(R.id.about_company_tv)
        val name: TextView = view.findViewById(R.id.name_tv)

        init {
            view.setOnClickListener {
                onItemClickListener.onItemClick(adapterPosition, logo, name)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompaniesViewHolder {
        val v = activity.layoutInflater.inflate(R.layout.company_list_item, parent, false)
        return CompaniesViewHolder(v, onItemClickListener)
    }

    override fun onBindViewHolder(holder: CompaniesViewHolder, position: Int) {
        holder.about.text = companies[position].details
        holder.name.text = companies[position].name
        Glide.with(activity).load(companies[position].image).into(holder.logo)
        //Transition name has to be unique
        ViewCompat.setTransitionName(holder.logo, companies[position].image)
        ViewCompat.setTransitionName(holder.name, companies[position].id)
    }

    override fun getItemCount() = companies.size
}