package com.raywenderlich.rocketlauncher.animationactivities

import android.animation.ValueAnimator
import android.view.animation.AccelerateInterpolator

class AccelerateRocketAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val valueAnimator = ValueAnimator.ofFloat(0f, -screenHeight)

    valueAnimator.addUpdateListener {
      val value = it.animatedValue as Float
      rocket.translationY = value
    }

    valueAnimator.interpolator = AccelerateInterpolator(1.5f) //애니메이션이 가속된다.
    valueAnimator.duration = DEFAULT_ANIMATION_DURATION

    valueAnimator.start()
  }
}
