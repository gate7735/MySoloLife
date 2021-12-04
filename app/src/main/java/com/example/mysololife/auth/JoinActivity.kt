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
import com.example.mysololife.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_join)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        auth = Firebase.auth

        val joinBtn = binding.btnJoin
        joinBtn.setOnClickListener {
            var isGoToJoin: Boolean
            // Given String is empty or null 오류 발
            val email = binding.etEmail.text.toString()
            val pwd = binding.etPwd.text.toString()
            val pwdChk = binding.etPwdchk.text.toString()

            isGoToJoin = dataCheck(this, email, pwd, pwdChk)
            if (isGoToJoin) {
                auth = Firebase.auth
                auth.createUserWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            toastMsg(this, "성공")
                            val intent = Intent(this, MainActivity::class.java)
                            // 기존 액티비티 다 날리기
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                        } else {
                            toastMsg(this, "실패")
                        }
                    }
            }
        }
    }

    fun dataCheck(context: Context, email: String, pwd: String, pwdChk: String): Boolean {
        if(email.isEmpty()) {
            toastMsg(context, "이메일을 입력해주세요")
        } else if(pwd.isEmpty()) {
            toastMsg(context, "비밀번호를 입력해주세요")
        } else if(pwdChk.isEmpty()) {
            toastMsg(context, "비밀번호 체크를 입력해주세요")
        } else if(!pwd.equals(pwdChk)) {
            toastMsg(context, "비밀번호를 동일하게 입력해주세요")
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