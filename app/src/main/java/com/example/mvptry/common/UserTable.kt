package com.example.mvptry.common

class UserTable {

    object COLUMN {
        const val ID = "_id"
        const val NAME = "name"
        const val EMAIL = "email"
    }

    companion object {
        const val TABLE = "users"
        val CREATE_SCRIPT = String.format(
            "create table %s ("
                    + "%s integer primary key autoincrement,"
                    + "%s text,"
                    + "%s text" + ");",
            TABLE, COLUMN.ID, COLUMN.NAME, COLUMN.EMAIL
        )
    }
}