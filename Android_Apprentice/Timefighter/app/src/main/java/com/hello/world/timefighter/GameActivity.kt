package com.hello.world.timefighter

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class GameActivity : AppCompatActivity() { //GameActivity는 AppCompatActivity를 확장한다.
    //해당 앱은 Activity가 하나인 간단한 앱이고, GameActivity가 시작 Activity이자 유일한 Activity이다.
    internal val TAG = GameActivity::class.java.simpleName //클래스 이름을 반환한다.
    //로그 메시지에서 클래스 이름을 사용하여, 메시지가 어떤 클래스에서 출력되었는지 쉽게 알 수 있다.

    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    internal lateinit var tapMeButton: Button
    //internal은 동일한 모듈 내에서만 액세스 가능하다.
    //lateinit는 일반적으 사용하는 lazy와 달리, Non-null 이지만 아직 값이 없는 상태에서 컴파일러가 이를 인식해 정상적으로 컴파일 되도록 한다.
    //.isInitialized 를 사용해, 해당 속성이 초기화되었는지 확인할 수 있다.

    internal var score = 0
    internal var gameStarted = false

    internal lateinit var countDownTimer: CountDownTimer //카운트 다운 타이머
    internal var initialCountDown: Long = 60000
    internal var countDownInterval: Long = 1000
    internal var timeLeft = 60

    companion object { //Java, Swift의 static이라 생각하면 된다. class 인스턴스에 상관없이 공유되는 변
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
        //방향 변경(회전) 시, 저장할 변수들의 키.
    }

    override fun onCreate(savedInstanceState: Bundle?) { //onCreate 함수는 해당 Activity의 진입점이다.
        //AppCompatActivity의 메서드를 override한다.
        super.onCreate(savedInstanceState)
        //반드시 상위 클래스(AppCompatActivity)의 onCreate()를 호출해야 한다.
        //해당 Activity를 구현하기 전에 필요한 설정을 하고, 상위 클래스에 알려준다.

        setContentView(R.layout.activity_game)
        //사용자가 생성한 레이아웃을 가져와서 화면에 배치한다. 레이아웃 ID는 "R.layout.파일이름"이 된다.

        //여기까지가 Android에서 Activity를 생성하는 핵심이다. 가장 일반적이며, 다른 로직들은 setContentView() 호출 이후에 온다.

        Log.d(TAG, "onCreate called. Score is: $score")
        //Activity가 생성될 때, 메시지를 기록한다. 현재의 클래스와, score를 출력한다.
        //앱을 실행하면, 콘솔 창의 Logcat에서 디바이스가 수행하는 모든 작업을 볼 수 있다.
        //여기에는 응용 프로그램 외부에서 오는 메시지도 포함되며, 필요한 메시지만 필터링할 수도 있다.





        gameScoreTextView = findViewById(R.id.game_score_text_view)
        timeLeftTextView = findViewById(R.id.time_left_text_view)
        tapMeButton = findViewById(R.id.tap_me_button)
        //findViewById()는 activity_game 파일을 검색하여 해당 ID가 있는 view를 찾고 변수로 저장할 수 있는 참조를 제공한다.
        //findViewById()는 필요한 하위 클래스가 아닌 View로 반환하기 때문에 타입을 지정해 주는 것이 좋다.
        //ex. gameScoreTextView = findViewById<TextView>(R.id.game_score_text_view)
        //레이아웃에서 추가한 컴포넌트를 코드에서 사용할 수 있도록 변수에 할당해 준다.

        tapMeButton.setOnClickListener { v ->
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            //애니메이을 불러온다.
            v.startAnimation(bounceAnimation) //애니메이션 시작

            incrementScore()
        } //Lambda
        //클릭 또는 탭 했을 때의 listener를 연결한다. 즉, 버튼을 탭할 떄 마다 incrementScore()가 호출된다.

        if (savedInstanceState != null) { //savedInstanceState는 Bundle로 화면이 전환될 때 전달할 값의 Dictionary이다.
            //여기서는 화면이 회전한 경우, savedInstanceState에 값이 있게 된다.
            score = savedInstanceState.getInt(SCORE_KEY)
            timeLeft = savedInstanceState.getInt(TIME_LEFT_KEY)
            //onSaveInstanceState에서 저장한 값들을 복원해 준다.

            restoreGame()
        } else { //savedInstanceState가 null인 경우는 처음 앱을 시작했을 때이다.
            resetGame()
        }
    }
    //onCreate()가 Activity를 사용하는 유일한 진입점은 아니지만 가장 익숙해져야 한다.
    //onCreate()은 Activity의 Life-cycle을 구성하는 다른 메서드와 함께 작동한다.
    //https://developer.android.com/guide/components/activities/activity-lifecycle.html

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //디바이스를 회전할 때, 속성이 0으로 재설정 된다. 이는 안드로이드가 기기 방향 변경을 처리하는 방식과 연관이 있다.
        //안드로이드는 방향 변경을 감지할 때마다 세 가지 작업을 수행한다.
        // 1. Activity의 property를 저장 한다.
        // 2. Activity를 메모리에서 해제한다.
        // 3. onCreate를 호출하여 새로운 방향에 대한 Activity를 다시 시작한다.
        //안드로이드는 설정이 변경될 때마다 이를 수행한다(ex. 방향 변경, 언어 변경 ...).
        //따라서 앱을 사용하는 동안 Activity가 여러 번 삭제되고 생성될 수 있다. 이런 설정 변경이 있을 때마다 이전 상태를 복구할 수 있도록 개발해야 한다.

        //시스템은 Activity가 중단될 때, onSaveInstanceState()를 호출해 상태 정보를 저장한다.
        //기본 구현은 위젯의 스크롤 위치와 같은 뷰 계층 구조 정보를 저장한다.
        //따라서, 설정이 변경될 떄 예기치 않게 삭제되는 값들을 추가해 줄 수 있다.

        outState.putInt(SCORE_KEY, score)
        outState.putInt(TIME_LEFT_KEY, timeLeft)
        //Bundle에 저장할 값을 삽입한다. key-value
        //Bundle은 안드로이드가 다른 화면으로 값을 전달할 때 사용하는 Dictionary이다.

        countDownTimer.cancel() //타이머 취소

        Log.d(TAG, "onSaveInstanceState: Saving Score: $score & Time Left: $timeLeft")
    }

    override fun onDestroy() { //Activity 가 해제될 때 호출된다.
        super.onDestroy()

        Log.d(TAG, "onDestroy called.")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //onCreateOptionsMenu는 Activity의 옵션 메뉴를 구성한다.
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.menu, menu) //Activity의 menuInflater를 사용해 레이아웃을 설정한다.
        //xml에서 작성한 menu를 가져온다. menuInflater는 xml파일을 Menu 객체로 인스턴스화 한다.

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //메뉴의 항목이 선택될 때마다 호출된다.
        if (item.itemId == R.id.action_settings) { //id가 이전에 설정한 항목의 id와 일치한다
            showInfo()
        }

        return true
    }




    private fun incrementScore() {
        if (!gameStarted) { //게임이 시작되지 않았다면 시작한다.
            startGame()
        }

        score++

//        val newScore = "Your Score: " + Integer.toString(score)
        val newScore = getString(R.string.your_score, Integer.toString(score)) //Integer.toString으로 캐스팅 한다.
        //strings.xml에 코드 전체의 문자열을 저장할 수 있다.
        //<string name="app_name">Timefighter</string> 와 같이 name에 식별자를 추가해 주고 해당 text를 쓰면 된다.
        //getString()으로 strings.xml에서 해당 id의 텍스트를 가져온다. //이를 구현해 놓으면, Localizing 하기 쉽다.
        //strings.xml의 your_score는 placeholder를 설정해 놓았다. 여기에서는 placeholder에 Integer.toString(score)이 대체되어 들어간다.

        gameScoreTextView.text = newScore
    }

    private fun resetGame() { //게임 기본 상태로 초기화
        score = 0

        val initialScore = getString(R.string.your_score, Integer.toString(score)) //Integer.toString으로 캐스팅 한다.
        //getString()으로 strings.xml에서 해당 id의 텍스트를 가져온다.
        gameScoreTextView.text = initialScore

        val initialTimeLeft = getString(R.string.time_left, Integer.toString(60)) //Integer.toString으로 캐스팅 한다.
        //getString()으로 strings.xml에서 해당 id의 텍스트를 가져온다.
        timeLeftTextView.text = initialTimeLeft

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval) { //싱글톤
            //60000, 1000 으로, 60초 동안, 1초 간격으로 시간이 줄어든다. //0에 도달 할때까지 계속된다.
            override fun onTick(millisUntilFinished: Long) { //타이머가 줄어들 때 마다(여기서는 1000ms, 1초) 호출
                //줄어든 시간이 millisUntilFinished로 전달 된다.
                timeLeft = millisUntilFinished.toInt() / 1000

                val timeLeftString = getString(R.string.time_left, Integer.toString(timeLeft))
                timeLeftTextView.text = timeLeftString
            }

            override fun onFinish() { //타이머가 종료될 때 호출
                endGame()
            }
        }

        gameStarted = false //게임 시작 flag 토글
    }

    private fun restoreGame() {
        val restoredScore = getString(R.string.your_score, Integer.toString(score))
        gameScoreTextView.text = restoredScore

        val restoredTime = getString(R.string.time_left, Integer.toString(timeLeft))
        timeLeftTextView.text = restoredTime

        countDownTimer = object : CountDownTimer((timeLeft * 1000).toLong(), countDownInterval) { //싱글톤
            //timeLeft * 1000, 1000 으로, timeLeft초 동안, 1초 간격으로 시간이 줄어든다. //0에 도달 할때까지 계속된다.
            override fun onTick(millisUntilFinished: Long) { //타이머가 줄어들 때 마다(여기서는 1000ms, 1초) 호출
                //줄어든 시간이 millisUntilFinished로 전달 된다.
                timeLeft = millisUntilFinished.toInt() / 1000

                val timeLeftString = getString(R.string.time_left, Integer.toString(timeLeft))
                timeLeftTextView.text = timeLeftString
            }

            override fun onFinish() { //타이머가 종료될 때 호출
                endGame()
            }
        }

        countDownTimer.start()
        gameStarted = true
    }

    private fun startGame() {
        countDownTimer.start() //countDownTimer를 실행한다.
        //앱이 시작할 때, resetGame가 호출 되고, resetGame에서 countDownTimer이 초기화 된다.

        gameStarted = true
    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.game_over_message, Integer.toString(score)), Toast.LENGTH_LONG).show()
        //Toast는 이벤트를 알리기 위해 일시적으로 팝업되는 알림이다.
        //context, string, duration을 지정해 주고, show()로 trigger 한다.
        //duration으로 지정할 수 있는 상수로는, LENGTH_SHORT, LENGTH_LONG이 있다.
        resetGame()
    }

    private fun showInfo() {
        var dialogTitle = getString(R.string.about_title, BuildConfig.VERSION_NAME)
        //getString()으로 strings.xml에서 해당 id의 텍스트를 가져온다.
        //앱을 컴파일 할때 설정들의 정보를 BuildConfig에서 가져온다. 여기에서는 about_title의 placeholder에 들어간다.
        val dialogMessage = getString(R.string.about_message)

        val builder = AlertDialog.Builder(this) //iOS의 UIAlertController와 비슷
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show() //dialog 표
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
//https://developer.android.com/training/constraint-layout/




//Chapter 3: Activities
//안드로이드에서 앱의 화면을 Activity라고 한다. Activity는 single task를 구축한다. //iOS의 UIViewController

//Getting started
//안드로이드에서 ID는 View와 같은 항목을 코드에 다시 연결하는 데 중요한 역할을 한다.
//Visual Editor에서 해당 컴포넌트를 선택하면, 우측의 Attribute 윈도우에서 속성을 볼 수 있다. 이곳에서 컴포넌트의 ID를 확인하고 수정할 수 있다.
//ID는 단순히 button 등 보다, 의미 있는 값으로 설정해 주는 것이 좋다.
//텍스트를 입력할 때, %s는 문자열의 placeholder 이다. 나중에 해당 placeholder를 코드로 채우게 된다.
//빌드 시에 Android Studio는 이러한 ID를 가져와 코드가 R 파일로 액세스할 수 있는 상수로 변환한다.
//즉, 뷰에 할당한 game_score_text_view 같은 ID가 R.id.game_score_text_view 라는 상수를 생성한다. 이 상수는 코드로 액세스할 수 있다.
//따라서 코드로 액세스해야 하는 컴포넌트에 유의미한 ID를 지정해 줘야 한다.

//Exploring Activities
//가끔 클래스에서 새 객체를 사용할 때 클래스를 인식하지 못하는 경우가 있다. 이 경우, 해당 객체를 클릭하고, Alt + Return 를 누르면 된다.
//Android Studio ▸ Preferences ▸ Editor ▸ General ▸ Auto Import 에서 자동으로 가져올 라이브러리를 설정해 줄 수도 있다.

//Hooking up the views
//레이아웃에서 추가한 컴포넌트를 변수에 연결 시켜 준다. findViewById()로 설정한 ID를 가진 뷰를 가져온다.

//Managing strings in your app
//String resources 로 Text를 Localizing 해 줄 수 있다. Project ▸ res/values/strings.xml 을 연다.
//strings.xml에 코드 전체의 문자열을 저장할 수 있다.
//<string name="app_name">Timefighter</string> 와 같이 name에 식별자를 추가해 주고 해당 text를 쓰면 된다.

//Where to go from here?
//앱의 크기와 상관없이, Activity를 생성하는 것은 다음과 같은 흐름을 따른다.
// 1. Activity의 레이아웃을 만든다.
// 2. Activity에서 view에 ID를 지정해 준다.
// 3. Activity 클래스에서 property를 설정하고, findViewById로 ID를 참조해 온다.
// 4. view에 로직을 추가해 준다.




//Chapter 4: Debugging

//Add some logging
//디버깅의 가장 기본적인 방법은 앱에 logging을 추가하는 것이다. 로깅은 코드의 특정 지점에서 어떤 일이 일어나고 있는지 알려 준다.
//앱을 실행하면, 콘솔 창의 Logcat에서 디바이스가 수행하는 모든 작업을 볼 수 있다.
//여기에는 응용 프로그램 외부에서 오는 메시지도 포함되며, 필요한 메시지만 필터링할 수도 있다.
//https://developer.android.com/studio/command-line/logcat.html

//Orientation changes
//디바이스를 회전할 때, 속성이 0으로 재설정 된다. 이는 안드로이드가 기기 방향 변경을 처리하는 방식과 연관이 있다.
//안드로이드는 방향 변경을 감지할 때마다 세 가지 작업을 수행한다.
// 1. Activity의 property를 저장 한다.
// 2. Activity를 메모리에서 해제한다.
// 3. onCreate를 호출하여 새로운 방향에 대한 Activity를 다시 시작한다.
//안드로이드는 설정이 변경될 때마다 이를 수행한다(ex. 방향 변경, 언어 변경 ...). 따라서 앱을 사용하는 동안 Activity가 여러 번 삭제되고 생성될 수 있다.
//이런 설정 변경이 있을 때마다 이전 상태를 복구할 수 있도록 개발해야 한다.

//Breakpoints //p.86
//일일이 로깅하지 않고, breakpoint(중단점)을 설정 해 앱의 실행을 일시 중지 시키고, 현재 상태를 검사할 수 있다.
//line num이 있는 회색 테두리(gutter)를 클릭하면 breakpoint(빨간 원)가 생성된다.
//이후, Run 버튼 옆의 Debug를 클릭하면, 설정한 중단점에서 일시 중지 된다.
//디버그 윈도우의 Variable 탭에서 해당 Activity를 선택해, 할당된 위치, property들의 값 등을 살펴 볼 수 있으며, console에 명령어를 입력할 수도 있다.
//해당 값들을 확인해 제대로 값들이 전달되고 저장되었는지 확인해 볼 수 있다.

//Restarting the game
//방향 변경을 감지하면, onCreate를 다시 호출하므로(Orientation changes 참고), onCreate()에서 이전 값을 복원해 준다.

//Where to go from here?
//https://developer.android.com/studio/debug/




//Chapter 5: Prettifying the App

//Changing the app bar color
//project navigator에서 app ‣ res ‣ values ‣ colors.xml를 클릭해, 색상을 설정해 줄 수 있다.
//앱에서 사용된 색상을 저장하는 데 사용되며, strings.xml과 유사하다.
//색상 속성은 R.java로 컴파일될 때 앱에서 참조로 사용할 name 속성과 함께 <color> 태그로 정의된다. 색상은 16진수로 표현할 수 있다.
//이렇게 설정된 색상은 app ‣ res ‣ values ‣ styles.xml에서 값을 변경해 줄 수 있다.
//<item> 태그에서 colors.xml에서 설정한 색상을 넣어준다.
//https://developer.android.com/guide/topics/ui/themes
//이후, activity_game.xml 에서 Design 대신 Text로 전환한 후, ConstraintLayout 태그를 업데이트하여 배경 색을 변경한다.

//Animations
//애니메이션에서 중요한 규칙은 어디서 그리고 언제 사용할 지 정하는 것이다.
//app ‣ res 에서 마우스 오른쪽 버튼을 눌러 New ‣ Android resource directory로 새 리소스 폴더를 만든다.
//Resource type을 anim으로 변경하고(자동으로 Directory name도 변경된다) 생성한다.
//다음으로 애니메이션을 정의하는 파일을 생성해야 한다. 방금 생성한 anim 폴더를 마우스 오른쪽 버튼으로 클릭하고 New ‣ Animation resource file 을 클릭한다.
//파일이름에 생성할 애니메이션의 이름을 적어주면 된다. set 속성은 동일한 애니메이션에서 둘 이상의 변환을 묶어 동시에 실행할 수 있도록 포함하는 컨테이너이다.
//https://developer.android.com/guide/topics/resources/animation-resource.html




//Adding a Dialog
//Dialog는 화면의 주요 콘텐츠에서 벗어나지 않고 사용자가 필요로 하는 정보를 제공해 주는 방법이다.
//가장 손쉬운 방법은 상단 표시 줄에 버튼을 설정해 주는 것이다.
//project navigator에서 app ‣ res 에서 마우스 우클릭하여 Android resource directory 폴더를 생성한다.
//anim 폴더를 생성했을 때와 비슷하지만, Resource Type을 menu로 선택해 준다.
//똑같이 New ‣ Menu resource file을 클릭해 파일을 생성한다.
//생성한 파일에서 Design 탭을 누르면 레이아웃 편집기와 유사한 설정을 볼 수 있다.
//좌측 상단의 Palette에서 필요한 컴포넌트를 가져와 드래그 하면 된다.
//레이아웃 편집기에서 컴포넌트를 추가했던 것 처럼 ID를 설정해 주고 알맞은 Text를 입력한다. icon 옆의 ... 버튼을 선택해 이미지를 포함할 수 있다.
//showAsAction 속성은 컴포넌트가 표시되는 방법을 정의한다. 역시 옆의 ... 버튼을 눌러 설정해 줄 수 있다.
//항상 표시되도록 하려면 always를 선택하면 된다.