package com.globant.cv_fpv.view_model

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.cv_fpv.model.ProfileModel
import com.globant.domain.executor.InmediateThreadExecutor
import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.executor.ThreadExecutor
import com.globant.domain.interactor.usecases.ProfileUseCase
import com.globant.domain.model.Profile
import com.globant.domain.repositories.ProfileRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)

class ProfileViewModelTest {


    companion object {
        private val profile =
            Profile(
                name = "Pedro García",
                grade = "Contador",
                address = "Tlalpan, CDMX",
                email = "pedro.garcía@fakecompany.com",
                phone = "55 55555555"
            )

        private val profileModel =
            ProfileModel(
                name = "Pedro García",
                grade = "Contador",
                address = "Tlalpan, CDMX",
                email = "pedro.garcía@fakecompany.com",
                phone = "55 55555555"
            )
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: ProfileRepository
    @Mock
    private lateinit var postThreadExecutor: PostExecutionThread

    private val threadExecutor: ThreadExecutor = InmediateThreadExecutor()

    private lateinit var useCase: ProfileUseCase

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun before() {
        Mockito.`when`(postThreadExecutor.scheduler).thenReturn(Schedulers.trampoline())
        useCase = ProfileUseCase(
            repository,
            threadExecutor,
            postThreadExecutor
        )
        viewModel = ProfileViewModel(useCase)
    }

    @Test
    fun `it should load  skills from view model`() {
        Mockito.`when`(repository.getProfile()).thenReturn(Single.just(profile))
        viewModel.loadProfile()
        assertEquals(
            viewModel.getProfile().value,
            profileModel
        )
    }
}