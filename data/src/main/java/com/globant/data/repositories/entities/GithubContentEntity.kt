package com.globant.data.repositories.entities

import com.google.gson.annotations.SerializedName
import mx.globant.data.entities.GithubFileEntity

data class GithubContentEntity(
    @SerializedName(value = "profile.json", alternate = ["skills.json", "experience.json"])
    val rootFile: GithubFileEntity
)