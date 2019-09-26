/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.razegalactic

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.animation.LinearInterpolator
//import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.keyframe1.*


/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {
  //Animation을 위해 추가.
  private val constraintSet1 = ConstraintSet() //View에 애니메이션을 적용한는데 사용할 제약 조건 세트
  private val constraintSet2 = ConstraintSet() //View에 애니메이션을 적용한는데 사용할 제약 조건 세

  private var isOffscreen = true //레이아웃 상태 추적


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_main)
    setContentView(R.layout.keyframe1)





    //Animating the View
    constraintSet1.clone(constraintLayout) //id로 해당 레이아웃(첫 프레임) 정보를 가져온다.
    constraintSet2.clone(this, R.layout.activity_main) //해당 레이아웃(마지막 프레임) 정보를 가져온다.
    //ConstraintSet로 가져오지만, 실제로 inflate 하는 것이 아니기 때문에, 리소스가 적게 소모된다.

    departButton.setOnClickListener { //버튼에 listener 추가
      //apply the transition
//      TransitionManager.beginDelayedTransition(constraintLayout) //전환
//
//      val constraint = if (!isOffscreen) constraintSet1 else constraintSet2
//      isOffscreen = !isOffscreen
//      constraint.applyTo(constraintLayout) //현재 레이아웃(constraintLayout)에 해당 constraintSet을 적용한다.




      //Animating the Circular Constraint
      departButton.setOnClickListener {
        val layoutParams = rocketIcon.layoutParams as ConstraintLayout.LayoutParams
        val startAngle = layoutParams.circleAngle
        val endAngle = startAngle + (if (switch1.isChecked) 360 else 180)
        //애니메이션 시작 전에 해당 아이콘의 레이아웃 파라미터를 가져온다.
        //switch1(편도/왕복)에 따라 끝나는 각도를 지정해 준다.

        val anim = ValueAnimator.ofFloat(startAngle, endAngle)
        //ValueAnimator는 두 값 사이에서 타이밍을 맞춰 애니메이션을 실행한다.
        anim.addUpdateListener { valueAnimator ->
          val animatedValue = valueAnimator.animatedValue as Float
          //애니메이션 값을 가져온다.
          val layoutParams = rocketIcon.layoutParams as ConstraintLayout.LayoutParams
          layoutParams.circleAngle = animatedValue //값을 업데이트 해 준다.
          rocketIcon.layoutParams = layoutParams

          rocketIcon.rotation = (animatedValue % 360 - 270) //아이콘을 회전 시킨다.
        }

        anim.duration = if (switch1.isChecked) 2000 else 1000 //애니메이션 지속 시간

        anim.interpolator = LinearInterpolator() //interpolator 설정. 선형 속도로 애니메이션 된다.
        //AnticipateOvershootInterpolator()를 사용해 볼 수도 있다.
        anim.start()
      }
    }






    switch1.setOnCheckedChangeListener { _, isChecked ->
      switch1.setText(if (isChecked) R.string.round_trip else R.string.one_way)
    }
  }



  //Using Custom Transitions to Make Animation Easier
  override fun onEnterAnimationComplete() {
    //View가 애니메이션 되는 동안, Activity는 아무것도 drawing 할 수 없다.
    //onEnterAnimationComplete()는 view life cycle 중에서, view 애니메이션이 완료됐을 때 호출된다.
    //이 시점에ㅔ서 drawing 코드를 추가하는 것이 안전하다.
    super.onEnterAnimationComplete()

    constraintSet2.clone(this, R.layout.activity_main) //최종 레이아웃(activity_main)의 정보를 가져온다.

    //apply the transition
    val transition = AutoTransition() //사용자 정의 transition을 생성한다.
    //default transition 효과는 이전 레이아웃을 fade out한 이후, 새로운 레이아웃을 이동 시켜 크기를 조정한 다음 fade in 한다.
    transition.duration = 1000 //애니메이션 지속 시간
    TransitionManager.beginDelayedTransition(constraintLayout, transition) //전환. 사용자 정의 transition 도 함께 파라미터로 넘겨준다.

    constraintSet2.applyTo(constraintLayout) //새 ConstraintSet을 현재 레이아웃(ConstraintLayout)에 적용한다.
  }
}

//ConstraintLayout으로 Android에서 View Layout을 유연하게 만들 수 있다.
//ConstraintLayout는 Android Studio의 기본 레이아웃으로, 객체를 배치하는 여러 가지 방법을 제공한다.
//복잡한 구조의 View 레이아웃도 구성할 수 있으며, 애니메이션도 지원한다.




//Raze Galactic — An Intergalactic Travel Service

