package com.hello.world.listmaker

import android.content.Context
import android.preference.PreferenceManager

class ListDataManager(val context: Context) {
    fun saveList(list: TaskList) {
        //디바이스에 list를 저장해야 할때, SharedPreferences를 사용한다.
        //SharedPreferences를 사용하면, key-value collection으로 데이터를 저장할 수 있다. //UserDefault 와 비슷
        //다른 앱에서 SharedPreferences에 접근할 수도 있다. 복잡한 데이터를 저장할 때는 다른 방법을 사용해야 한다.
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()
        //default SharedPreferences를 context를 사용해 생성한다.
        //edit()를 호출하여, sharedPreferences를 가져올 수 있다.
        sharedPreferences.putStringSet(list.name, list.tasks.toHashSet())
        //list.name을 key로, list.tasks.toHashSet()를 value로 전달한다.
        //list는 배열이므로 HashSet으로 변환한 다음 전달한다.
        sharedPreferences.apply() //변경사항 적용한다.
    }

    fun readLists() : ArrayList<TaskList> {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        //default SharedPreferences를 가져온다. //읽기만 하므로 edit()는 사용하지 않는다.
        val sharedPreferenceContents = sharedPreferences.all
        //SharedPreferences의 모든 내용을 Map으로 가져온다.
        //Map은 key-value 쌍으로 되어 있기 때문에 해당 값을 효율적으로 검색할 수 있다(고유한 키를 가지고 있다).
        val taskLists = ArrayList<TaskList>() //빈 ArrayList

        for (taskList in sharedPreferenceContents) { //SharedPreferences의 모든 list를 loop
            val itemsHashSet = taskList.value as HashSet<String> //value를 가져온다.
            //saveList에서 value를 직접 String으로 저장할 수 없기에, HashSet으로 변환해서 저장했다.
            //따라서 HashSet으로 가져와 ArrayList<String>로 변환해야 한다.
            val list = TaskList(taskList.key, ArrayList(itemsHashSet)) //key, value로 TaskList 객체를 만든다.

            taskLists.add(list) //추가
        }

        return taskLists
    }
}

