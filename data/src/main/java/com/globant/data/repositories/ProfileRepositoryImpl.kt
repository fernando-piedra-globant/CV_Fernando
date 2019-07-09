package com.globant.data.repositories

import com.globant.domain.model.Profile
import com.globant.domain.repositories.ProfileRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor() : ProfileRepository {
    override fun getProfile(): Single<Profile> {
        return Single.just(Profile(
            "Fernando",
            "Ingeniero en sistemas",
            "Azcapotzalco, CDMX",
            "fernando.piedra@globant.com",
            "5555555555"
        ))
    }
}