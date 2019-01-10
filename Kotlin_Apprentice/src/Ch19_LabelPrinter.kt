//Free functions
//Kotlin의 extension을 포함하여 클래스 내의 메서드가 아닌 것을 의미한다. 전역함수와 비슷하지만, interop 코드로 전달된다.

@file:JvmName("LabelPrinter")
//Kotlin 코드가 Java에 포함될 때 해당파일의 이름을 설정해 준다.
//래퍼 이름이 Uh19_LabelPrinterKt(default 값)이 아닌 LabelPrinter이 된다.

fun labelFor(user: Ch19_User, type: AddressType): String { //Free function
    val address = user.addressOfType(type)
    //Kotlin 코드 이므로, User 객체에서 extension 메서드를 사용할 수 있다.
    if (address != null) {
        var label = "-----\n"
        label += "${user.fullName}\n${address.forPostalLabel()}\n"
        label += "-----\n"
        return label
        //Kotlin에서 여러 줄의 문자열은 += 로 문자열을 추가해 만들 수 있다.
    } else {
        return "\n!! ${user.fullName} does not have a $type address set up !!\n"
    }
}

@JvmOverloads //생성자와 마찬가지로, 주어진 매개 변수에 대한 기본값을 가진 것으로 처리하기 때문에 추석을 추가해 알려줘야 한다.
fun printLabelFor(user: Ch19_User, type: AddressType = AddressType.Shipping) { //Free function
    //기본값을 추가해 준다.
    println(labelFor(user, type))
}
