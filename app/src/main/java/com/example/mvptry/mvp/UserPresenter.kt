package com.example.mvptry.mvp

import android.content.ContentValues
import android.text.TextUtils
import com.example.mvptry.R
import com.example.mvptry.common.User
import com.example.mvptry.common.UserTable

class UserPresenter(private val model: UserModel) {

    private var view: UserActivity? = null

    fun attachView(usersActivity: UserActivity) {
        view = usersActivity
    }

    fun detachView() {
        view = null
    }

    fun viewIsReady() {
        loadUsers()
    }

    fun loadUsers() {
        model.loadUsers(object : UserModel.LoadUserCallback {
            override fun onLoad(users: List<User>) {
                view!!.showUsers(users)
            }
        })
    }

    fun add() {
        val userData: UserData = view!!.getUserData()
        if (TextUtils.isEmpty(userData.name) || TextUtils.isEmpty(userData.email)) {
            view!!.showToast(R.string.empty_values)
            return
        }
        val cv = ContentValues(2)
        cv.put(UserTable.COLUMN.NAME, userData.name)
        cv.put(UserTable.COLUMN.EMAIL, userData.email)
        view!!.showProgress()
        model.addUser(cv, object : UserModel.CompleteCallback {
            override fun onComplete() {
                view!!.hideProgress()
                loadUsers()
            }
        })
    }

    fun clear() {
        view!!.showProgress()
        model.clearUsers(object : UserModel.CompleteCallback {
            override fun onComplete() {
                view!!.hideProgress()
                loadUsers()
            }
        })
    }

}
