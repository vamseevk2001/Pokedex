package vamsee.application.pokemon

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //TO HIDE THE STATUS BAR AND TO MAKE THE SPLASH SCREEN  A FULLSCREEN ACTIVITY:
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        

        //ANIMATION:
        val bg: ImageView = findViewById(R.id.logo)
        val sideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_transition)
        bg.startAnimation(sideAnimation)


        //POST DELAYED(RUNNABLE, TIME) IS USED TO SEND THE MSG WITH A DEALYED TIME
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) //3000 IS THE TIME IN MILLI SEC0NDS

    }
}