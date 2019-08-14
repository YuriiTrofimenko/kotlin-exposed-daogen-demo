package org.tyaa.kotlin.exposed.daogen

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun main(){
    // Connect
    Database.connect(
        "jdbc:postgresql://localhost:5432/postgres"
        , driver = "org.postgresql.Driver"
        , user = "postgres"
        , password = "1"
    ) // <1>
    transaction {
        // <2>
        addLogger(StdOutSqlLogger)  // <3>
        SchemaUtils.create(Teams)    // <4>

        //Insert
        /* Teams.insert {
            it[name] = "Marketing"
        } */

        // Select
        Teams.selectAll().forEach { println("$it") }
    }
}