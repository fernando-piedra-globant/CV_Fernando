package com.globant.data.repositories.repositories

import com.globant.data.repositories.source.CVApi
import com.globant.domain.model.Experience
import com.globant.domain.model.Profile
import com.globant.domain.repositories.ExperienceRepository
import com.globant.domain.repositories.ProfileRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import io.reactivex.SingleEmitter
import javax.inject.Inject

class ExperienceRepositoryImpl @Inject constructor(val gson: Gson, val api: CVApi) : ExperienceRepository {

    override fun getExperience(): Single<List<Experience>> {

        return Single.create<List<Experience>> { emitter: SingleEmitter<List<Experience>> ->

            try {
                val response = api.getSkills().execute()

                if (response.isSuccessful) {
                    val experience: List<Experience> =
                        gson.fromJson(response.body()?.files?.rootFile?.content, object : TypeToken<List<Experience>>() {}.type)
                    emitter.onSuccess(experience)
                } else {
                    emitter.onError(Exception("No data received"))
                }
            }catch (e: java.lang.Exception){
                emitter.onError(Exception("No data received"))
            }
        }
    }
}