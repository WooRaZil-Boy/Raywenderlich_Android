package com.hello.world.listmaker

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_list.*

class MainActivity : AppCompatActivity(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {
    companion object {
        val INTENT_LIST_KEY = "list"
        val LIST_DETAIL_REQUEST_CODE = 123
    }

    lateinit var listsRecyclerView: RecyclerView //list를 생성하는 View
    //lateinit 키워드로 컴파일 시, 이후에 생성되게 될 것을 알려줘 에러가 나지 않도록 한다.
    //RecyclerView를 사용하면, 많은 양의 데이터를 표시할 수 있다. 각 데이터 조각들은 RecyclerView 내의 항목으로 취급되어 내용을 구성한다.
    val listDataManager: ListDataManager = ListDataManager(this) //데이터 관리 객

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        //The components of a RecyclerView
        val lists = listDataManager.readLists() //리스트를 가져온다.
        listsRecyclerView = findViewById(R.id.lists_recyclerview) //ID로 listsRecyclerView 객체를 가져온다.
        listsRecyclerView.layoutManager = LinearLayoutManager(this) //레이아웃의 종류를 알려준.
        //이 외에도 GridLayoutManager, StaggeredGridLayoutManager 을 사용할 수도 있다.
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this) //Adapter를 지정해 준다.
        //RecyclerView는 아이템 리스트를 표현하기 위한, 필수 구성 요소인 Adapter와 ViewHolder가 있다. p.127
        // 1. RecyclerView는 Adapter에 지정된 항목에 있는 item 혹은 ViewHolder를 요구한다.
        // 2. Adapter가 작성된 ViewHolder pool에 도착한다.
        // 3. 새 ViewHolder가 반환되거나 만들어 진다.
        // 4. Adapter는 이 ViewHolder를 주어진 위치의 데이터 항목에 바인딩 한다.
        // 5. ViewHolder가 표시되기 위해 RecyclerView로 반환된다.

       //일반적으로 Adapter는 RecyclerView에 표시할 데이터를 제공한다.
        //ViewHolder는 해당 항목의 시각적 컨테이너이다. 표의 cell이라 생각하면 된다. 여기서는 RecyclerView에 각 항목의 모양을 알려 준다.
        //ViewHolder는 데이터 목록의 주어진 위치에 데이터를 표시하는 데 사용하는 작은 레이아웃 항목이다.
        //RecyclerView를 스크롤하면, 새로운 ViewHolder를 만드는 대신, 보이는 화면 영역 밖의 ViewHolders를 재사용해 새 데이터를 채워 list의 아래에 사용한다.
        //이 프로세스는 RecyclerView를 스크롤 할 때 끝없이 반복된다. ViewHolder를 재활용하여 list를 표현하면 효율적이다.


        //Adding a Dialog
        fab.setOnClickListener{ view -> //fab 클릭시 이벤트 추가
            showCreateListDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    //Adding a Dialog
    private fun showCreateListDialog() {
        //FAB를 누르면, dialog가 뜨도록 한다. dialog의 문자열을 하드코딩해도 되지만, strings.xml을 이용하는 것이 좋다.
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)
        //strings.xml에서 정의한 문자열을 가져온다.

        var builder = AlertDialog.Builder(this) //UIAlertController
        var listTitleEditText = EditText(this) //UITextView, UITextField
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT //입력 유형을 선택해 주면, 키보드 타입이 변경된다.

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        builder.setPositiveButton(positiveButtonTitle) { dialog, i -> //버튼을 추가한다. //반대 작업을 하는 negative button도 있다.
            val list = TaskList(listTitleEditText.text.toString())
            listDataManager.saveList(list)
            //입력한 목록의 이름을 가져와 Tasklist를 생성한다.

            val recyclerViewAdapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
            recyclerViewAdapter.addList(list) //adaptor를 사용해 표시해야할 항목을 추가해 준다.

            dialog.dismiss()
            showListDetail(list)
        }
        builder.create().show() //화면에 표시한다.
    }

    private fun showListDetail(list: TaskList) {
        //Intents
        //Android OS는 Intent를 사용한 전달 방식을 주로 사용하므로, Activity 간의 통신이 필요한 경우 가장 먼저 Intent를 고려하는 것이 좋다.
        val listDetailIntent = Intent(this, ListDetailActivity::class.java) //Intent 생성
        //Intent 생성자에 현재 Activity와 표시하려는 Activity 클래스를 전달한다.
        listDetailIntent.putExtra(INTENT_LIST_KEY, list) //Extra 추가
        //Extra는 receiver가 수행할 작업에 대한 정보 제공하는 key-value 쌍이다.
//        startActivity(listDetailIntent) //해당 Intent를 가지고 다음 Activity로 전환한다.
//        //단순히 다음 Activity를 시작하는 용도

        startActivityForResult(listDetailIntent, LIST_DETAIL_REQUEST_CODE) //해당 Intent를 가지고 다음 Activity로 전환한다.
        //다음 Activity를 사용해 어떤 결과값을 받는 용도. 즉, ListDetailActivity가 종료되면 MainActivity로 result와 함께 돌아온다. p.187
        //두 번째 인자로 전달하는 값은 result를 식별할 수 있는 request code 이다.
    }

    override fun listItemClicked(list: TaskList) { //선택 시 이벤
        showListDetail(list)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LIST_DETAIL_REQUEST_CODE) { //request code 확인
            data?.let { //data가 null인지 여부 확인
                //https://kotlinlang.org/docs/reference/null-safety.html
                listDataManager.saveList(data.getParcelableExtra(INTENT_LIST_KEY)) //업데이트할 항목이 있으면 Data model에서 업데이트
                updateLists() //view 업데이트
            }
        }
    }

    private fun updateLists() {
        val lists = listDataManager.readLists()
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
        //새로운 데이터를 불러와 view를 업데이트 한다.
    }
}

