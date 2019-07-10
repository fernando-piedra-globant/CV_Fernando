package com.globant.domain.repositories

import com.globant.domain.model.Skill
import io.reactivex.Single

interface SkillsRepository {
    fun getSkills(): Single<List<Skill>>
}