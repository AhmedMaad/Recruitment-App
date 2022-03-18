package com.maad.recruitment.company.employee

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class EmployeeModel(
    val image: String = "",
    val name: String = "",
    val role: String = "",
    val funQuote: String = "",
    val companyId: String = ""
) : Parcelable