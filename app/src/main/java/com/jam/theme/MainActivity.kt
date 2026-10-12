package com.jam.theme
import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
class MainActivity : Activity() {
    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
    }
}
