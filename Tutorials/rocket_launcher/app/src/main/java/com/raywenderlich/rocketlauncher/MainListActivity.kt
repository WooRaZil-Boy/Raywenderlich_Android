package com.raywenderlich.rocketlauncher

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.raywenderlich.rocketlauncher.animationactivities.*
import java.util.*

class MainListActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main_layout)

    val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
    recyclerView.layoutManager = android.support.v7.widget.LinearLayoutManager(this)

    val items = ArrayList<RocketAnimationItem>()

    items.add(RocketAnimationItem(getString(R.string.title_no_animation),
      Intent(this, NoAnimationActivity::class.java)))

    items.add(RocketAnimationItem(getString(R.string.title_launch_rocket),
      Intent(this, LaunchRocketValueAnimatorAnimationActivity::class.java)))

    items.add(RocketAnimationItem(getString(R.string.title_spin_rocket),
      Intent(this, RotateRocketAnimationActivity::class.java)))

    items.add(RocketAnimationItem(getString(R.string.title_accelerate_rocket),
      Intent(this, AccelerateRocketAnimationActivity::class.java)))

    items.add(RocketAnimationItem(getString(R.string.title_launch_rocket_objectanimator),
      Intent(this, LaunchRocketObjectAnimatorAnimationActivity::class.java)))

    items.add(RocketAnimationItem(getString(R.string.title_color_animation),
      Intent(this, ColorAnimationActivity::class.java)))

    items.add(RocketAnimationItem(getString(R.string.launch_spin),
      Intent(this, LaunchAndSpinAnimatorSetAnimationActivity::class.java)))

    items.add(RocketAnimationItem(getString(R.string.launch_spin_viewpropertyanimator),
      Intent(this, LaunchAndSpinViewPropertyAnimatorAnimationActivity::class.java)))

    items.add(RocketAnimationItem(getString(R.string.title_with_doge),
      Intent(this, FlyWithDogeAnimationActivity::class.java)))

    items.add(RocketAnimationItem(getString(R.string.title_animation_events),
      Intent(this, WithListenerAnimationActivity::class.java)))

    items.add(RocketAnimationItem(getString(R.string.title_there_and_back),
      Intent(this, FlyThereAndBackAnimationActivity::class.java)))

    items.add(RocketAnimationItem(getString(R.string.title_jump_and_blink),
      Intent(this, XmlAnimationActivity::class.java)))

    recyclerView.adapter = RocketAdapter(this, items)
  }
}

//How do Property Animations Work?

//애니메이션은 프레임이라는 스틸 이미지로 구성되어 있으며, 일정 시간 동안 하나씩 표시 된다.
//프레임 간 경과 시간을 frame refresh delay 이라고 하며, 기본적으로 10ms이다.
//시간을 수동으로 설정할 필요는 없다. ValueAnimator를 사용해 시작과 마지막 value와 지속 시간을 지정해 주고 listner를 설정해 준다.

//Time Interpolators
//Android 애니메이션 프레임 워크는 Time Interpolator를 사용한다. ValueAnimator는 TimeInterpolator를 통합한다.
//TimeInterpolator는 시간에 따른 애니메이션 값의 변화를 결정한다.
//  LinearInterpolator : 시간에 따라 일정한 속도(x 축 시간, y축 위치로 그래프를 그릴 때 선형으로 나타난다.)
//  AccelerateInterpolator : 시간에 따라 다른 속도(x 축 시간, y축 위치로 그래프를 그릴 때 선형으로 나타난다.)




//Your First Animation

//res/layout 에서 activity_base_animation.xml 이 BaseAnimationActivity에서 실행하는 모든 애니메이션의 슈퍼 클래스이다.




//Which Properties Can You Animate?
//ValueAnimator 생성 시 다양한 프로퍼티를 사용할 수 있다.
//  - ofFloat : float 형으로 값 입력
//  - ofInt : int 형으로 값 입력
//  - ofObject : int, float 외의 값. 색상 등을 적용할 때 사용한다.

//View 에도 다양한 프로퍼티를 적용할 수 있다.
//  - scaleX, scaleY : 독립적으로 x축 또는 y축으로 View를 확장하거나 View의 크기를 설정해 애니메이션 해 줄 수 있다.
//  - translationX, translationY : View의 위치를 변경할 수 있다.
//  - alpha : View의 투명도에 애니메이션을 적용한다. 0은 완전히 투명하고, 1은 완전히 불투명하다.
//  - rotation : 회전. 각도(360)를 값으로 가진다. 360은 전체 시계방향 회전. 음수값으로 방향을 지정해 줄 수 있다.
//               -90은 시계 반대 방향 1/4 회전
//  - rotationX, rotationY : rotation과 동일하지만, x 축 및 y축을 따라 회전한다. 이를 사용해 3D 회전을 구현할 수 있다.
//  - backgroundColor : 색상을 설정한다. Color.BLUE와 같은 int 상수를 지정해 줘야 한다.