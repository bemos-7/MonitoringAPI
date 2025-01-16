package infrastructure.database

import domain.repository.DisplayUiRepository
import infrastructure.database.table.DisplayUi
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class DisplayUiImpl : DisplayUiRepository {

    override fun getDisplayUis(): List<Pair<Int, String>> {
        return transaction {
            DisplayUi.selectAll().map {
                it[DisplayUi.id] to it[DisplayUi.uiDescription]
            }
        }
    }

    override fun insertDisplay(
        description: String
    ) {
        transaction {
            DisplayUi.insert {
                it[DisplayUi.uiDescription] = description
            }
        }
    }
}