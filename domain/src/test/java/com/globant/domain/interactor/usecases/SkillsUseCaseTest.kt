package com.globant.domain.interactor.usecases

import com.globant.domain.excecutor.InmediateThreadExecutor
import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.model.Skill
import com.globant.domain.repositories.ExperienceRepository
import com.globant.domain.repositories.SkillsRepository
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

class SkillsUseCaseTest {

    companion object {
        private val skills = listOf(
            Skill(
                name = "Java",
                proficiency = 100
            ),
            Skill(
                name = "Node Js",
                proficiency = 30
            ),
            Skill(
                name = ".Net",
                proficiency = 70
            ),
            Skill(
                name = "AWS",
                proficiency = 67
            ),
            Skill(
                name = "Swift",
                proficiency = 88
            )
        )
    }

    @Mock
    private lateinit var repository: SkillsRepository

    @Mock
    private lateinit var postExecutionThread: PostExecutionThread

    private lateinit var skillsUseCase: SkillsUseCase

    @Before
    fun setup() {
        whenever(postExecutionThread.scheduler).thenReturn(Schedulers.trampoline())
        skillsUseCase = SkillsUseCase(repository, InmediateThreadExecutor(), postExecutionThread)
    }

    @Test
    fun `it should execute get profile information`() {
        whenever(repository.getSkills()).thenReturn(Single.just(skills))
        val response = skillsUseCase.execute().test().assertComplete().values()[0]
        val skill = skills[2]
        Assert.assertEquals(skill.name, response[2].name)
        Assert.assertEquals(skill.proficiency, response[2].proficiency)
    }

    @Test
    fun `it should get a Exception`() {
        whenever(repository.getSkills()).thenReturn(Single.error(Exception("No data")))
        val testObserver = skillsUseCase.execute().test().assertError(Exception::class.java)
        Assert.assertEquals("No data", testObserver.errors()[0].message)
    }
}