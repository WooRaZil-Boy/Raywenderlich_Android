//Adding extension functions to a Java class
//Java의 User 클래스에서 firstName 속성과 lastName 속성을 결합한 fullName 속성을 만들려고 할때 Java에서 작성할 수도 있지만,
//Kotlin의 extension을 사용할 수도 있다.
@file:JvmName("UserExtensions")
//Kotlin 코드가 Java에 포함될 때 해당파일의 이름을 설정해 준다.
//래퍼 이름이 Uh19_UserExtensionsKt(default 값)이 아닌 UserExtensions이 된다.

val Ch19_User.fullName: String
    get() = firstName + " " + lastName //read only
    //Kotlin extension에는 제한이 있다. custom accessor가 있는 속성만 추가할 수 있다.

fun Ch19_User.addressOfType(type: AddressType): Ch19_Address? { //extension으로 메서드를 추가해 준다.
    return addresses.firstOrNull { it.addressType == type }
    //addresses의 첫 요소를 가져온다. 없을 경우에는 null
    //가져온 첫 요소의 addressType을 비교한다.
}
//이 extension 메서드는 Java, Kotlin에서 모두 호출할 수 있지만, 구현 내부에서 Kotlin의 함수형 프로그래밍과 nullable을 사용한다.

fun Ch19_User.addOrUpdateAddress(address: Ch19_Address) {
    val existingOfType = addressOfType(address.addressType) //유효성 검사

    if (existingOfType != null) {
        addresses.remove(existingOfType)
    }

    addresses.add(address)
}