package com.example.coffeewall

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    var isLogin: Boolean = true

    lateinit var editConfirm: EditText
    lateinit var tvModeLabel: TextView

    lateinit var btLogInRegister: Button
    lateinit var btSwitchMode: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editConfirm = findViewById(R.id.editConfirm)
        tvModeLabel = findViewById(R.id.tvModeLabel)

        btLogInRegister = findViewById(R.id.btLogInRegister)
        btSwitchMode = findViewById(R.id.btSwitchLoginMode)
        btSwitchMode.setOnClickListener { v -> onChangeModeClick(v) }
    }

    fun onChangeModeClick(v: View) {
        isLogin = !isLogin
        if (isLogin) {
            editConfirm.visibility = View.GONE
            btLogInRegister.text = "Log In"
            btSwitchMode.text = "Register!"
            tvModeLabel.text = "Don't Have an Account?"
        } else {
            tvModeLabel.text = "Already Got an Account?"
            btLogInRegister.text = "Sign Up"
            btSwitchMode.text = "Sign In!"
            editConfirm.visibility = View.VISIBLE
        }
    }
}