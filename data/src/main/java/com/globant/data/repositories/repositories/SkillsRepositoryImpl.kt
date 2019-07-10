package com.globant.data.repositories.repositories

import com.globant.data.repositories.source.CVApi
import com.globant.domain.model.Experience
import com.globant.domain.model.Skill
import com.globant.domain.repositories.ExperienceRepository
import com.globant.domain.repositories.SkillsRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import io.reactivex.SingleEmitter
import javax.inject.Inject

class SkillsRepositoryImpl @Inject constructor(val gson: Gson, val api: CVApi) : SkillsRepository {

    override fun getSkills(): Single<List<Skill>> {

        return Single.create<List<Skill>> { emitter: SingleEmitter<List<Skill>> ->

            try {
                val response = api.getSkills().execute()

                if (response.isSuccessful) {
                    val skills: List<Skill> =
                        gson.fromJson(
                            response.body()?.files?.rootFile?.content,
                            object : TypeToken<List<Skill>>() {}.type
                        )
                    emitter.onSuccess(skills)
                } else {
                    emitter.onError(Exception("No data received"))
                }
            } catch (e: java.lang.Exception) {
                emitter.onError(Exception("No data received"))
            }
        }
    }
}