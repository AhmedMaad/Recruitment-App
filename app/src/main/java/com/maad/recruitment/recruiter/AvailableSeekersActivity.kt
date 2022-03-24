package com.maad.recruitment.recruiter

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.maad.recruitment.ExitDialog
import com.maad.recruitment.R
import com.maad.recruitment.databinding.ActivityAvailableSeekersBinding
import com.maad.recruitment.jobseeker.SeekerModel

class AvailableSeekersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAvailableSeekersBinding
    private lateinit var seekers: ArrayList<SeekerModel>
    private lateinit var adapter: SeekersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAvailableSeekersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()
        db
            .collection("seekers")
            .get()
            .addOnSuccessListener {
                binding.progress.visibility = View.INVISIBLE
                seekers = it.toObjects(SeekerModel::class.java) as ArrayList<SeekerModel>
                adapter = SeekersAdapter(this, seekers)
                binding.seekersRv.adapter = adapter
            }
            .addOnFailureListener {
                Toast.makeText(this, "Try again later", Toast.LENGTH_SHORT).show()
                binding.progress.visibility = View.INVISIBLE
            }

    }

    override fun onBackPressed() {
        val exit = ExitDialog()
        exit.isCancelable = false
        exit.show(supportFragmentManager, null)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val item = menu?.findItem(R.id.item_search)
        val searchView: SearchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText!!)
                return false
            }
        })
        return true
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredList: ArrayList<SeekerModel> = ArrayList()

        for (item in seekers)
            if (item.track.lowercase().contains(text.lowercase()))
                filteredList.add(item)

        if (filteredList.isEmpty())
            Toast.makeText(this, "No Track Found..", Toast.LENGTH_SHORT).show()
        else
            adapter.filterList(filteredList)
    }

}