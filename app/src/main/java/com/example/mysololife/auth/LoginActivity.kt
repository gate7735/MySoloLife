package com.example.mysololife.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mysololife.MainActivity
import com.example.mysololife.R
import com.example.mysololife.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val loginBtn = binding.btnLogin
        loginBtn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pwd = binding.etPwd.text.toString()

            val isGoToLogin = dataCheck(this, email, pwd)
            if (isGoToLogin) {
                auth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            toastMsg(this, "로그인 성공")
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                        } else {
                            toastMsg(this, "로그인 실패")
                        }
                    }
            }
        }
    }

    fun dataCheck(context: Context, email: String, pwd: String): Boolean {
        if(email.isEmpty()) {
            toastMsg(context, "이메일을 입력해주세요")
        } else if(pwd.isEmpty()) {
            toastMsg(context, "비밀번호를 입력해주세요")
        } else if(pwd.length < 6) {
            toastMsg(context, "비밀번호를 6자리 이상 입력해주세요")
        } else {
            return true
        }
        return false
    }

    // 전역 함수로 만들까?
    fun toastMsg(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}