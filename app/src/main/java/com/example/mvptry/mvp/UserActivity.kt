package com.example.mvptry.mvp

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvptry.R
import com.example.mvptry.common.User
import com.example.mvptry.common.UserAdapter
import com.example.mvptry.database.DbHelper

class UserActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private var progressDialog: ProgressDialog? = null

    private lateinit var presenter: UserPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun init() {
        editTextName = findViewById(R.id.name)
        editTextEmail = findViewById(R.id.email)
        Log.d("tag", "get view by id")
        findViewById<Button>(R.id.add).setOnClickListener { presenter.add() }
        findViewById<Button>(R.id.clear).setOnClickListener { presenter.clear() }
        Log.d("tag", "set listener on button")

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        userAdapter = UserAdapter()
        Log.d("tag", "create adapter")

        val userList: RecyclerView = findViewById(R.id.list)
        userList.layoutManager = layoutManager
        userList.adapter = userAdapter
        val dbHelper = DbHelper(this)
        Log.d("tag", "dbHelper init")

        val usersModel = UserModel(dbHelper)
        Log.d("tag", "usersModel init")

        presenter = UserPresenter(usersModel)
        Log.d("tag", "UserPresenter init")


        presenter.attachView(this)
        Log.d("tag", "attachView")

        presenter.viewIsReady()
        Log.d("tag", "viewIsReady")

    }

    fun getUserData(): UserData {
        return UserData(
            name = editTextName.text.toString(),
            email = editTextEmail.text.toString()
        )
    }

    fun showUsers(users: List<User>) {
        userAdapter.setData(users)
    }

    fun showToast(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    fun showProgress() {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.please_wait))
    }

    fun hideProgress() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
        }
    }
}