//Getting Started
//activity_main.xml에서 레이아웃을 구성한다. starter project의 기본적인 구조는 여러개의 LinearLayout과 RelativeLayout이 중첩된 구조이다.
//이와 같이 LinearLayout과 RelativeLayout으로 구현해도 되지만, 복잡한 레이아웃을 구성할 때 ConstraintLayout을 사용하면 유용하다.

//Converting a Layout to ConstraintLayout
//디자인 섹션의 Component Tree 에서 최상위 레벨의 LinearLayout을 마우스 우클릭 한후, Convert LinearLayout to ConstraintLayout를 선택한다.
//확인을 누르면, 중첩되어 있던 모든 레이아웃을 제거하고 ConstraintLayout로 변환한다.

//Removing Inferred Constraints
//제약조건이 엉켜서 레이아웃이 엉망이 되는 경우도 있는데, 그 경우에는 Edit 메뉴에서 Undo Infer Constraints을 선택한다(⌘-Z).
//위 작업을 진행하면, 단순히 충돌나는 제약 조건을 되돌리기 때문에 세부적으로 수정을 추가해줘야 한다. 세부적으로 수정하기 앞서 대략적인 위치와 크기를 잡아준다.
//세부적인 작업은 코드로 맞춰주므로, 지금 단계에서 완벽하게 맞출 필요는 없다.
//뷰를 드래그할 때, Android Studio에서 자동으로 제약조건을 추가될 수도 있다. 이 경우에는, Clear All Constraints 버튼을 눌러 제약조건을 모두 지워줄 수 있다.


//Resizing the Images
//해당 이미지의 크기를 조절해 준다. 각 아이콘을 선택하고, Attributes 패널에서 layout_width, layout_height 를 변경해 준다.
//각 프로퍼티를 수정하면서, Component Tree에 UI 요소 위치를 알려주는 제약 조건이 없어 여러 에러들이 뜨는데, 이를 수정해 나가면 된다.




//Adding Constraints: Figuring out Alignment
//화면 상단부터 하단까지 하향식으로 제약 조건을 설정한다.

//Constraining the First Icon
//기본적인 margin과 크기를 지정해 준다. Design 탭에서 각 속성을 고칠 수 있지만, Text 상태에서 편집을 할 수도 있다.

//Aligning the Top Three Icons Horizontally: Using Chains
//화면 상단의 세 아이콘을 같은 간격으로 이어지도록 한다. 제약 조건을 추가해 구현할 수 있지만, chains를 사용하면 훨씬 쉽게 작업할 수 있다.




//Chains
//chain은 제약조건이 양방향인 경우 사용한다. Align Horizontal Center 등을 사용할 경우, chain을 사용한다.
//chain마다 스타일, 가중치, 여백 등을 적용할 수 있다.
//정렬할 아이콘을 다중 클릭 한 다음, 우클릭, Center ▸ Horizontally를 선택한다. chain이 생성되고 제약조건이 채워진다.

//Exploring Chains
//chain에는 여러 모드가 있다. Design 탭에서 chain이 연결된 해당 뷰를 선택하면 아래쪽에 아이콘이 뜬다(Android Studio 버전 바뀌면서 없어진듯. 우클릭에 있음).
//Cycle Chain Mode를 선택한다. 세 가지 모드가 있으며, 선택할 때마다 각 모드로 바뀐다.
//  - Packed : chain의 요소들이 모아져서 함께 보여진다.
//  - Spread : chain의 요소들이 빈 공간에 비례하여 퍼진다.
//  - Spread inside : Spread와 비슷하지만, chain의 endpoint는 변경되지 않는다.
//Text 탭에서 해당 아이콘이 app:layout_constraintHorizontal_chainStyle="spread" 으로 설정되었는지 확인해 볼 수 있다.

//Aligning Views
//다시 정렬할 세 아이콘을 선택하고 우클릭 후 Align ▸ Vertical Centers 를 선택한다.
//Android Studio가 해당 뷰에 제약 조건을 추가하여 세로로 정렬되도록 만들어 준다.

//Aligning the Text for Each of the Icons
//각 아이콘에 맞는 Text를 정렬해 준다. 이미 아이콘은 정렬되어 있기 때문에, 드래그로 위치를 맞춰주면 된다.




//Using Guidelines
//지금까지는 UI 요소를 상위 컨테이너에 제약조건을 추가했다. 이와 다른 방법으로, 레이아웃에 보이지 않는 guide line을 추가해 여기에 UI 제약조건을 추가해 줄 수 있다.

//Setting the Horizontal and Vertical Guidelines
//해당 아이콘을 선택하고 크기를 지정해 준 뒤 우클릭 후 Center ▸ Horizontally in Parent 를 선택한 다음 각 아이콘들을 정렬한다.
//툴바의 Guidelines 버튼을 눌러 Add Horizontal Guideline를 선택한다.
//Component Tree 에서 생성한 Guideline를 선택하고 ID를 지정해 준다.
//layout_constraintGuide_begin 과 layout_constraintGuide_percent 를 지정해 준다.
//비슷한 방식으로, vertical guideline도 추가해 준다.

