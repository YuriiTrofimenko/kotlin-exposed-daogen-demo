package org.tyaa.kotlin.exposed.daogen

import org.jetbrains.exposed.dao.*

object Teams : IntIdTable("team", "id") {
    // Database Columns
    val name = varchar("name", 25)
}
