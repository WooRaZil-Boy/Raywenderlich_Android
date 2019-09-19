package com.raywenderlich.rocketlauncher.animationactivities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View

import com.raywenderlich.rocketlauncher.R

abstract class BaseAnimationActivity : AppCompatActivity() {
  protected lateinit var rocket: View
  protected lateinit var doge: View
  //rocket과 doge는 모두 Image를 가진 ImageView이지만, Android의 애니메이션 변수는 모두 View에서 작동한다.

  protected lateinit var frameLayout: View //rocket과 doge를 포함하는 frameLayout
  //사용하기 전에 설정되기 때문에 lateinit로 선언. 다른 곳에서 사용되기 전에 onCreate 에서 설정된다.
  protected var screenHeight = 0f

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_base_animation) //레이아웃 파일을 설정해 준다.

    rocket = findViewById(R.id.rocket)
    doge = findViewById(R.id.doge)
    frameLayout = findViewById(R.id.container)
    //변수 바인딩

    frameLayout.setOnClickListener {
      //listener를 성정해 준다.
      onStartAnimation()
      //화면을 터치할 때마다 호출된다.
    }

  }

  override fun onResume() {
    super.onResume()

    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    screenHeight = displayMetrics.heightPixels.toFloat()
  }

  protected abstract fun onStartAnimation()

  companion object {
    val DEFAULT_ANIMATION_DURATION = 2500L
  }
}
