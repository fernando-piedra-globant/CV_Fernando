package com.globant.data.repositories.repositories

import com.globant.data.repositories.source.CVApi
import com.globant.domain.model.Profile
import com.globant.domain.repositories.ProfileRepository
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.SingleEmitter
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(val gson: Gson, val api: CVApi) : ProfileRepository {

    override fun getProfile(): Single<Profile> {

        return Single.create<Profile> { emitter: SingleEmitter<Profile> ->

            val profile: Profile =
                gson.fromJson(api.getProfile().execute().body()?.files?.rootFile?.content, Profile::class.java)
            emitter.onSuccess(profile)

            /*
            if (shouldUpdate(page, forced)) {
                loadUsersFromNetwork(page, emitter)
            } else {
                loadOfflineUsers(page, emitter)
            }
            */
        }
    }
}