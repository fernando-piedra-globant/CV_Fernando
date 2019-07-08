package com.globant.cv_fpv.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.globant.cv_fpv.R
import com.globant.cv_fpv.model.Profile
import com.globant.cv_fpv.view.base.BaseFragment
import com.globant.cv_fpv.view_model.ProfileViewModel
import io.reactivex.Single
import io.reactivex.SingleEmitter
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        viewModel.profile.observe(this, Observer<Profile> { profile ->
            name_text.text = profile?.name
            grade_text.text = profile?.grade
            address_text.text = profile?.address
            email_text.text = profile?.email
            phone_text.text = profile?.phone
        })

        viewModel.loadProfile()


    }

}