//Chapter 6: Creating a New Project

//Getting started
//안드로이드 스튜디오를 실행했을떄, Profile or debug APK 메뉴를 선택하면, 기기의 파일 시스템에서 .apk 파일을 선택하고
//기기 또는 에뮬레이터에서 실행하여 앱에 대한 유용한 정보를 수집하고 개산할 수 있는 옵션을 제공해 준다.
//수집할 수 있는 정보는 앱의 크기, 내용, 메모리 사용, 네트워크 활동 같은 런타임 중에 수집된 정교한 정보들도 포함된다.

//Targeting Android devices
//지원하는 최소 SDK 를 설정할 때, 최신 버전은 더 많은 기능을 지원하지만, 사용자 수가 충분하지 않을 수 있다.
//이 떄, Help me choose 를 클릭하면 각 안드로이드 SDK 버전 별 점유율을 확인해 볼 수 있다.
//레이아웃 파일을 생성할 때의 규칙은 연결된 객체의 일반적인 이름이 먼저 오게 된다. ex.FooView - view_foo.xml




//Chapter 7: RecyclerViews
//안드로이드에서 List를 구현하는 가장 간단한 방법은 RecyclerView를 사용하는 것이다.

//Getting started
//이전 앱과 달리, xml 파일이 두 개 (activity_list.xml, content_main.xml) 있다.
//activity_list.xml의 component tree를 살펴보면, Toolbar, FloatingActionButton와 함께 include 라는 항목이 있다.
//include에 content_main.xml이 연결되어 있는 구조로, activity_list.xml 레이아웃은 content_main.xml에 정의된 레이아웃을 포함한다.
//이런 방법은 앱이 복잡할 경우, 여러 파일로 나눠 관리할 때 유용하다.

//Adding a RecyclerView
//리스트 작성은 RecyclerView를 사용한다. 이전과 같이 컴포넌트를 drag-and-drop 해주고, ID를 설정해 준 후,
//layout_width과 layout_height를 match_parent로 해서 채워준다. //콤보 박스에 안 나올 때도 있는데, text로 직접 넣어줘도 된다.

