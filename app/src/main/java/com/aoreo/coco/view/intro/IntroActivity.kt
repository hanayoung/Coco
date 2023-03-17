package com.aoreo.coco.view.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.aoreo.coco.view.main.MainActivity
import com.aoreo.coco.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var binding : ActivityIntroBinding

    private val viewModel : IntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen() //installSplashScreen은 스플래시 화면 객체를 반환하며, 이 객체를 사용하여 애니메이션을 맞춤설정하거나 화면에 스플래시 화면을 더 오래 표시할 수 있습니다. 이를 추가하지 않을 경우 " You need to use a Theme.AppCompat theme (or descendant) with this activity." 오류 발생

        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.checkFirstFlag()

        viewModel.first.observe(this, Observer {
            if(it){
                // 처음 접속하는 유저가 아님
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }else{
                // 처음 접속하는 유저
                binding.animationView.visibility=View.INVISIBLE // 뒤에 애니메이션이 계속 보이는 걸 방지하기 위해
                binding.fragmentContainerView.visibility=View.VISIBLE
            }
        })
    }
}