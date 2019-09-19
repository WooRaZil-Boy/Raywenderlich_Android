package com.raywenderlich.rocketlauncher.animationactivities

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import com.raywenderlich.rocketlauncher.R

class XmlAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val rocketAnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.jump_and_blink) as AnimatorSet
    //xml에서 해당 애니메이션을 가져온다.
    rocketAnimatorSet.setTarget(rocket) //애니메이션의 대상 지정

    val dogeAnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.jump_and_blink) as AnimatorSet
    dogeAnimatorSet.setTarget(doge)

    val bothAnimatorSet = AnimatorSet()
    bothAnimatorSet.playTogether(rocketAnimatorSet, dogeAnimatorSet) //두 애니메이션을 동시에 실행한다.
    bothAnimatorSet.duration = DEFAULT_ANIMATION_DURATION //지속 시간

    bothAnimatorSet.start()
  }
}