//The components of a RecyclerView
//RecyclerView를 사용하면, 많은 양의 데이터를 표시할 수 있다. 각 데이터 조각들은 RecyclerView 내의 항목으로 취급되어 내용을 구성한다.
//RecyclerView는 아이템 리스트를 표현하기 위한, 필수 구성 요소인 Adapter와 ViewHolder가 있다. p.127
// 1. RecyclerView는 Adapter에 지정된 항목에 있는 item 혹은 ViewHolder를 요구한다.
// 2. Adapter가 작성된 ViewHolder pool에 도착한다.
// 3. 새 ViewHolder가 반환되거나 만들어 진다.
// 4. Adapter는 이 ViewHolder를 주어진 위치의 데이터 항목에 바인딩 한다.
// 5. ViewHolder가 표시되기 위해 RecyclerView로 반환된다.
//일반적으로 Adapter는 RecyclerView에 표시할 데이터를 제공한다.
//ViewHolder는 해당 항목의 시각적 컨테이너이다. 표의 cell이라 생각하면 된다. 여기서는 RecyclerView에 각 항목의 모양을 알려 준다.
//ViewHolder는 데이터 목록의 주어진 위치에 데이터를 표시하는 데 사용하는 작은 레이아웃 항목이다.
//RecyclerView를 스크롤하면, 새로운 ViewHolder를 만드는 대신, 보이는 화면 영역 밖의 ViewHolders를 재사용해 새 데이터를 채워 list의 아래에 사용한다.
//이 프로세스는 RecyclerView를 스크롤 할 때 끝없이 반복된다. ViewHolder를 재활용하여 list를 표현하면 효율적이다.
//janking 이라는 용어는 렌더링 동안 손실되거나 누락된 프레임을 나타내는 데 사용하는 용어이다.
//ex. 긴 list를 스크롤하는 동안 끊김이 발생하는 경우, jank가 발생한 것이다.

//Setting up a RecyclerView Adapter
//Project Navigator에서 해당 package를 선택한 후, 우클릭 New ▸ Kotlin File/Class 로 새 파일을 생성할 수 있다.
//이때, 생성되는 파일의 Kind를 class로 선택해 준다.
//생성된 클래스에서 클래스 이에 커서를 올리고, option + Enter 를 하면, 필요한 추가 메서드를 자동으로 추가할 수 있다.
//Implement Members 를 클릭해 필요한 메서드를 추가해 준다.

//Where to go from here?
//https://developer.android.com/guide/topics/ui/layout/recyclerview.html




//Chapter 8: SharedPreferences
//오른쪽 하단의 둥근 단추를 Floating Action Button(FAB)라 한다. FAB를 사용하여 화면에서 중요한 동작을 강조할 수 있다.
//이 앱에서는 List를 생성하는 것이 가장 중요하기 때문에, 이 버튼에 목록 추가 로직을 넣어주는 것이 좋다.
//적합한 아이콘으로 바꾸기 위해서는 activity_list.xml의 Component Tree에서  Floating Action Button을 선택한다.
//우측의 Attributes 창의 srcCompat 필드에 이미지를 넣어주면 된다. ... 버튼을 눌러 아이콘을 검색할 수 있다.

//Adding a Dialog
//FAB를 누르면, dialog가 뜨도록 한다. dialog의 문자열을 하드코딩해도 되지만, strings.xml을 이용하는 것이 좋다.

//Creating a list
//앱 전체에 사용할 list모델을 생성한다.
//kt 파일이 있는 패키지에서 우클릭 ▸ New ▸ Kotlin File/Class 으로 해서, 이름을 정하고, Kind는 Class로 해주면 된다.
//디바이스에 list를 저장해야 할때, SharedPreferences를 사용한다.
//SharedPreferences를 사용하면, key-value collection으로 데이터를 저장할 수 있다. //UserDefault 와 비슷
//다른 앱에서 SharedPreferences에 접근할 수도 있다. 복잡한 데이터를 저장할 때는 다른 방법을 사용해야 한다.
//데이터를 관리할 클래스를 따로 분리해 주면 좋다.

//Hooking up the Activity
//데이터를 ListDataManager가 관리하므로, 참조할 수 있는 객체를 추가해 준다.
//list를 새로 생성한 후, 다시 앱을 재실행 해서 데이터가 제대로 남아서 표시되는지 확인해봐야 한다.




//Chapter 9: Communicating Between Activities
//앱이 커지면, 기능에 따라 Activity를 분할해 주는 것이 좋다.

//Getting started
//하나의 Activity는 하나의 작업에만 초점을 맞춰야 논리가 깔끔하고 단순하게 유지된다(단일 책임 원칙).
//kt 파일이 있는 패키지에서 우클릭 ▸ New ▸ Activity ▸ Empty Activity를 선택한다(다른 Activity를 선택할 수도 있다).

//Creating a new Activity
//Activity 생성 창이 출력된다.
// • Activity Name : Activity의 이름. 클래스의 이름을 지정하는 데에도 사용한다.
// • Generate Layout File : Activity과 함께 사용할 xml 파일을 생성하는 옵션. 기본적으로 선택되어 있다.
// • Launcher Activity : 앱이 시작될 때 처음 표시되는 Activity로 설정한다(rootViewController). 설정을 변경할 수도 있다.
// • Backwards Compatibility (AppCompat) : Activity가 AppCompatActivity를 상속하도록 한다. 여러 안드로이드 버전에서 호환된다.
//  기본적으로 체크하는 것이 좋다.
// • Package Name : Activity가 생성될 때, 패키지를 선택한다.
// • Source Language : Activity에서 사용할 프로그래밍 언어를 선택한다.
//Activity Name를 변경하면, Layout File 이름도 변경된다.

