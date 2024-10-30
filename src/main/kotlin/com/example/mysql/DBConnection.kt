package com.example.mysql

import org.ktorm.database.Database

object DBConnection {
    private val db:Database? = null

    fun getDatabaseInstance(): Database {
        if (db == null) {
            return Database.connect(
                url = "jdbc:mysql://localhost:3306/ktor",
                driver = "com.mysql.cj.jdbc.Driver",
                user = "root",
                password = ""
            )
        }
        return db
    }
}