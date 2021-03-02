package pakiet.arkadiuszzimny.expenotes_v1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.splash_screen.*
import pakiet.arkadiuszzimny.expenotes_v1.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation)

        logoLeft.startAnimation(topAnimation)
        logoRight.startAnimation(middleAnimation)

        val splashScreenTimeOut = 1900
        val homeIntent = Intent(this, MainActivity::class.java)

        Handler().postDelayed({
            startActivity(homeIntent)
            finish()
        }, splashScreenTimeOut.toLong())
    }
}