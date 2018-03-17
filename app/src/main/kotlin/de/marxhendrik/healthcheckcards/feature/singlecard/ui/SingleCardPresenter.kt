package de.marxhendrik.healthcheckcards.feature.singlecard.ui

class SingleCardPresenter(
        private val screenWidth: Float,
        private val view: SingleCardContract.View) : SingleCardContract.Presenter {
    override var centered: Boolean = false

    override fun getCenterTranslation(): Float {
        val centerX = (view.left + view.right) / 2
        val screenCenter = screenWidth / 2
        return screenCenter - centerX
    }

    override fun isToRightOf(card: SingleCardContract.View) = getCenterTranslation() - card.getCenterTranslation() <= 0

    override fun getOutRightTranslation() = screenWidth - view.left
}