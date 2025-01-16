package infrastructure.database.table

import infrastructure.database.table.DisplayUi.autoIncrement
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object SelectedDisplayUi : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val uiNumber: Column<Int> = integer("ui_number")

    override val primaryKey = PrimaryKey(id)
}