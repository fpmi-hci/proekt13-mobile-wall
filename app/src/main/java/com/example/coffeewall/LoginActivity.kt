package com.example.coffeewall

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


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
        btLogInRegister.setOnClickListener { v -> onLoginClick(v) }
        btSwitchMode = findViewById(R.id.btSwitchLoginMode)
        btSwitchMode.setOnClickListener { v -> onChangeModeClick(v) }
    }

    private fun onLoginClick(v: View) {
        val mainActivityIntent = Intent(this@LoginActivity, MainActivity::class.java)
        this@LoginActivity.startActivity(mainActivityIntent)
        finish()
    }

    private fun onChangeModeClick(v: View) {
        isLogin = !isLogin
        if (isLogin) {
            editConfirm.visibility = View.GONE
            btLogInRegister.text = getString(R.string.log_in)
            btSwitchMode.text = getString(R.string.register)
            tvModeLabel.text = getString(R.string.dont_have_an_account)
        } else {
            tvModeLabel.text = getString(R.string.already_got_account)
            btLogInRegister.text = getString(R.string.sign_up)
            btSwitchMode.text = getString(R.string.log_in)
            editConfirm.visibility = View.VISIBLE
        }
    }
}