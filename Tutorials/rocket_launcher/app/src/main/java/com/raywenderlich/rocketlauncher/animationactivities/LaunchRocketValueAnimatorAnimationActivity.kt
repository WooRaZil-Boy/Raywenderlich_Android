package com.raywenderlich.rocketlauncher.animationactivities

import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator

class LaunchRocketValueAnimatorAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val valueAnimator = ValueAnimator.ofFloat(0f, -screenHeight)
    //ofFloat 정적 메서드로 ValueAnimator를 생성한다. 0f로 시작해서, -screenHeight으로 끝이 난다.
    //Android는 왼쪽 상단에서 화면 좌표가 시작되므로, Y는 0에서 화면 높이의 음수로 바뀐다(아래에서 위로 이동).

    valueAnimator.addUpdateListener {
      //Listener 호출. ValueAnimator의 애니메이션 값이 업데이트 될 때마다 이 Listener가 호출 된다. 기본 지연 시간은 10ms 이다.
      val value = it.animatedValue as Float //animatedValue에서 현재 값을 가져와 Float으로 캐스팅
      rocket.translationY = value //translationY을 설정하여 로켓의 위치를 변경한다.
    }

    valueAnimator.interpolator = LinearInterpolator() //Time Interpolator
    valueAnimator.duration = DEFAULT_ANIMATION_DURATION //지속시간

    valueAnimator.start() //애니메이션 시작
  }
}
