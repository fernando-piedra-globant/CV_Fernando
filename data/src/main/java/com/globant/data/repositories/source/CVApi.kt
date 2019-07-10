package com.globant.data.repositories.source

import com.globant.data.repositories.entities.GithubEntity
import retrofit2.Call
import retrofit2.http.GET


interface CVApi {

    companion object {
        private const val CV_PROFILE = "gists/4c520d8edceb33054cde65ba09852eb4"
        private const val CV_EXPERIENCE = "gists/9ea2244b4f2055dadb18385a3f83b7f5"
        private const val CV_SKILLS = "gists/5496357e77da74b7ef68e0a0a4443126"
    }

    @GET(CV_PROFILE)
    fun getProfile(): Call<GithubEntity>

    @GET(CV_EXPERIENCE)
    fun getExperience(): Call<GithubEntity>

    @GET(CV_SKILLS)
    fun getSkills(): Call<GithubEntity>
}