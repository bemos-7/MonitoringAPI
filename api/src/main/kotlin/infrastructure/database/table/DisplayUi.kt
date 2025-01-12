package infrastructure.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object DisplayUi : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val uiId: Column<Int> = integer("ui_id")

    override val primaryKey = PrimaryKey(id)
}