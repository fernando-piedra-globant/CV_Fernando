package com.globant.domain.repositories

import com.globant.domain.model.Profile
import io.reactivex.Single

interface ProfileRepository {
    fun getProfile(): Single<Profile>
}