package de.marxhendrik.healthcheckcards.feature.singlecard.ui

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import de.marxhendrik.testbase.BaseTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SingleCardPresenterTest : BaseTest() {


    private lateinit var presenter: SingleCardPresenter

    private val view = mock<SingleCardContract.View>()

    private val screenWidth: Float = 400f
    private val viewLeft: Float = 100f
    private val viewRight: Float = 200f

    @Before
    override fun setup() {
        super.setup()
        presenter = SingleCardPresenter(screenWidth = screenWidth, view = view)
    }


    @After
    override fun teardown() {
        super.teardown()
    }


    @Test
    fun getCentered() {
        presenter.centered = true
        assertEquals(true, presenter.centered)
    }

    @Test
    fun getCenterTranslation() {
        whenever(view.left).thenReturn(viewLeft)
        whenever(view.right).thenReturn(viewRight)
        val translation = presenter.getCenterTranslation()
        assertEquals(screenWidth / 2 - (viewLeft + viewRight) / 2, translation)
        verify(view).left
        verify(view).right
    }


    @Test
    fun `isToRightOf - true`() {
        val view2 = mock<SingleCardContract.View>()
        whenever(view.left).thenReturn(viewLeft)
        whenever(view.right).thenReturn(viewRight)
        whenever(view2.getCenterTranslation()).thenReturn(100f)
        val toRightOf = presenter.isToRightOf(view2)
        verify(view2).getCenterTranslation()
        assertEquals(true, toRightOf)
    }

    @Test
    fun `isToRightOf - false`() {
        val view2 = mock<SingleCardContract.View>()
        whenever(view.left).thenReturn(viewLeft)
        whenever(view.right).thenReturn(viewRight)
        whenever(view2.getCenterTranslation()).thenReturn(-100f)
        val toRightOf = presenter.isToRightOf(view2)
        verify(view2).getCenterTranslation()
        assertEquals(false, toRightOf)
    }


    @Test
    fun getOutRightTranslation() {
        whenever(view.left).thenReturn(viewLeft)
        assertEquals(screenWidth - viewLeft, presenter.getOutRightTranslation())
    }

}