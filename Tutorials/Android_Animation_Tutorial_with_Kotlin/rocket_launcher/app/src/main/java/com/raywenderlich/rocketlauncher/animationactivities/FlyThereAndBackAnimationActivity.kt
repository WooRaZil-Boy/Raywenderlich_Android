package com.raywenderlich.rocketlauncher.animationactivities

import android.animation.ValueAnimator

class FlyThereAndBackAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val animator = ValueAnimator.ofFloat(0f, -screenHeight)

    animator.addUpdateListener {
      val value = it.animatedValue as Float
      rocket.translationY = value
      doge.translationY = value
    }

    animator.repeatMode = ValueAnimator.REVERSE //반복 모드
    //  - RESTART : 애니메이션을 처음부터 다시 시작한다.
    //  - REVERSE : 반복 시, 애니메이션을 역 재생한다.
    animator.repeatCount = 3 //반복 횟수
    animator.duration = 500L //애니메이션 시간

    animator.start()
  }
}
