package com.globant.cv_fpv.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globant.cv_fpv.R
import com.globant.cv_fpv.adapters.SkillsAdapter
import com.globant.cv_fpv.model.Skill
import com.globant.cv_fpv.view.base.BaseFragment
import com.globant.cv_fpv.view_model.SkillsViewModel
import kotlinx.android.synthetic.main.experience_fragment.*
import javax.inject.Inject

class SkillsFragment : BaseFragment() {

    companion object {
        fun newInstance() = SkillsFragment()
    }

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.skills_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        val viewModel = ViewModelProviders.of(this, vmFactory)[SkillsViewModel::class.java]

        viewModel.skills.observe(this, Observer<List<Skill>> { skills ->

            val adapter = SkillsAdapter(skills ?: ArrayList())
            recycler.layoutManager = LinearLayoutManager(context)
            recycler.adapter = adapter
        })

        viewModel.loadSkills()
    }

}
