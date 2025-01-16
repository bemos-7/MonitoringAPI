package domain.repository

interface DisplayUiRepository {
    fun getDisplayUis() : List<Pair<Int, String>>
    fun insertDisplay(description: String)
}