package com.example.mysql.entity

import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar

object Users : Table<Nothing>("user") {
    val id = uuid("id").primaryKey()
    val username = varchar("username")
    val password = varchar("password")
}

