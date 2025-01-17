package infrastructure.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object DisplayUi : Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val uiDescription: Column<String> = varchar("ui_description", 255)

    override val primaryKey = PrimaryKey(id)
}