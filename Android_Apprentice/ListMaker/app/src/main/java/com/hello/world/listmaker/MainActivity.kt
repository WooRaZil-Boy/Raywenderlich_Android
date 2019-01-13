package com.hello.world.listmaker

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_list.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
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

