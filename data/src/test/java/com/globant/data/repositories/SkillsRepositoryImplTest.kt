package com.globant.data.repositories

import com.globant.data.repositories.entities.GithubContentEntity
import com.globant.data.repositories.entities.GithubEntity
import com.globant.data.repositories.repositories.SkillsRepositoryImpl
import com.globant.data.repositories.source.CVApi
import com.globant.domain.model.Skill
import com.google.gson.GsonBuilder
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import mx.globant.data.entities.GithubFileEntity
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)

class SkillsRepositoryImplTest {

    companion object {
        private val skills = listOf(
            Skill(
                name = "Java",
                proficiency = 100
            ),
            Skill(
                name = "Node Js",
                proficiency = 30
            )
        )
        private const val bodySkills =
            "[{\n\t\"name\": \"Java\",\n\t\"proficiency\": 100\n}, {\n\t\"name\": \"Node Js\",\n\t\"proficiency\": 30\n}]"
    }

    private var gson = GsonBuilder().create()

    @Mock
    private lateinit var api: CVApi

    private lateinit var skillsRepositoryImpl: SkillsRepositoryImpl

    @Before
    fun setup() {
        skillsRepositoryImpl = SkillsRepositoryImpl(gson, api)
    }

    @Test
    fun `it should execute get skills information from repository`() {

        whenever(api.getSkills()).thenReturn(
            Single.just(
                Response.success(
                    GithubEntity(
                        files = GithubContentEntity(
                            GithubFileEntity(bodySkills)
                        )
                    )
                )
            )
        )

        val response = skillsRepositoryImpl.getSkills().test().assertComplete().values()[0]
        val skill = response[1]
        Assert.assertEquals(skills[1].name, skill.name)
        Assert.assertEquals(skills[1].proficiency, skill.proficiency)
    }

    @Test
    fun `it should get a Exception`() {
        whenever(api.getSkills()).thenReturn(
            Single.just(
                Response.error(400, ResponseBody.create(MediaType.parse("application/json"), "{}"))
            )
        )
        val testObserver = skillsRepositoryImpl.getSkills().test().assertError(Exception::class.java)

        Assert.assertEquals("No data received", testObserver.errors()[0].message)
    }
}