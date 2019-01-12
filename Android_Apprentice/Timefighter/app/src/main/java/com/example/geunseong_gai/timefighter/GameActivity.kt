package com.example.geunseong_gai.timefighter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }
}
//Chapter 1: Setting Up Android Studio

//Setting up an Android device p.38
//실제 안드로이드 디바이스를 연결하려면, 기기의 설정에서 Setting > About Phone 에서 build number를 눌러 메시지가 뜨도록 해야 한다.
//그러면 Setting에서 Developer Options 탭이 활성화되는데, USB Debugging을 토글해 설정해 줘야 한다.

//Running the app
//project navigator에서 app ‣ res ‣ layout ‣ activity_game.xml을 클릭하면 레이아웃을 볼 수 있다.
//하단 탭의 Design | Text 를 선택해 전환할 수 있다.

//Installing new versions of Android studio
//Preferences ‣ Appearance & Behavior ‣ System Settings ‣ Android SDK 에서 최신 SDK를 관리한다.




//Chapter 2: Layouts
//두 가지 방법 중 하나로 레이아웃을 만들 수 있다.
//1. XML 파일을 사용해 사용자 인터페이스를 선언할 수 있다.
//2. 앱을 실행할 때, 레이아웃을 코드로 작성한다.

//The Visual Editor
//activity_game.xml 에서 레이아웃을 편집한다. 하단 탭의 Design | Text 를 선택해 전환할 수 있다.
//Palette 탭에서 원하는 컴포넌트를 검색하고 drag and drop 으로 화면에 추가해 줄 수 있다.
//iOS의 스토리보드나, xib와 비슷하다.

//Component Tree View
//컴포넌트가 많아지면, 하단의 Component Tree를 사용해 원하는 계층 구조에 컴포넌트를 삽입해 줄 수 있다.
//iOS에서 컴포넌트를 추가해 주는 것과 비슷하다.

//Positioning your views
//단순히 배치하기만 한 컴포넌트의 위치를 디바이스가 알 수 없다.

//Adding rules to your position
//따라서 배치한 컴포넌트의 위치에 대한 제약 조건을 추가해 줘야 한다.
//Stack 모양의 아이콘을 클릭하면, Design, BluePrint 중 선택할 수 있으며, 동시에 볼 수도 있다.
//BluePrint에서 해당 컴포넌트의 제약조건을 추가해 줘야 한다.
//BluePrint에서 해당 컴포넌트를 선택하면 네 방향에 흰색 윤곽과 모서리에 크기 조절 사각형, 둥근 원의 Create Connection 버블이 나타난다.
//클릭해서 BluePrint의 가장자리 쪽으로 드래그 하면 해당 컴포넌트가 움직이는데, 이때 마우스를 놓으면 레이아웃 제약조건을 추가해 줄 수 있다.
//이후, 우측의 Attribute 윈도우에서 해당 제약조건의 세부조건을 추가해 주거나 수정할 수 있다.
//iOS에서 스토리보드에 제약조건을 추가해 주는 것과 비슷하다.

//Finishing the screen
//네 방향으로 모두 제약조건을 추가하면 중앙으로 정렬이 된다.

//Where to go from here?
//제약조건은 ConstraintLayout 으로 구현된다. 이외에도 LinearLayout, FrameLayout 등이 있지만, ConstraintLayout를 사용하는 것이 좋다.

