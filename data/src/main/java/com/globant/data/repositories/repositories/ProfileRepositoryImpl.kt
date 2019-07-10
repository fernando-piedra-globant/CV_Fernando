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

            try {
                val response = api.getProfile().execute()

                if (response.isSuccessful) {
                    val profile: Profile =
                        gson.fromJson(response.body()?.files?.rootFile?.content, Profile::class.java)
                    emitter.onSuccess(profile)
                } else {
                    emitter.onError(Exception("No data received"))
                }
            } catch (e: java.lang.Exception) {
                emitter.onError(Exception("No data received"))
            }
        }
    }
}