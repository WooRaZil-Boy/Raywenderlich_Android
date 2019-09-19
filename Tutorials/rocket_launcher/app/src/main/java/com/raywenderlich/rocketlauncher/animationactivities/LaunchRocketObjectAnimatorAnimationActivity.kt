package com.raywenderlich.rocketlauncher.animationactivities

import android.animation.ObjectAnimator

class LaunchRocketObjectAnimatorAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    val objectAnimator = ObjectAnimator.ofFloat(rocket, "translationY", 0f, -screenHeight)
    //ObjectAnimator는 ValueAnimator의 하위 클래스로, 단일 객체에 하나의 프로퍼티만을 사용할 때 유용하다.
    //  - target : 애니메이션 적용할 객체(rocket)
    //  - propertyName : 변경하려는 property 이름을 지정해 준다(translationY).
    //  - values : 해당 값을 지정해 준다. 0f ~ -screenHeight

    objectAnimator.duration = DEFAULT_ANIMATION_DURATION //지속 시간 설정

    objectAnimator.start()

    //ValueAnimator로 똑같이 구현 할 수 있지만, 코드를 더 간결하게 유지할 수 있다.
    //하지만, ObjectAnimator는 두 객체에 동시에 애니메이션을 적용할 수 없다. 따라서, ObjectAnimator 를 여러 개 만들어야 한다.
    //상황에 따라 ValueAnimator와 ObjectAnimator 중 적절한 것을 사용할 수 있다.
  }
}
