package com.raywenderlich.rocketlauncher.animationactivities

import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator

class RotateRocketAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val valueAnimator = ValueAnimator.ofFloat(0f, 360f)
    //0f 에서 360f까지 회전. 0f ~ 180f 로 설정하면 절반만 회전한다.

    valueAnimator.addUpdateListener {
      val value = it.animatedValue as Float

      rocket.rotation = value //회전 값 설정
    }

    valueAnimator.interpolator = LinearInterpolator()
    valueAnimator.duration = DEFAULT_ANIMATION_DURATION

    valueAnimator.start()
  }
}
