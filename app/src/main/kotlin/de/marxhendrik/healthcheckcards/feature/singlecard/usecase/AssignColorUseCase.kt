package de.marxhendrik.healthcheckcards.feature.singlecard.usecase

import de.marxhendrik.healthcheckcards.R
import de.marxhendrik.healthcheckcards.feature.singlecard.network.ColorApi
import de.marxhendrik.healthcheckcards.feature.singlecard.repo.ColorRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class AssignColorUseCase(private val colorRepo: ColorRepository) {
    operator fun invoke(index: Int): Observable<Int> {
        return Observable.fromCallable { colorRepo.getColor(index) }
            .subscribeOn(Schedulers.io())
            .map { toDrawable(it) }
    }

    private fun toDrawable(color: ColorApi.Color) =
        when (color) {
            ColorApi.Color.GREEN -> R.drawable.card_green
            ColorApi.Color.RED -> R.drawable.card_red
            ColorApi.Color.YELLOW -> R.drawable.card_orange
        }
}
