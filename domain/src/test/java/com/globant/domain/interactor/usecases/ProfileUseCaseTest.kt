package com.globant.domain.interactor.usecases

import com.globant.domain.excecutor.InmediateThreadExecutor
import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.model.Profile
import com.globant.domain.repositories.ProfileRepository
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)

class ProfileUseCaseTest {

    companion object {
        private val profile =
            Profile(
                name = "Pedro García",
                grade = "Contador",
                address = "Tlalpan, CDMX",
                email = "pedro.garcía@fakecompany.com",
                phone = "55 55555555"
            )
    }

    @Mock
    private lateinit var repository: ProfileRepository

    @Mock
    private lateinit var postExecutionThread: PostExecutionThread

    private lateinit var profileUseCase: ProfileUseCase

    @Before
    fun setup() {
        whenever(postExecutionThread.scheduler).thenReturn(Schedulers.trampoline())
        profileUseCase = ProfileUseCase(repository, InmediateThreadExecutor(), postExecutionThread)
    }

    @Test
    fun `it should execute get profile information`() {
        whenever(repository.getProfile()).thenReturn(Single.just(profile))
        val response = profileUseCase.execute().test().assertComplete().values()[0]
        Assert.assertEquals(profile.name, response.name)
        Assert.assertEquals(profile.grade, response.grade)
        Assert.assertEquals(profile.address, response.address)
        Assert.assertEquals(profile.email, response.email)
        Assert.assertEquals(profile.phone, response.phone)
    }

    @Test
    fun `it should get a Exception`() {
        whenever(repository.getProfile()).thenReturn(Single.error(Exception("No data")))
        val testObserver = profileUseCase.execute().test().assertError(Exception::class.java)
        Assert.assertEquals("No data", testObserver.errors()[0].message)
    }
}