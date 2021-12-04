package com.example.mysololife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.mysololife.auth.IntroActivity
import com.example.mysololife.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Firebase 코드는 따로 클래스 지정해서 빼고싶다
 * toastMsg 메소드를 전역으로 선언하고 싶다
 * Log를 사용할 때 Tag를 자신의 액티비티 이름명으로 하고싶다
 * Toast를 사용할 때 왜 context를 사용할까
 * 만일 레이아웃을 꾸밀 때 1920x1080 기준으로 라이브러리를 만든다면?
 * 이미 띄어져있는 액티비티들을 다 종료하고 싶다면?
 *
 */

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

}