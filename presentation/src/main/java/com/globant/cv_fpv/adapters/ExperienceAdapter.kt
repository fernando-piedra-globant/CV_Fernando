package com.globant.cv_fpv.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globant.cv_fpv.R
import com.globant.cv_fpv.model.Experience
import kotlinx.android.synthetic.main.experience_item.view.*

class ExperienceAdapter(val items: List<Experience>) : RecyclerView.Adapter<ExperienceAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items.get(position))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.experience_item, parent, false))
    }

    class ViewHolder(internal var view: View) : RecyclerView.ViewHolder(view) {
        fun bind(experience: Experience) {
            view.job_text.text = experience.job
            view.company_text.text = experience.company
            view.range_text.text = experience.range
            view.description_text.text = experience.description
        }
    }

}