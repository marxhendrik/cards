package de.marxhendrik.healthcheckcards.feature.threecards.ui

import com.jakewharton.rxrelay2.PublishRelay
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import de.marxhendrik.healthcheckcards.base.ViewLifecycle
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardAnimationCommand
import de.marxhendrik.testbase.BaseTest
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class ThreeCardsPresenterTest : BaseTest() {

    private lateinit var presenter: ThreeCardsPresenter

    private val view: ThreeCardsView = mock()
    private val animationCommandRelay: PublishRelay<SingleCardAnimationCommand> = mock()
    private val viewLifecycle: ViewLifecycle = mock()

    @Before
    override fun setup() {
        super.setup()
        whenever(view.getClicks()).thenReturn(Observable.empty())
        presenter = ThreeCardsPresenter(view, animationCommandRelay, viewLifecycle)
    }

    @After
    override fun teardown() {
        super.teardown()
    }

    @Test
    fun `create presenter - register lifecycle`() {
        verify(viewLifecycle).register(eq(presenter))
    }

    @Test
    fun `onStart() subscribes to click event`() {
        presenter.onStart()
        verify(view).getClicks()
    }

    @Test
    fun `onStart() - emit click - send command`() {
        whenever(view.getClicks()).thenReturn(Observable.just(1))

        presenter.onStart()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(animationCommandRelay).accept(eq(SingleCardAnimationCommand(1)))
        verify(view).getClicks()
    }


}
