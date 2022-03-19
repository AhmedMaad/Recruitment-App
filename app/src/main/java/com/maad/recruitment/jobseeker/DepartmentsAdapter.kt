package com.maad.recruitment.jobseeker

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maad.recruitment.R
import com.maad.recruitment.company.department.DepartmentModel

class DepartmentsAdapter(val activity: Activity, val departments: ArrayList<DepartmentModel>) :
    RecyclerView.Adapter<DepartmentsAdapter.DepartmentVH>() {

    class DepartmentVH(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.dept_name_tv)
        val description: TextView = view.findViewById(R.id.dept_description_tv)
        val vision: TextView = view.findViewById(R.id.dept_vision_tv)
        val mission: TextView = view.findViewById(R.id.dept_mission_tv)
        val number: TextView = view.findViewById(R.id.dept_employees_no_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentVH {
        val view = activity.layoutInflater.inflate(R.layout.department_list_item, parent, false)
        return DepartmentVH(view)
    }

    override fun onBindViewHolder(holder: DepartmentVH, position: Int) {
        holder.name.text = departments[position].name
        holder.description.text = departments[position].description
        holder.vision.text = departments[position].vision
        holder.mission.text = departments[position].mission
        holder.number.text = departments[position].employeesNumber.toString()
    }

    override fun getItemCount() = departments.size

}