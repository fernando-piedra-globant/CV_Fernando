package com.globant.domain.repositories

import com.globant.domain.model.Experience
import com.globant.domain.model.Profile
import io.reactivex.Single

interface ExperienceRepository {
    fun getExperience(): Single<List<Experience>>
}