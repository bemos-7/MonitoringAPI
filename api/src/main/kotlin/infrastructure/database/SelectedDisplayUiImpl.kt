package infrastructure.database

import domain.repository.SelectedDisplayUiRepository
import infrastructure.database.table.SelectedDisplayUi
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class SelectedDisplayUiImpl : SelectedDisplayUiRepository {
    override fun insertSelectedDisplay(id: Int) {
        dropSelectedDisplay()
        transaction {
            SelectedDisplayUi.insert {
                it[SelectedDisplayUi.uiNumber] = id
            }
        }
    }

    override fun dropSelectedDisplay() {
        transaction {
            SelectedDisplayUi.deleteAll()
        }
    }

    override fun getSelectedDisplay(): Int? {
        return transaction {
            SelectedDisplayUi.selectAll()
                .map { it[SelectedDisplayUi.uiNumber] }
                .firstOrNull()
        }
    }
}