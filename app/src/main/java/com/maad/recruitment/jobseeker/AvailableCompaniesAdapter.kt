package com.maad.recruitment.jobseeker

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maad.recruitment.R
import com.maad.recruitment.company.CompanyModel

class AvailableCompaniesAdapter(val activity: Activity, val companies: ArrayList<CompanyModel>) :
    RecyclerView.Adapter<AvailableCompaniesAdapter.CompaniesViewHolder>() {

    class CompaniesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val logo: ImageView = view.findViewById(R.id.company_logo_iv)
        val about: TextView = view.findViewById(R.id.about_company_tv)
        val name: TextView = view.findViewById(R.id.name_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompaniesViewHolder {
        val v = activity.layoutInflater.inflate(R.layout.company_list_item, parent, false)
        return CompaniesViewHolder(v)
    }

    override fun onBindViewHolder(holder: CompaniesViewHolder, position: Int) {
        holder.about.text = companies[position].details
        holder.name.text = companies[position].name
        Glide.with(activity).load(companies[position].image).into(holder.logo)
    }

    override fun getItemCount() = companies.size
}