package com.maad.recruitment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AvailableCompaniesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_companies)
        title = "Companies"

        //make recycler view that shows companies using joneer list item
        //with logo, name, about with (see more..)

    }
}