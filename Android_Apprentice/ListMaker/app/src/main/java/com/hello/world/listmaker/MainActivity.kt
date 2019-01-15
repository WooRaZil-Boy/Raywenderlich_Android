package com.hello.world.listmaker

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

class MainActivity : AppCompatActivity() {
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
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists) //Adapter를 지정해 준다. //lists 전
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
        }
        builder.create().show() //화면에 표시한다.
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







