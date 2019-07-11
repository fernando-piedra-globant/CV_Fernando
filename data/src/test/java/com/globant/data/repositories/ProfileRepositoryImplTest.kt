package com.globant.data.repositories

import com.globant.data.repositories.entities.GithubContentEntity
import com.globant.data.repositories.entities.GithubEntity
import com.globant.data.repositories.repositories.ProfileRepositoryImpl
import com.globant.data.repositories.source.CVApi
import com.globant.domain.model.Profile
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

class ProfileRepositoryImplTest {

    companion object {
        private val profile =
            Profile(
                name = "Pedro García",
                grade = "Contador",
                address = "Tlalpan, CDMX",
                email = "pedro.garcía@fakecompany.com",
                phone = "55 55555555"
            )

        private const val bodyProfile =
            "{\n  \"name\": \"Pedro García\",\n  \"grade\": \"Contador\",\n  \"address\" : \"Tlalpan, CDMX\",\n  \"email\" : \"pedro.garcía@fakecompany.com\",\n  \"phone\" : \"55 55555555\"\n}"
    }

    private var gson = GsonBuilder().create()

    @Mock
    private lateinit var api: CVApi

    private lateinit var profileRepositoryImpl: ProfileRepositoryImpl

    @Before
    fun setup() {
        profileRepositoryImpl = ProfileRepositoryImpl(gson, api)
    }

    @Test
    fun `it should execute get profile information from repository`() {

        whenever(api.getProfile()).thenReturn(
            Single.just(
                Response.success(
                    GithubEntity(
                        files = GithubContentEntity(
                            GithubFileEntity(bodyProfile)
                        )
                    )
                )
            )
        )

        val profileResponse = profileRepositoryImpl.getProfile().test().assertComplete().values()[0]
        Assert.assertEquals(profile.name, profileResponse.name)
        Assert.assertEquals(profile.grade, profileResponse.grade)
        Assert.assertEquals(profile.address, profileResponse.address)
        Assert.assertEquals(profile.email, profileResponse.email)
        Assert.assertEquals(profile.phone, profileResponse.phone)
    }

    @Test
    fun `it should get a Exception`() {
        whenever(api.getProfile()).thenReturn(
            Single.just(
                Response.error(400, ResponseBody.create(MediaType.parse("application/json"), "{}"))
            )
        )
        val testObserver = profileRepositoryImpl.getProfile().test().assertError(Exception::class.java)

        Assert.assertEquals("No data received", testObserver.errors()[0].message)
    }
}