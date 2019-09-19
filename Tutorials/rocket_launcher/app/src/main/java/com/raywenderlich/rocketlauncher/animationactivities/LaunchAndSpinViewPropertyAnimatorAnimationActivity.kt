package com.raywenderlich.rocketlauncher.animationactivities

class LaunchAndSpinViewPropertyAnimatorAnimationActivity : BaseAnimationActivity() {
  override fun onStartAnimation() {
    rocket.animate() //ViewPropertyAnimator를 사용해 여러 애니메이션을 함께 지정해 줄 수도 있다. //View에 animate() 메서드를 연결해 호출한다.
            .translationY(-screenHeight)
            .rotationBy(360f)
            .setDuration(DEFAULT_ANIMATION_DURATION)
            .start()
    //AnimatorSet을 사용한 것과 같은 결과를 내지만, 코드를 훨씬 간결하게 유지할 수 있다.
    //ViewPropertyAnimator 가 여러 애니메이션을 혼합할 때 더 나은 성능을 보여준다.
  }
}