//The App Manifest
//모든 안드로이드 앱에는 app manifest 파일이 있다. 파일이름은 반드시, AndroidManifest.xml 이어야 하며, 반드시 계층 구조의 올바른 위치에 있어야 한다.
//이 파일이 없으면 안드로이드 앱이 실행되지 않는다. project navigator에서 app ▸ manifests ▸ AndroidManifest.xml를 선택해 열 수 있다.
//여기서 manifests 폴더는 가상의 폴더이며, 실제로는 app/src에 위치하고 있다.
//manifest tag는 루트요소로 다른 모든 태그는 이 안에서 선언되어야 한다. package 선언도 이 안에 있다.
//application tag는 응용 프로그램에 사용할 아이콘, 이름, 정보가 들어 있다.
//앱의 모든 Activity는 activity tag로 선언되어 있어야 한다.
//activity tag 안의 intent-filter로 앱이 시작할 때, 처음 실행되는 Activity를 지정해 준다.
//위에서의 방법으로, Activity를 생성하게 되면, 자동으로 AndroidManifest에 해당 Activity를 추가한다.

//Intents
//Intent를 사용해 Activity 사이를 navigate할 수 있다. Intent는 어떤 시점에서 앱이 수행할 작업이나 액션을 캡슐화 해 주는 객체이다.
//Android OS는 Intent를 사용한 전달 방식을 주로 사용하므로, Activity 간의 통신이 필요한 경우 가장 먼저 Intent를 고려하는 것이 좋다.
//Intent는 매우 유연하며 다른 응용 프로그램과 통신하거나 프로세스에 데이터를 제공하고 시작하는 등 다양한 작업을 할 수 있다. p.162

//Intents and Parcels
//Activity를 전환하면서, Extra에 list를 넣어 보냈지만, 안드로이드는 그 유형의 객체를 알 수 없기 때문에, TaskList 선언을 변경해 줘야 한다.
//Parcelable 인터페이스를 구현해 Intent에서 사용할 수 있도록 해야 한다.
//Parcelable은 String, Int 등의 유형으로 객체응 분류할 수 있다.
//https://developer.android.com/reference/android/os/Parcelable
//Kotlin 1.1.4에서는 @Parcelize를 주석으로 달아 일일이 Parcelable을 구현할 필요 없다(아직 베타 버전).




//Chapter 10: Completing the Detail View

//Getting started
//activity_list_detail.xml 파일의  Palette 창에서 AppCompat ▸ RecyclerView를 선택한다.
//ID와 제약조건을 설정해 준다 (match_constraints).

//Coding the RecyclerView
//kt 파일이 있는 패키지에서 우클릭 ▸ New ▸ Kotlin File/Class 으로 해서, 이름을 정하고, Kind는 Class로 해서 사용할 class를 생성한다.

//Adapting the Adapter
//Android Studio에서 구현해야하는 멤버를 쉽게 알수 있다. 빨간 밑줄이 그어져 있는 클래스 이름을 클릭하고, Alt + Return을 누른다.
//Implement Members를 누르면, 구현해야할 멤버들을 표시해 준다. 여기서 필요한 것을 선택해 확인을 누르면 보일러 플레이트 코드가 보여진다.

//Visualizing the ViewHolder
//List_Detail에서도 목록을 추가할 수 있는 FAB를 추가해 준다. 계층구조에서 ConstraintLayout에 추가해 줘야 한다.
//blueprint에서 FAB를 알맞은 위치로 (대부분의 경우 우측 하단) 옮겨주고 ID와 제약조건을 추가해 주면 된다.

//Getting the list back
//세부 아이템을 list에 추가하는 것은 ListDataManager를 생성해 MainActivity.kt 에서 한 것처럼 똑같이 해 줄 수 있다.
//하지만, 데이터가 저장되는 별도의 지점이 두 개가 생겨 버그가 발생할 확률이 높아질 수 있다.
//해당 list를 detail activity로 전달하고 원래의 Data manager를 사용하는 것이 더 좋은 구현이다(DI).
//https://kotlinlang.org/docs/reference/null-safety.html









