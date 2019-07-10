package com.globant.domain.interactor.usecases

import com.globant.domain.excecutor.InmediateThreadExecutor
import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.model.Experience
import com.globant.domain.repositories.ExperienceRepository
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

class ExperienceUseCaseTest {

    companion object {
        private val experience = listOf(
            Experience(
                job = "Job1",
                company = "FakeCompany1",
                range = "2010 - 2011",
                description = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium"
            ),
            Experience(
                job = "Job2",
                company = "FakeCompany2",
                range = "2010 - 2014",
                description = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium"
            ),
            Experience(
                job = "Job3",
                company = "FakeCompany3",
                range = "2010 - 2017",
                description = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium"
            ),
            Experience(
                job = "Job4",
                company = "FakeCompany4",
                range = "2010 - 2019",
                description = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium"
            )
        )
    }

    @Mock
    private lateinit var repository: ExperienceRepository

    @Mock
    private lateinit var postExecutionThread: PostExecutionThread

    private lateinit var experienceUseCase: ExperienceUseCase

    @Before
    fun setup() {
        whenever(postExecutionThread.scheduler).thenReturn(Schedulers.trampoline())
        experienceUseCase = ExperienceUseCase(repository, InmediateThreadExecutor(), postExecutionThread)
    }

    @Test
    fun `it should execute get profile information`() {
        whenever(repository.getExperience()).thenReturn(Single.just(experience))
        val response = experienceUseCase.execute().test().assertComplete().values()[0]
        val experienceItem = experience[2]
        Assert.assertEquals(experienceItem.job, response[2].job)
        Assert.assertEquals(experienceItem.company, response[2].company)
        Assert.assertEquals(experienceItem.range, response[2].range)
        Assert.assertEquals(experienceItem.description, response[2].description)
    }

    @Test
    fun `it should get a Exception`() {
        whenever(repository.getExperience()).thenReturn(Single.error(Exception("No data")))
        val testObserver = experienceUseCase.execute().test().assertError(Exception::class.java)
        Assert.assertEquals("No data", testObserver.errors()[0].message)
    }
}