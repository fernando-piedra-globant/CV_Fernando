package com.globant.domain.repositories

import com.globant.domain.model.Experience
import io.reactivex.Single

interface ExperienceRepository {
    fun getExperience(): Single<List<Experience>>
}