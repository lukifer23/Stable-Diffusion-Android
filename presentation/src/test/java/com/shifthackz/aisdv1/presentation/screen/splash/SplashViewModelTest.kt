package com.shifthackz.aisdv1.presentation.screen.splash

import com.shifthackz.aisdv1.domain.usecase.splash.SplashNavigationUseCase
import com.shifthackz.aisdv1.presentation.core.CoreViewModelInitializeStrategy
import com.shifthackz.aisdv1.presentation.core.CoreViewModelTest
import com.shifthackz.aisdv1.presentation.model.LaunchSource
import com.shifthackz.aisdv1.presentation.navigation.router.main.MainRouter
import com.shifthackz.aisdv1.presentation.stub.stubDispatchersProvider
import com.shifthackz.aisdv1.presentation.stub.stubSchedulersProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Ignore
import org.junit.Test

@Ignore("ToDo: Investigate why sometimes tests fail on remote worker due to race-conditions.")
class SplashViewModelTest : CoreViewModelTest<SplashViewModel>() {

    private val stubMainRouter = mockk<MainRouter>()
    private val stubSplashNavigationUseCase = mockk<SplashNavigationUseCase>()

    override val testViewModelStrategy = CoreViewModelInitializeStrategy.InitializeEveryTime

    override fun initializeViewModel() = SplashViewModel(
        mainRouter = stubMainRouter,
        splashNavigationUseCase = stubSplashNavigationUseCase,
        dispatchersProvider = stubDispatchersProvider,
        schedulersProvider = stubSchedulersProvider,
    )

    @Test
    fun `given initialized, use case emits LAUNCH_ONBOARDING action, expected nothing happens`() {
        every {
            stubSplashNavigationUseCase()
        } returns Single.just(SplashNavigationUseCase.Action.LAUNCH_ONBOARDING)

        viewModel.hashCode()

        verify(inverse = true) {
            stubMainRouter.navigateToServerSetup(LaunchSource.SPLASH)
        }
        verify(inverse = true) {
            stubMainRouter.navigateToPostSplashConfigLoader()
        }
    }

    @Test
    fun `given initialized, use case emits LAUNCH_SERVER_SETUP action, expected router navigateToServerSetup() method called`() {
        every {
            stubMainRouter.navigateToServerSetup(any())
        } returns Unit

        every {
            stubSplashNavigationUseCase()
        } returns Single.just(SplashNavigationUseCase.Action.LAUNCH_SERVER_SETUP)

        viewModel.hashCode()

        verify {
            stubMainRouter.navigateToServerSetup(
                LaunchSource.SPLASH
            )
        }
    }

    @Test
    fun `given initialized, use case emits LAUNCH_HOME action, expected router navigateToServerSetup() method called`() {
        every {
            stubMainRouter.navigateToPostSplashConfigLoader()
        } returns Unit

        every {
            stubSplashNavigationUseCase()
        } returns Single.just(SplashNavigationUseCase.Action.LAUNCH_HOME)

        viewModel.hashCode()

        verify {
            stubMainRouter.navigateToPostSplashConfigLoader()
        }
    }
}
