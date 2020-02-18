package com.example.mvptry.mvp

import android.content.ContentValues
import android.database.Cursor
import android.os.AsyncTask
import com.example.mvptry.common.User
import com.example.mvptry.common.UserTable
import com.example.mvptry.database.DbHelper
import java.util.*
import java.util.concurrent.TimeUnit

class UserModel(val dbHelper: DbHelper) {

    fun loadUsers(callback: LoadUserCallback) {
        val loadUsersTask = LoadUsersTask(callback)
        loadUsersTask.execute()
    }

    fun addUser(contentValues: ContentValues, callback: CompleteCallback) {
        val addUserTask = AddUserTask(callback)
        addUserTask.execute(contentValues)
    }

    fun clearUsers(completeCallback: CompleteCallback) {
        val clearUsersTask = ClearUsersTask(completeCallback)
        clearUsersTask.execute()
    }

    interface LoadUserCallback {
        fun onLoad(users: List<User>)
    }

    interface CompleteCallback {
        fun onComplete()
    }

    inner class LoadUsersTask(private val callback: LoadUserCallback) :
        AsyncTask<Void, Void, List<User>>() {

        override fun doInBackground(vararg params: Void): List<User> {
            val users: MutableList<User> = LinkedList()
            val cursor: Cursor = dbHelper.readableDatabase
                .query(UserTable.TABLE, null, null, null, null, null, null)
            while (cursor.moveToNext()) {
                val user = User(
                    id = cursor.getLong(cursor.getColumnIndex(UserTable.COLUMN.ID)),
                    name = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN.NAME)),
                    email = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN.EMAIL))
                )
                users.add(user)
            }
            cursor.close()
            return users
        }

        override fun onPostExecute(users: List<User>) {
            callback.onLoad(users)
        }

    }

    inner class AddUserTask(private val callback: CompleteCallback) :
        AsyncTask<ContentValues, Void, Void>() {

        override fun doInBackground(vararg params: ContentValues): Void? {
            val cvUser = params[0]
            dbHelper.writableDatabase.insert(UserTable.TABLE, null, cvUser)
            try {
                TimeUnit.SECONDS.sleep(1)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(aVoid: Void) {
            super.onPostExecute(aVoid)
            callback.onComplete()
        }
    }

    inner class ClearUsersTask(private val callback: CompleteCallback) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            dbHelper.writableDatabase.delete(UserTable.TABLE, null, null)
            try {
                TimeUnit.SECONDS.sleep(1)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(aVoid: Void) {
            super.onPostExecute(aVoid)
            callback.onComplete()
        }

    }
}