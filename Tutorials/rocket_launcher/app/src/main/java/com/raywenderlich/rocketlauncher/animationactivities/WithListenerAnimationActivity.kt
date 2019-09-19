package com.raywenderlich.rocketlauncher.animationactivities

import android.animation.Animator
import android.animation.ValueAnimator
import android.widget.Toast

class WithListenerAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    //Listener는 특정 작업이 실행됐거나 실행됨을 알려준다. //애니메이션이 끝났을 때, 특정 View를 제거하여 리소스를 줄일 수 있다.
    //AnimatorListener는 다음과 같은 이벤트가 발생하면 알림을 받는다.
    //  - onAnimationStart() : 애니메이션 시작 시 호출
    //  - onAnimationEnd() : 애니메이션 종료 시 호출
    //  - onAnimationRepeat() : 애니메이션 반복 시 호출
    //  - onAnimationCancel() : 애니메이션 취소 시 호출

    val animator = ValueAnimator.ofFloat(0f, -screenHeight)

    animator.addUpdateListener {
      val value = it.animatedValue as Float
      rocket.translationY = value
      doge.translationY = value

      //ObjectAnimator는 ValueAnimator와 달리 하나의 동일한 작업을 여러 객체에 적용할 수 없다.
    }

    animator.addListener(object : Animator.AnimatorListener { //Listener를 추가한다.
      override fun onAnimationStart(animation: Animator) { //애니메이션이 시작될 때
        Toast.makeText(applicationContext, "Doge took off", Toast.LENGTH_SHORT)
                .show()
      }

      override fun onAnimationEnd(animation: Animator) { //애니메이션이 끝날 때
        Toast.makeText(applicationContext, "Doge is on the moon", Toast.LENGTH_SHORT)
                .show()
        finish()
      }

      override fun onAnimationCancel(animation: Animator) {} //애니메이션 취소 시
      override fun onAnimationRepeat(animation: Animator) {} //애니메이션 반복 시
    })

    animator.duration = 5000L
    animator.start()


    //ViewPropertyAnimator를 사용하는 경우에는 start() 이전에 다음과 같은 방식으로 Listener를 추가해 준다.
//    rocket.animate().setListener(object : Animator.AnimatorListener {
//      // Your action
//    })

  }
}
