package com.hello.world.listmaker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.widget.EditText

class ListDetailActivity : AppCompatActivity() {
    lateinit var list: TaskList
    lateinit var listItemsRecyclerView : RecyclerView
    lateinit var addTaskButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list_detail)

        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY) //key를 사용해 intent에서 해당 value를 사져온다.
        title = list.name

        listItemsRecyclerView = findViewById(R.id.list_items_reyclerview) //id로 해당 레이아웃을 가져온다.
        listItemsRecyclerView.adapter = ListItemsRecyclerViewAdapter(list) //Adaptor 할당
        listItemsRecyclerView.layoutManager = LinearLayoutManager(this) //레이아웃 관리자 할당

        addTaskButton = findViewById(R.id.add_task_button) //id로 해당 레이아웃을 가져온다.
        addTaskButton.setOnClickListener { //클릭 시 실행될 콜백
            showCreateTaskDialog()
        }
    }

    private fun showCreateTaskDialog() { //MainActivity의 showCreateListDialog와 유사
        val taskEditText = EditText(this) //텍스트를 입력받을 taskEditText
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT

        AlertDialog.Builder(this)
            .setTitle(R.string.task_to_add)
            .setView(taskEditText)
            .setPositiveButton(R.string.add_task, { dialog, _ ->
                val task = taskEditText.text.toString() //입력된 텍스트를 가져온다.
                list.tasks.add(task)

                val recyclerAdapter = listItemsRecyclerView.adapter as ListItemsRecyclerViewAdapter
                recyclerAdapter.notifyItemInserted(list.tasks.size)
                //adaptor에 새 항목이 추가되었음을 알려준다.

                dialog.dismiss() //dialog 종료
            })
            .create()
            .show()
            //AlertDialog 호출
    }

    override fun onBackPressed() { //뒤로 버튼으로 이전 Activity(여기서는 MainActivity)로 돌아갈 때 코드를 실행한다.
        val bundle = Bundle()
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY, list) //패키징

        val intent = Intent()
        intent.putExtras(bundle) //MainActivity로 다시 전달될 intent에 해당 번들을 추가한다.
        setResult(Activity.RESULT_OK, intent)

        super.onBackPressed()
    }

}

//


