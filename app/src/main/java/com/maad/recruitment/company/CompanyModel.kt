package com.maad.recruitment.company

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CompanyModel(
    val image: String = "",
    val name: String = "",
    val email: String = "",
    val details: String = "",
    val number: String = "",
    val location: String = "",
    val id: String = ""
) : Parcelable