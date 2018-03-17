package de.marxhendrik.healthcheckcards.feature.threecards.ui

import com.nhaarman.mockito_kotlin.*
import de.marxhendrik.healthcheckcards.feature.singlecard.ui.SingleCardContract
import de.marxhendrik.testbase.BaseTest
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class ThreeCardsPresenterTest : BaseTest() {

    private val view = mock<ThreeCardsContract.View>()

    private lateinit var presenter: ThreeCardsPresenter

    @Before
    override fun setup() {
        super.setup()

        whenever(view.getClicks()).thenReturn(Observable.empty())
        presenter = ThreeCardsPresenter(view, this)
    }

    @After
    override fun teardown() {
        super.teardown()
    }

    @Test
    fun `onStart() subscribes to click event`() {
        markStarted()
        verify(view).getClicks()
    }

    @Test
    fun `onStart() - emit click - center card`() {
        val mockCard = mock<SingleCardContract.View>()

        whenever(view.getClicks()).thenReturn(Observable.just(mockCard))

        markStarted()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(mockCard).centered
        verify(mockCard).centered = eq(true)
        verify(view).getClicks()
    }

    @Test
    fun `onStart() - emit click - move out right cards`() {
        val mockCard = mock<SingleCardContract.View>()
        val mockCardRight = mock<SingleCardContract.View>()
        val mockCardRight2 = mock<SingleCardContract.View>()
        val translation = 400f

        whenever(view.cards).thenReturn(setOf(mockCard, mockCardRight, mockCardRight2))
        whenever(view.getClicks()).thenReturn(Observable.just(mockCard))
        whenever(mockCardRight.isToRightOf(eq(mockCard))).thenReturn(true)
        whenever(mockCardRight2.isToRightOf(eq(mockCard))).thenReturn(true)
        whenever(mockCardRight.getOutRightTranslation()).thenReturn(translation)
        whenever(mockCardRight2.getOutRightTranslation()).thenReturn(translation)

        markStarted()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(mockCardRight).isToRightOf(eq(mockCard))
        verify(mockCardRight2).isToRightOf(eq(mockCard))
        verify(mockCardRight).getOutRightTranslation()
        verify(mockCardRight2).getOutRightTranslation()
        verify(view).animateTranslateX(eq(mockCardRight), eq(translation), eq(0), any())
        verify(view).animateTranslateX(eq(mockCardRight2), eq(translation), eq(0), any())
        verify(view).getClicks()
    }

    @Test
    fun `onStart() - emit click - don't move out left cards`() {
        val mockCard = mock<SingleCardContract.View>()
        val mockCardRight = mock<SingleCardContract.View>()
        val mockCardRight2 = mock<SingleCardContract.View>()
        val translation = 400f

        whenever(view.cards).thenReturn(setOf(mockCard, mockCardRight, mockCardRight2))
        whenever(view.getClicks()).thenReturn(Observable.just(mockCard))
        whenever(mockCardRight.isToRightOf(eq(mockCard))).thenReturn(false)
        whenever(mockCardRight2.isToRightOf(eq(mockCard))).thenReturn(true)
        whenever(mockCardRight.getOutRightTranslation()).thenReturn(translation)
        whenever(mockCardRight2.getOutRightTranslation()).thenReturn(translation)

        markStarted()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(mockCardRight).isToRightOf(eq(mockCard))
        verify(mockCardRight2).isToRightOf(eq(mockCard))
        verify(mockCardRight, never()).getOutRightTranslation()
        verify(mockCardRight2).getOutRightTranslation()
        verify(view, never()).animateTranslateX(eq(mockCardRight), eq(translation), eq(0), any())
        verify(view).animateTranslateX(eq(mockCardRight2), eq(translation), eq(0), any())
        verify(view).getClicks()
    }

    @Test
    fun `onStart() - emit click card - center card`() {
        val mockCard = mock<SingleCardContract.View>()

        whenever(view.cards).thenReturn(setOf(mockCard))
        whenever(view.getClicks()).thenReturn(Observable.just(mockCard))

        markStarted()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(view).animateTranslateZ(eq(mockCard), eq(CENTERED_Z_TRANSLATION), eq(ANIMATION_DELAY_MS), any())
        verify(view).animateTranslateX(eq(mockCard), eq(0f), eq(ANIMATION_DELAY_MS), any())
    }

    @Test
    fun `onStart() - emit click  centered card - uncenter card`() {
        val mockCard = mock<SingleCardContract.View>()

        whenever(mockCard.centered).thenReturn(true)
        whenever(view.cards).thenReturn(setOf(mockCard))
        whenever(view.getClicks()).thenReturn(Observable.just(mockCard))

        markStarted()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(view).animateTranslateZ(eq(mockCard), eq(0f), eq(ANIMATION_DELAY_MS), any())
        verify(view).animateTranslateX(eq(mockCard), eq(0f), eq(0), any())
    }

    @Test
    fun `onStart() - emit click  centered card - move cards to right to original x`() {
        val mockCard = mock<SingleCardContract.View>()
        val mockCardRight = mock<SingleCardContract.View>()
        val mockCardRight2 = mock<SingleCardContract.View>()

        whenever(view.cards).thenReturn(setOf(mockCard, mockCardRight, mockCardRight2))
        whenever(view.getClicks()).thenReturn(Observable.just(mockCard))
        whenever(mockCardRight.isToRightOf(eq(mockCard))).thenReturn(true)
        whenever(mockCardRight2.isToRightOf(eq(mockCard))).thenReturn(true)

        markStarted()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(view).animateTranslateX(eq(mockCardRight), eq(0f), eq(ANIMATION_DELAY_MS), any())
        verify(view).animateTranslateX(eq(mockCardRight2), eq(0f), eq(ANIMATION_DELAY_MS), any())
    }
}