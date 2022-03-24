package com.maad.recruitment.recruiter

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maad.recruitment.R
import com.maad.recruitment.jobseeker.SeekerModel

class SeekersAdapter(val activity: Activity, val seekers: ArrayList<SeekerModel>) :
    RecyclerView.Adapter<SeekersAdapter.SeekersVH>() {

    class SeekersVH(view: View): RecyclerView.ViewHolder(view) {
        val image: ImageView= view.findViewById(R.id.seeker_iv)
        val name: TextView= view.findViewById(R.id.seeker_name_tv)
        val role: TextView= view.findViewById(R.id.seeker_role_tv)
        val experience: TextView= view.findViewById(R.id.seeker_experience_tv)
        val mobile: TextView= view.findViewById(R.id.seeker_mobile_tv)
        val email:TextView = view.findViewById(R.id.seeker_email_tv)
        val cv: TextView= view.findViewById(R.id.seeker_cv_tv)
    }

    override fun onBindViewHolder(holder: SeekersVH, position: Int) {
        Glide.with(activity).load(seekers[position].picture).into(holder.image)
        holder.name.text = seekers[position].name
        holder.role.text = "(${seekers[position].track})"
        holder.experience.text = seekers[position].experience
        holder.mobile.text = seekers[position].number
        holder.email.text = seekers[position].email
        holder.mobile.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, "tel:${seekers[position].number}".toUri())
            activity.startActivity(intent)
        }
        holder.email.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, "mailto:".toUri())
            intent.putExtra(Intent.EXTRA_EMAIL, seekers[position].email)
            activity.startActivity(intent)
        }
        holder.cv.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, seekers[position].cv.toUri())
            activity.startActivity(intent)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeekersVH {
        val v = activity.layoutInflater.inflate(R.layout.seeker_list_item, parent, false)
        return SeekersVH(v)
    }

    override fun getItemCount() = seekers.size


}