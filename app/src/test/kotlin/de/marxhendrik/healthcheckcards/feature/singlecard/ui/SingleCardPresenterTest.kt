package de.marxhendrik.healthcheckcards.feature.singlecard.ui

import com.jakewharton.rxrelay2.PublishRelay
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import de.marxhendrik.healthcheckcards.base.ViewLifecycle
import de.marxhendrik.testbase.BaseTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class SingleCardPresenterTest : BaseTest() {


    private lateinit var presenter: SingleCardPresenter

    private val view: SingleCardView = mock()
    private val animationCommandRelay = PublishRelay.create<SingleCardAnimationCommand>()
    private val viewLifecycle: ViewLifecycle = mock()

    @Before
    override fun setup() {
        super.setup()
        presenter = SingleCardPresenter(animationCommandRelay, view, viewLifecycle)
    }

    @After
    override fun teardown() {
        super.teardown()
    }

    @Test
    fun `construct presenter - manage lifecycle`() {
        verify(viewLifecycle).register(eq(presenter))
    }

    @Test
    fun `receive command - this index - uncentered - center card`() {
        val index = 0
        whenever(view.getIndex()).thenReturn(index)

        presenter.onStart()
        animationCommandRelay.accept(SingleCardAnimationCommand(index))

        verify(view).animateToFront()
        verify(view).animateCenter()
    }

    @Test
    fun `receive command - this index - centered - uncenter card`() {
        val index = 0
        whenever(view.getIndex()).thenReturn(index)

        presenter.onStart()
        //center card
        animationCommandRelay.accept(SingleCardAnimationCommand(index))
        //uncenter card
        animationCommandRelay.accept(SingleCardAnimationCommand(index))

        verify(view).animateToOriginalX()
        verify(view).animateToBack()
    }

    @Test
    fun `receive command - not this index - uncenter card`() {
        val index = 0
        val commandIndex = 1
        whenever(view.getIndex()).thenReturn(index)

        presenter.onStart()
        animationCommandRelay.accept(SingleCardAnimationCommand(commandIndex))

        verify(view).animateToOriginalX()
        verify(view).animateToBack()
    }

}
