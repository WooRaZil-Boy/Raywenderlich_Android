package com.hello.world.listmaker

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ListSelectionFragment : Fragment(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {
    //ListSelectionRecyclerViewClickListener를 구현하여, 선택된 list를 Activity로 전달해 준다.
    lateinit var listDataManager: ListDataManager //데이터 관리 객리
    lateinit var listsRecyclerView: RecyclerView //list를 생성하는 View
    //lateinit 키워드로 컴파일 시, 이후에 생성되게 될 것을 알려줘 에러가 나지 않도록 한다.
    //RecyclerView를 사용하면, 많은 양의 데이터를 표시할 수 있다. 각 데이터 조각들은 RecyclerView 내의 항목으로 취급되어 내용을 구성한다.

    private var listener: OnListItemFragmentInteractionListener? = null
    //Fragment 인터페이스 구현하는 객체에 대한 참조.

    interface OnListItemFragmentInteractionListener { //인터페이스. MainActivity가 이 인터페이스를 구현한다.
        fun onListItemClicked(list: TaskList) //클릭 시 알리는 메서드를 구현해야 한다.
    }

    companion object { //Fragment의 새 인스턴스를 생성하는 모든 객체에서 공동으로 사용된다.
        fun newInstance() : ListSelectionFragment {
            val fragment = ListSelectionFragment()
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        //Fragment가 Activity와 처음 연결될 때 호출된다(Life cycle에서 첫 번째 메서드).
        //따라서 Fragment가 생성되기 전에 필요한 모든 것을 설정해 줄 수 있다.
        super.onAttach(context)

        if (context is OnListItemFragmentInteractionListener) {
            listener = context //context를 listener 변수에 할당한다.
            //context는 Fragment를 포함하는 MainActivity이다.
            listDataManager = ListDataManager(context)
        } else {
            throw RuntimeException(context.toString() + " must implement OnListItemFragmentInteractionListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) { //Activity의 onCreate 메서드와 역할이 유사하다.
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //Fragment가 Activity 내에서 표현되기 위해 가지고 있어야 하는 레이아웃을 가져온다.
        return inflater.inflate(R.layout.fragment_list_selection, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        //Fragment가 attached 되고, onCreate()이 완료됐을 때 호출되는 Life cycle 메서드
        super.onActivityCreated(savedInstanceState)

        val lists = listDataManager.readLists() //리스트를 가져온다.
        view?.let { //nullable에서 null이 해제된 객체를 it으로 가져 올 수 있다. //Swift에서 let it 으로 바인딩 했다고 생각하면 된다.
            listsRecyclerView = it.findViewById(R.id.lists_recyclerview) //ID로 listsRecyclerView 객체를 가져온다.
            listsRecyclerView.layoutManager = LinearLayoutManager(activity) //레이아웃의 종류를 알려준다.
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
        }
    }

    override fun onDetach() { //Fragment가 Activity와의 연결이 끊어질때 호출되며 Life cycle에서 최종 메서드이다.
        super.onDetach()

        listener = null //더 이상 Activity와 연결되지 않으므로 null로 설정한다.
    }

    override fun listItemClicked(list: TaskList) {
        listener?.onListItemClicked(list)
        //RecyclerView Adapter에서 선택 하면, listener를 사용해, Activity에 알린다.
        //Activity는 list를 수신하고, 앱을 유지하면서 list를 표시하는 새 Activity를 시작할 수 있다.

        //전체 list에서 선택된 list를 알려줘야 한다.
        //MainActivity에 ListSelectionRecyclerViewAdapter가 더 이상 존재하지 않으므로 Fragment에서 구현해 줄 수 있다.
        //그러나 Activity에서는 여전히 어떤 list가 선택되었는지 알고 있어야 한다.
        //왜냐하면, Activity만이 다른 Activity를 시작할수 있기 때문이다. 분리된 View인 Fragment에서는 처리하는 이벤트의 Activity만을 알린다.
    }

    fun addList(list: TaskList) {
        listDataManager.saveList(list) //입력한 목록의 이름을 가져와 Tasklist를 생성한다.

        val recyclerAdapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
        recyclerAdapter.addList(list) //adaptor를 사용해 표시해야할 항목을 추가해 준다.
    }

    fun saveList(list: TaskList) {
        listDataManager.saveList(list) //업데이트할 항목이 있으면 Data model에서 업데이트
        updateLists() //view 업데이트
    }

    private fun updateLists() {
        val lists = listDataManager.readLists()
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
        //새로운 데이터를 불러와 view를 업데이트 한다.
    }
}

//Creating a Fragment
//project navigator에서 해당 패키지를 우클릭하고, New ▸ Fragment ▸ Fragment (Blank) 를 선택한다.
//Fragment를 Activity와 비슷한 것으로 생각하면 된다. Activity 생성 때와 마찬가지로 생성 시 옵션을 추가해 줄 수 있는 창이 뜬다.
// • Fragment Name : Fragment 명을 입력한다. 아래의 xml 이름도 자동으로 바뀌게 된다.
// • Create Layout XML : xml 파일을 추가할 지 체크박스. 보통은 default로 선택한다.
// • Include fragment factory methods : Fragment를 인스턴스화하는 factory 메서드를 추가한다.
// • Include interface callbacks : 다른 객체가 Fragment에서 콜백을 수신할 수 있도록 인터페이스를 생성한다.
// • Source Language : Kotlin을 선택한다.

//What is a Fragment?
//Fragment는 Activity의 사용자 인터페이스 일부이며, Activity에 자체 레이아웃을 제공한다.
//이 레이아웃으로 앱이 실행되는 동안 앱에서 동적으로 사용자 인터페이스를 추가하고 제거할 수 있다.
//예를 들어 화면 크기에 따라 Activity 몇 개의 Fragment를 가져야 하는 지 결정할 수 있다.
//해당 앱에서는, 태블릿에서 실행한 경우, Fragment 중 하나만 Activity에 표시하고, List를 클릭한 경우 다음 Activity를 표시할 수 있다.
//Fragment에 포함된 Activity의 Life cycle과 다른 Fragment 자신만의 Life cycle이 있다.
//Fragment가 런타임에 표시되는지 여부를 알 수 없으므로, 가능한 self contained 되도록 구현하 것이 중요하다.
//따라서 첫 Fragment를 생성할 때 콜백 인퍼체이스를 생성하도록 설정했다. 이것으로 다른 객체와 통신하게 된다.
//https://developer.android.com/guide/components/fragments
