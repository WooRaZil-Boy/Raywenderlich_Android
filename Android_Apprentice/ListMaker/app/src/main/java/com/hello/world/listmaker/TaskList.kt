package com.hello.world.listmaker

import android.os.Parcel
import android.os.Parcelable

class TaskList constructor(val name: String, val tasks: ArrayList<String> = ArrayList<String>()) : Parcelable {
    constructor(source: Parcel) : this( //추가 생성자
        source.readString(),
        source.createStringArrayList()
        //전달된 source(Parcel)에서 해당 값을 가져와 기본 생성자로 TaskList 객체를 생성한다.
        //Parcel에서 readString를 호출해 기본 생성자 name의 인자로 사용하고
        //Parcel에서 createStringArrayList 호출해 기본 생성자 tasks 인자로 사용해 기본 생성자로 반환한다.
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        //TaskList 객체에서 Parcel을 생성할 때 호출된다.
        dest.writeString(name)
        dest.writeStringList(tasks)
    }

    companion object CREATOR: Parcelable.Creator<TaskList> { //static
        //Parcelable 프로토콜은  Parcelable.Creator<TaskList>을 구현해야 한다.
        //해당 메서드들은 Kotlin에 존재하지 않아, Java의 메서드를 재정의 해야 한다.
        override fun createFromParcel(source: Parcel): TaskList = TaskList(source) //생성자 호
        override fun newArray(size: Int): Array<TaskList?> = arrayOfNulls(size)

    }
}

//Creating a list
//앱 전체에 사용할 list모델을 생성한다.
//kt 파일이 있는 패키지에서 우클릭 ▸ New ▸ Kotlin File/Class 으로 해서, 이름을 정하고, Kind는 Class로 해주면 된다.

//Intents and Parcels
//Intent에 싣기 위해서는 Parcel 인터페이스를 구현해야 한다.
//https://developer.android.com/reference/android/os/Parcelable
//Kotlin 1.1.4에서는 @Parcelize를 주석으로 달아 일일이 Parcelable을 구현할 필요 없다(아직 베타 버전).