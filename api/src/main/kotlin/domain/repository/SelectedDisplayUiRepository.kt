package domain.repository

interface SelectedDisplayUiRepository {
    fun insertSelectedDisplay(id: Int)
    fun dropSelectedDisplay()
    fun getSelectedDisplay(): Int?
}