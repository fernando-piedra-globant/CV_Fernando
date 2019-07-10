package com.globant.cv_fpv.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.globant.cv_fpv.R
import com.globant.cv_fpv.constants.CVConstants.IMAGE_URL
import com.globant.cv_fpv.model.Profile
import com.globant.cv_fpv.navigator.Navigator
import com.globant.cv_fpv.view.base.BaseActivity
import com.globant.cv_fpv.view.base.BaseFragment
import com.globant.cv_fpv.view_model.ProfileViewModel
import kotlinx.android.synthetic.main.profile_fragment.*
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    @Inject
    lateinit var navigator: Navigator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObservers()
        setupViews()
    }

    private fun setupViews() {
        btnExperience.setOnClickListener {
            navigator.addFragment(
                activity as BaseActivity,
                ExperienceFragment.newInstance()
            )
        }

        Glide.with(this)
            .load(IMAGE_URL)
            .centerCrop()
            .placeholder(R.drawable.default_profile)
            .into(img_profile)

    }

    private fun setupObservers() {
        val viewModel = ViewModelProviders.of(this, vmFactory)[ProfileViewModel::class.java]

        viewModel.profile.observe(this, Observer<Profile> { profile ->
            name_text.text = profile?.name
            grade_text.text = profile?.grade
            address_text.text = profile?.address
            email_text.text = profile?.email
            phone_text.text = profile?.phone
        })

        viewModel.isLoading.observe(this, Observer {
            showLoading(it ?: false)
        })

        viewModel.isError.observe(this, Observer {
            showError(it ?: false)
        })

        viewModel.loadProfile()
    }

    private fun showLoading(show: Boolean) {
        fm_loading.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun showError(show: Boolean) {
        fm_error.visibility = if (show) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

}
