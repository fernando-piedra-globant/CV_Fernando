package com.globant.data.repositories.repositories

import com.globant.data.repositories.source.CVApi
import com.globant.domain.model.Experience
import com.globant.domain.model.Profile
import com.globant.domain.repositories.ExperienceRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import io.reactivex.SingleEmitter
import javax.inject.Inject

class ExperienceRepositoryImpl @Inject constructor(val gson: Gson, val api: CVApi) : ExperienceRepository {

    override fun getExperience(): Single<List<Experience>> {

        return api.getExperience().flatMap { response ->
            if (response.isSuccessful) {
                val experience: List<Experience> =
                    gson.fromJson(
                        response.body()?.files?.rootFile?.content,
                        object : TypeToken<List<Experience>>() {}.type
                    )
                Single.just(experience)
            } else {
                Single.error(Exception("No data received"))
            }
        }
    }
}