//Positioning the Guidelines
//  - layout_constraintGuide_begin : Parent View의 왼쪽(vertical guides) 혹은, 상단(horizontal guides)에서 부터 해당 dp 만큼 떨어진 위치에 guideline 을 배치한다.
//  - layout_constraintGuide_end : Parent View의 오른쪽(vertical guides) 혹은, 하단(horizontal guides)에서 부터 해당 dp 만큼 떨어진 위치에 guideline 을 배치한다.
//  - layout_constraintGuide_percent : Parent View의 너비(vertical guides) 혹은, 높이(horizontal guides)에서 부터 해당 백분율(0 ~ 1) 만큼 떨어진 위치에 guideline 을 배치한다.
//해당 요소를 Guideline에 추가한 후에도 다른 제약조건을 추가할 수 있다. 이렇게 하면, Guideline의 위치가 변경되면, 여기에 고정된 다른 요소들도 모두 위치가 조정된다.
//이 방법을 사용해 애니메이션을 구현할 수 있다.

//Adding Constraints to Guidelines
//각 요소의 위치를 맞춰 준다.




//Circular Position Constraints
//지금까지 방법 외에도 거리와 각도를 사용해 UI 요소에 제약조건을 추가해 줄 수도 있다. 이를 이용하면, 하나의 UI 요소가 원의 중앙에 있고 다른 UI요소가 원의 둘레에 있도록 배치할 수도 있다.
//rocketIcon을 업데이트 한다.
//  - layout_constraintCircle : 원의 중심에 위치할 UI의 id를 지정해 준다.
//  - layout_constraintCircleAngle : 각도
//  - layout_constraintCircleRadius : 반경
//Component Tree 에 오류가 뜰 수도 있다. Android Studio는 아직 제대로 circular constraint를 인식하지 못 한다.




//Animating the UI Elements on the Screen

//Constraint Sets
//ConstraintLayout을 사용해서, view에 Keyframe Animation을 추가할 수 있다.
//이를 구현하기 위해, ConstraintSet 이라는 레이아웃의 복사본을 제공한다. ConstraintSet에 ConstraintLayout 요소의 제약조건, margin, padding을 포함하면 된다.
//Kotlin에서는 ConstraintLayout에 레이아웃을 업데이트하기 위해 ConstraintSet을 적용할 수 있다.
//애니메이션을 만드려면, 단일 레이아웃 파일과 시작과 끝의 key frame으로 사용할 ConstraintSet이 필요하다.
//transition을 적용해 더 나은 애니메이션 효과를 만들 수도 있다.

//Setting up the Starting Layout for Your Animation
//레이아웃 파일을 복사해, keyframe1.xml을 생성한다. 여기서 요소의 위치를 변경해 시작 레이아웃으로 설정한다.
//guideline1의 layout_constraintGuide_begin를 0dp로 변경한다. 해당 가이드라인에 연결된 모든 UI가 이동(위로)한다.
//guideline2의 layout_constraintGuide_percent을 1로 변경한다. 해당 가이드라인에 연결된 모든 UI가 이동(우측)한다.
//해당 요소를 확인해 보려면, Activity의 onCreate()에서 setContentView() 메서드의 파라미터로 연결된 레이아웃을 변경해 주면 된다.




//Animating the View
//상단의 import 구문에서도 해당 xml로 바꿔준다. findViewById()로 바인딩된 뷰를 가져올 수 있게 된다.
//또한, 애니메이션을 위한, ConstraintSet 과련 변수들을 추가해 준다.

//Transition Manager
//Transition Manager를 사용하여 키 프레임별 전환 처리를 할 수 있다.
//전환하려는 ConstraintSet을 Transition Manager으로 처리해주면 된다. 사용자 정의 애니메이션을 만들 수도 있다.




//Animating the Bounds of a View
//제약 조건을 변경하여 View의 요소 위치를 변경하거나 크기를 변경할 수도 있다.
//시작 레이아웃을 keyframe1.xml 최종 레이아웃을 activity_main.xml 으로 보고, 해당 부분을 변경해 주면 된다.




//Using Custom Transitions to Make Animation Easier
//처음 로드 될때, View가 자동으로 애니메이션 되도록 구현해 줄 수도 있다.
//기본 애니메이션 대신 사용자 지정 애니메이션을 만들고 타이밍을 직접 지정해 준다.
//onEnterAnimationComplete() 함수를 추가해 준다.




//Animating the Circular Constraint
//원형으로 움직이는 애니메이션을 적용하려면, 제약 조건 외에 각도, 회전 등을 고려해야 한다.


