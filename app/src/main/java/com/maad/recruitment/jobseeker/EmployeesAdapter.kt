package com.maad.recruitment.jobseeker

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maad.recruitment.R
import com.maad.recruitment.company.employee.EmployeeModel

class EmployeesAdapter(val activity: Activity, val employees: ArrayList<EmployeeModel>) :
    RecyclerView.Adapter<EmployeesAdapter.EmployeeVH>() {

    class EmployeeVH(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.employee_iv)
        val name: TextView = view.findViewById(R.id.employee_name_tv)
        val role: TextView = view.findViewById(R.id.employee_role_tv)
        val quote: TextView = view.findViewById(R.id.employee_fun_quote_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeVH {
        val view = activity.layoutInflater.inflate(R.layout.employee_list_item, parent, false)
        return EmployeeVH(view)
    }

    override fun onBindViewHolder(holder: EmployeeVH, position: Int) {
        Glide.with(activity).load(employees[position].image).into(holder.image)
        holder.name.text = employees[position].name
        holder.role.text = employees[position].role
        holder.quote.text = employees[position].funQuote
    }

    override fun getItemCount() = employees.size

}