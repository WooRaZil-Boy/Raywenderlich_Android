package com.hello.world.listmaker

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ListDetailFragment : Fragment() {
    lateinit var list: TaskList
    lateinit var listItemsRecyclerView: RecyclerView

    companion object {
        fun newInstance(list: TaskList) : ListDetailFragment {
            //해당 fragment는 세부 목록을 보여주기 때문에 TaskList를 전달 받아 처리한다.
            val fragment = ListDetailFragment()
            val args = Bundle()
            args.putParcelable(MainActivity.INTENT_LIST_KEY, list)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        list = arguments!!.getParcelable(MainActivity.INTENT_LIST_KEY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list_detail, container, false)

        view?.let {
            listItemsRecyclerView = it.findViewById(R.id.list_items_reyclerview)
            listItemsRecyclerView.adapter = ListItemsRecyclerViewAdapter(list)
            listItemsRecyclerView.layoutManager = LinearLayoutManager(activity)
        }

        return view
    }

    fun addTask(item: String) {
        list.tasks.add(item)

        val listRecyclerAdapter =  listItemsRecyclerView.adapter as ListItemsRecyclerViewAdapter
        listRecyclerAdapter.list = list
        listRecyclerAdapter.notifyDataSetChanged()
    }
}

//Creating your next Fragment
//선택한 list의 detail을 표시해 줄 Fragment를 추가해 준다.
//project navigator에서 해당 패키지를 우클릭하고, New ▸ Fragment ▸ Fragment (Blank) 를 선택한다.
//레이아웃 또한 이전 파트에서 처럼, xml에서 Text 탭을 선택해 필요한 위젯을 복사해서 옮겨주면 된다.