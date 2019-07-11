package com.globant.data.repositories

import com.globant.data.repositories.entities.GithubContentEntity
import com.globant.data.repositories.entities.GithubEntity
import com.globant.data.repositories.repositories.ExperienceRepositoryImpl
import com.globant.data.repositories.source.CVApi
import com.globant.domain.model.Experience
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

class ExperienceRepositoryImplTest {

    companion object {
        private val experience = listOf(
            Experience(
                job = "Job1",
                company = "FakeCompany1",
                range = "2010 - 2011",
                description = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium"
            )
        )
        private const val bodyExperience = "  [{\n" +
                "    \"job\" : \"Job1\",\n" +
                "    \"company\" : \"FakeCompany1\",\n" +
                "    \"range\" : \"2010 - 2011\",\n" +
                "    \"description\": \"At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium\"\n" +
                "  }]"
    }

    private var gson = GsonBuilder().create()

    @Mock
    private lateinit var api: CVApi

    private lateinit var experienceRepositoryImpl: ExperienceRepositoryImpl

    @Before
    fun setup() {
        experienceRepositoryImpl = ExperienceRepositoryImpl(gson, api)
    }

    @Test
    fun `it should execute get experience information from repository`() {

        whenever(api.getExperience()).thenReturn(
            Single.just(
                Response.success(
                    GithubEntity(
                        files = GithubContentEntity(
                            GithubFileEntity(bodyExperience)
                        )
                    )
                )
            )
        )

        val response = experienceRepositoryImpl.getExperience().test().assertComplete().values()[0]
        val experienceItem = response[0]
        Assert.assertEquals(experience[0].job, experienceItem.job)
        Assert.assertEquals(experience[0].company, experienceItem.company)
        Assert.assertEquals(experience[0].range, experienceItem.range)
        Assert.assertEquals(experience[0].description, experienceItem.description)
    }

    @Test
    fun `it should get a Exception`() {
        whenever(api.getExperience()).thenReturn(
            Single.just(
                Response.error(400, ResponseBody.create(MediaType.parse("application/json"), "{}"))
            )
        )
        val testObserver = experienceRepositoryImpl.getExperience().test().assertError(Exception::class.java)

        Assert.assertEquals("No data received", testObserver.errors()[0].message)
    }
}