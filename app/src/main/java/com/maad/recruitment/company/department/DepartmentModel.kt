package com.maad.recruitment.company.department

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class DepartmentModel(
    val name: String = "",
    val description: String = "",
    val vision: String = "",
    val mission: String = "",
    val employeesNumber: Int = 0,
    val companyId: String = ""
) : Parcelable