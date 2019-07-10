package com.globant.data.repositories.repositories

import com.globant.data.repositories.source.CVApi
import com.globant.domain.model.Skill
import com.globant.domain.repositories.SkillsRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import javax.inject.Inject

class SkillsRepositoryImpl @Inject constructor(val gson: Gson, val api: CVApi) : SkillsRepository {

    override fun getSkills(): Single<List<Skill>> {

        return api.getSkills().flatMap { response ->
            if (response.isSuccessful) {
                val skills: List<Skill> =
                    gson.fromJson(
                        response.body()?.files?.rootFile?.content,
                        object : TypeToken<List<Skill>>() {}.type
                    )
                Single.just(skills)
            } else {
                Single.error(Exception("No data received"))
            }
        }
    }
}