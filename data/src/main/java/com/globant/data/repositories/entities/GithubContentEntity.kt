package mx.globant.data.entities

import com.google.gson.annotations.SerializedName

data class GithubContentEntity(
    @SerializedName(value = "profile.json", alternate = ["skills.json", "experience.json"])
    val rootFile: GithubFileEntity
)