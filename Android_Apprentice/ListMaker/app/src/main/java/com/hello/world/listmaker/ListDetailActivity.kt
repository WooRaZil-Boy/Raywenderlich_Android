package com.hello.world.listmaker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class ListDetailActivity : AppCompatActivity() {
    lateinit var list: TaskList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list_detail)

        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY) //key를 사용해 intent에서 해당 value를 사져온다.
        title = list.name
    }
}


