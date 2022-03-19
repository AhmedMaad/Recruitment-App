package com.maad.recruitment.jobseeker

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.maad.recruitment.R
import com.maad.recruitment.company.department.DepartmentModel
import com.maad.recruitment.company.job.JobModel

class JobsAdapter(val activity: Activity, val jobs: ArrayList<JobModel>) :
    RecyclerView.Adapter<JobsAdapter.JobVH>() {

    class JobVH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.job_title_tv)
        val description: TextView = view.findViewById(R.id.job_description_tv)
        val benefits: TextView = view.findViewById(R.id.job_benefits_tv)
        val salary: TextView = view.findViewById(R.id.job_salary_tv)
        val negotiable: TextView = view.findViewById(R.id.negotiable_cb)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobVH {
        val view = activity.layoutInflater.inflate(R.layout.job_list_item, parent, false)
        return JobVH(view)
    }

    override fun onBindViewHolder(holder: JobVH, position: Int) {
        holder.title.text = jobs[position].title
        holder.description.text = jobs[position].description
        holder.benefits.text = jobs[position].benefits
        holder.salary.text = jobs[position].salary
        Log.d("trace", "Negotiable: ${jobs[position].negotiable}")
        if (jobs[position].negotiable)
            holder.negotiable.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                ActivityCompat.getDrawable(activity, R.drawable.ic_checked),
                null
            )
        else
            holder.negotiable.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                ActivityCompat.getDrawable(activity, R.drawable.ic_unchecked),
                null
            )
    }

    override fun getItemCount() = jobs.size

}