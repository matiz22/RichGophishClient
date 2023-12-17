package com.matiz22.richgophishclient.model

import org.jetbrains.exposed.sql.Table


object UserTable : Table() {
    val id = long("id").autoIncrement().uniqueIndex()
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    override val primaryKey = PrimaryKey(id)
}