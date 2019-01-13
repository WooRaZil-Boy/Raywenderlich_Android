package com.hello.world.listmaker

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class ListSelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //기본 생성자 추가
    val listPosition = itemView?.findViewById<TextView>(R.id.itemNumber) as TextView
    val listTitle = itemView?.findViewById<TextView>(R.id.itemString) as TextView
}

//Binding data to your ViewHolder
//이 클래스는 하나의 항목을 나타낸다. //iOS의 UITableViewCell로 생각하면 된다.
