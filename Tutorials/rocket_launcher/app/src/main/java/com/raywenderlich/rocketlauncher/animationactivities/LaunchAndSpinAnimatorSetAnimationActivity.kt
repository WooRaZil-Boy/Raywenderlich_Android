package com.raywenderlich.rocketlauncher.animationactivities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator

class LaunchAndSpinAnimatorSetAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val positionAnimator = ValueAnimator.ofFloat(0f, -screenHeight) //첫 번째 애니메이션

    positionAnimator.addUpdateListener {
      //listener 생성
      val value = it.animatedValue as Float
      rocket.translationY = value
    }

    val rotationAnimator = ObjectAnimator.ofFloat(rocket, "rotation", 0f, 180f) //두 번째 애니메이션
    val animatorSet = AnimatorSet() //AnimatorSet을 사용해 여러 애니메이션을 동시에 혹은 순서대로 실행할 수 있다.
    animatorSet.play(positionAnimator).with(rotationAnimator)
    //play()에 실행할 애니메이션을 변수로 전달하고, 정적 메서드를 연결해 애니메이션을 실행할 시점을 지정해 준다.
    //  - with() : 동시에 실행
    //  - before() : 이전에 실행
    //  - after() : 이후에 실행

    animatorSet.duration = DEFAULT_ANIMATION_DURATION //지속 시간

    animatorSet.start() //애니메이션 실행
  }
}

