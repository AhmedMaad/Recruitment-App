package com.maad.recruitment.company.job

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class JobModel(
    val title: String = "",
    val description: String = "",
    val benefits: String = "",
    val salary: String = "",
    val negotiable: Boolean = false,
    val companyId: String = ""
) : Parcelable