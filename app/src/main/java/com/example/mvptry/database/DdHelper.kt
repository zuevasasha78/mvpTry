package com.example.mvptry.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mvptry.common.UserTable

class DbHelper(context: Context?) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(UserTable.CREATE_SCRIPT)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {}

    companion object {
        const val NAME = "mvpsample"
        const val VERSION = 1
    }
}