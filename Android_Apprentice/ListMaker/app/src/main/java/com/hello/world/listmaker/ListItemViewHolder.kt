package com.hello.world.listmaker

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //생성자에 ViewHolders 위젯을 참조하는 데 사용하는 View를 전달한다.
    //RecyclerView.ViewHolder(itemView) 인터페이스를 구현한다.
    val taskTextView = itemView?.findViewById(R.id.textview_task) as TextView //레이아웃에서 textview_task 참고
}

//Coding the RecyclerView
//kt 파일이 있는 패키지에서 우클릭 ▸ New ▸ Kotlin File/Class 으로 해서, 이름을 정하고, Kind는 Class로 해서 ListItemViewHolder를 생성한다.

//Visualizing the ViewHolder
//List_Detail에서도 목록을 추가할 수 있는 FAB를 추가해 준다. 계층구조에서 ConstraintLayout에 추가해 줘야 한다.
//blueprint에서 FAB를 알맞은 위치로 (대부분의 경우 우측 하단) 옮겨주고 ID와 제약조건을 추가해 주면 된다.
