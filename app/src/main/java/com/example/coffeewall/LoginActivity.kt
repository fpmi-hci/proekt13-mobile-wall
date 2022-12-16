package com.example.coffeewall

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    val users = setOf(Pair("Kate", "kate"), Pair("Zmitser", "zmitser"))

    var isLogin: Boolean = true

    private lateinit var editLogin: EditText
    private lateinit var editPassword: EditText

    private lateinit var editConfirm: EditText
    private lateinit var tvModeLabel: TextView

    private lateinit var btLogInRegister: Button
    private lateinit var btSwitchMode: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editLogin = findViewById(R.id.editLogin)
        editPassword = findViewById(R.id.editPassword)
        editConfirm = findViewById(R.id.editConfirm)
        tvModeLabel = findViewById(R.id.tvModeLabel)

        btLogInRegister = findViewById(R.id.btLogInRegister)
        btLogInRegister.setOnClickListener { v -> onLoginClick(v) }
        btSwitchMode = findViewById(R.id.btSwitchLoginMode)
        btSwitchMode.setOnClickListener { v -> onChangeModeClick(v) }
    }

    private fun onLoginClick(v: View) {
        val enteredLogin = editLogin.text.toString()
        val enteredPassword = editPassword.text.toString()
        val enteredConfirm = editConfirm.text.toString()

        if (enteredLogin.isBlank() || enteredPassword.isBlank()) {
            Toast.makeText(this, getString(R.string.blank_login_password), Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (isLogin && !users.contains(Pair(enteredLogin, enteredPassword))) {
            Toast.makeText(this, getString(R.string.wrong_credentials), Toast.LENGTH_SHORT)
                .show()
            return
        }

        if (!isLogin && enteredConfirm != enteredPassword) {
            Toast.makeText(this, getString(R.string.passwords_not_match), Toast.LENGTH_SHORT)
                .show()
            return
        }

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