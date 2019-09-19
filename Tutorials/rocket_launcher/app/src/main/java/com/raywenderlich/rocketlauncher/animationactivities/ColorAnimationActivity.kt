package com.raywenderlich.rocketlauncher.animationactivities

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v4.content.ContextCompat
import com.raywenderlich.rocketlauncher.R

class ColorAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val objectAnimator = ObjectAnimator.ofObject(
            //색상을 변경해야 할 때는 ofFloat() 이나 ofInt()이 아닌 ofObject() 정적 메서드를 사용한다.
            frameLayout, //애니메이션을 적용할 객체
            "backgroundColor", //애니메이션을 적용할 속성
            ArgbEvaluator(), //두 개의 다른 ARGB 색상 값 을 Interpolator 하는 객체
            ContextCompat.getColor(this, R.color.background_from), //시작 색상
            ContextCompat.getColor(this, R.color.background_to) //종료 색상
            //color.xml에 지정해 놓은 색상의 id를 가져온다.
    )

    objectAnimator.repeatCount = 1 //반복 횟수
    objectAnimator.repeatMode = ValueAnimator.REVERSE //애니메이션이 끝났을 때, 수행할 작업
    objectAnimator.duration = DEFAULT_ANIMATION_DURATION //지속 시간

    objectAnimator.start() //애니메이션 시작
  }
}
