package com.hello.world.listmaker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ListItemsRecyclerViewAdapter(var list: TaskList) : RecyclerView.Adapter<ListItemViewHolder>() {
    //ListDetailActivity에서 TaskList를 전달했으므로, 생성자에서 이를 받을 수 있도록 수정해 줘야 한다.
    //RecyclerView.Adapter<ViewHolder> 인터페이스를 구현해야 하므로, ViewHolder 또한 작성해 줘야 한다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder { //레이아웃 생성
        //RecyclerView가 ViewHolder로 사용할 레이아웃을 알려준다.
        var view = LayoutInflater.from(parent.context) //LayoutInflater는 레이아웃 xml파일을 해당 view 객체로 인스턴스화한다.
            .inflate(R.layout.task_view_holder, parent, false) //해당 xml 파일을 가져온다.
            //레이아웃 ID, 부모 ViewGroup

        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int { //RecyclerView의 항목수를 반환한다.
        return list.tasks.size
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
//        if (holder != null) {
           holder.taskTextView.text = list.tasks[position] //데이터를 viewHolder에 연결한다.
            //RecyclerView가 표시하는 행에 따라 List에서 특정 요소를 가져온다.
//        }
    }
}

//Coding the RecyclerView
//kt 파일이 있는 패키지에서 우클릭 ▸ New ▸ Kotlin File/Class 으로 해서, 이름을 정하고, Kind는 Class로 해서 Adpator로 사용할 class를 생성한다.

//Adapting the Adapter
//Adpator를 연결할 때, RecyclerView에게 각 목록을 표시하는 방법을 알려줘야 한다.
//Android Studio에서 이 작업을 쉽게 할 수 있다. 빨간 밑줄이 그어져 있는 클래스 이름을 클릭하고, Alt + Return을 누른다.
//Implement Members를 누르면, 구현해야할 멤버들을 표시해 준다. 여기서 필요한 것을 선택해 확인을 누르면 보일러 플레이트 코드가 보여진다.
//res/layout에서 우클릭 해서 New ▸ Layout resource file 을 선택해 준다.
//여기서는 Root element에 LinearLayout을 입력해 준다.
//LinearLayout은 세로, 가로 방향으로 뷰를 쌓을 수 있다.
//iOS에서의 xib와 비슷하게 개별적인 객체의 레이아웃을 정의해 주는 파일이라 생각하면 된다.
//레이아웃 에디터에서 이전에 했던 대로, 컴포넌트를 추가하고, ID와 제약조건을 추가해 준다.
//Attribute창 상단 우측에 검색 옆에 View All Attributes 버튼이 있다. 이를 클릭하면, 변경할 수 있는 모든 속성을 볼 수 있다.
//여기서는 Layout_Margin의 all을 선택해, 패딩을 줄 수 있다.
//https://developer.android.com/guide/practices/screens_support
