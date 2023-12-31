package com.matiz22.richgophishclient.model

import org.jetbrains.exposed.sql.Table

object UserConfigTable : Table() {
    val id = long("id").autoIncrement().uniqueIndex()
    val name = varchar("name", 255)
    val userId = reference("userId", UserTable.id)
    val url = varchar("url", 255)
    val apiKey = varchar("apiKey", 255)
}