package com.globant.cv_fpv.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globant.cv_fpv.R
import com.globant.cv_fpv.model.SkillModel
import kotlinx.android.synthetic.main.skill_item.view.*

class SkillsAdapter(val items: List<SkillModel>) : RecyclerView.Adapter<SkillsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items.get(position))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.skill_item, parent, false))
    }

    class ViewHolder(internal var view: View) : RecyclerView.ViewHolder(view) {
        fun bind(skill: SkillModel) {
            view.skill_text.text = skill.name
            view.proficiency_progress.progress = skill.proficiency
        }
    }
}