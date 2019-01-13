package com.hello.world.listmaker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ListSelectionRecyclerViewAdapter : RecyclerView.Adapter<ListSelectionViewHolder>() {
    //RecyclerView.Adapter<ListSelectionViewHolder>에서 <> 사이에 구현하는 viewHolder를 지정해 주면 된다.
    val listTitles = arrayOf("Shopping List", "Chores", "Android Tutorials")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder { //레이아웃을 생성한다.
        val view = LayoutInflater.from(parent.context) //LayoutInflater 객체를 사용해 코드로 레이아웃을 생성한다.
            //Activity의 컨텍스트를 사용한다. //LayoutInflater는 레이아웃 xml파일을 해당 view 객체로 인스턴스화한다.
            .inflate(R.layout.list_selection_view_holder, parent, false)
            //레이아웃 ID, 부모 ViewGroup

        return ListSelectionViewHolder(view)
        //생성된 뷰로 ListSelectionViewHolder를 생성해 반환하면, Adapter가 관련 정보를 채운다.
    }

    override fun getItemCount(): Int { //RecyclerView의 항목수를 반환한다.
        return listTitles.size
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) { //개별의 ViewHolder에 바인딩한다.
//        if (holder != null) { //null이 아닌지 확인
            holder.listPosition.text = (position + 1).toString()
            holder.listTitle.text = listTitles[position]
//        }

        //RecyclerView를 스크롤할 때마다 반복해서 호출된다.
    }

    //이거 Nullable로 생성하면 인자가 다름..
}

//클래스 명에 커서를 올리고, option + Enter 를 하면, 필요한 추가 메서드를 자동으로 추가할 수 있다.
//Implement Members 를 클릭해 필요한 메서드를 추가해 준다.

//Creating the ViewHolder
//res/layout에서 우클릭 해서 New ▸ Layout resource file 을 선택해 준다.
//여기서는 ListView에 사용할 Layout을 생성하므로 Root element에 LinearLayout을 입력해 준다.
//iOS에서의 xib와 비슷하게 개별적인 객체의 레이아웃을 정의해 주는 파일이라 생각하면 된다.
//레이아웃 에디터에서 이전에 했던 대로, 컴포넌트를 추가하고, ID와 제약조건을 추가해 준다.
//Attribute창 상단 우측에 검색 옆에 View All Attributes 버튼이 있다. 이를 클릭하면, 변경할 수 있는 모든 속성을 볼 수 있다.
//여기서는 Layout_Margin의 all을 선택해, 패딩을 줄 수 있다.
//https://developer.android.com/guide/practices/screens_support
//기본적으로 하위 컴포넌트들은 세로로 정렬된다. 가로로 정렬되게 하고 싶을 때는 Component Tree에 부모 컴포넌트를 선택하고
//Attributes 창에서 orientation를 horizontal로 변경해 준다.

