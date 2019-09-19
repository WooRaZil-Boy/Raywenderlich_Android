package com.raywenderlich.rocketlauncher.animationactivities

import android.animation.AnimatorSet
import android.animation.ValueAnimator

class FlyWithDogeAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val positionAnimator = ValueAnimator.ofFloat(0f, -screenHeight) //위치 변경 애니메이션
    positionAnimator.addUpdateListener {
      val value = it.animatedValue as Float
      rocket.translationY = value
      doge.translationY = value

      //ValueAnimator의 장점은 지정한 값을 재사용하여 여러 객체에 적용할 수 있다는 것이다.
    }

    val rotationAnimator = ValueAnimator.ofFloat(0f, 360f) //회전 애니메이션
    rotationAnimator.addUpdateListener {
      val value = it.animatedValue as Float
      doge.rotation = value
    }

    val animatorSet = AnimatorSet()
    animatorSet.play(positionAnimator).with(rotationAnimator) //애니메이션 결합
    animatorSet.duration = DEFAULT_ANIMATION_DURATION

    animatorSet.start()
  }
}
