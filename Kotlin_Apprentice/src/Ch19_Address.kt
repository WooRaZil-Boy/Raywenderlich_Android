//Adding a Kotlin class as a Java property
//Kotlin에서 새 클래스를 만든 다음, Java의 클래스에서 직접 사용할 수도 있다.

enum class AddressType {
    Billing,
    Shipping,
    Gift
}

data class Ch19_Address @JvmOverloads constructor (
        @JvmField val streetLine1: String,
        @JvmField val streetLine2: String?,
        @JvmField val city: String,
        @JvmField val stateOrProvince: String,
        @JvmField val postalCode: String,
        @JvmField var addressType: AddressType,
        @JvmField val country: String = "United States"
        //기본 값이 설정되어 있지만, Java에서는 나타나지 않는다.
        //Kotlin 컴파일러는 기본적으로 사용 가능한 모든 매개 변수가 있는 기본 생성자만 사용하기 때문이다.
        //따라서 기본값을 허용하려면 default 값이 없는 생성자를 따로 만들어야 한다.
        //@JvmOverloads 주석을 생성자에 사용하면, 컴파일러에게 이를 생성해야 한다고 알려 줄 수 있다.

        //@JvmField를 사용하면, 해당 변수의 getter, setter 메서드에서 뜨는 warning을 무시할 수 있다.
        //JVM 언어의 경우 getter, setter 를 생성 할 필요 없다는 것을 알려주며 필드 또는 속성 변수를 생성해 직접 사용합니다.
        //@JvmField는 모든 종류의 유형에 사용할 수 있으며 유형에 관계없이 getter, setter를 생성하지 않는 Kotlin 객체 속성에서 주로 사용한다.
) {

    fun forPostalLabel(): String {
        var printedAddress = streetLine1
        streetLine2?.let { printedAddress += "\n$streetLine2" }
        printedAddress += "\n$city, $stateOrProvince $postalCode"
        printedAddress += "\n${country.toUpperCase()}"
        return printedAddress
    }

    override fun toString(): String {
        return forPostalLabel()
    }




    //Accessing nested Kotlin objects
    //Kotlin에서 클래스 내의 클래스를 생성할 수 있다(ex. companion object). 이럴 경우 Java에서 접근하는 방법 HashMap으로 변환하는 것이다.
    object JSONKeys {
        const val streetLine1 = "street_1"
        const val streetLine2 = "street_2"
        const val city = "city"
        const val stateOrProvince = "state"
        const val postalCode = "zip"
        const val addressType = "type"
        const val country = "country"
        //독립적인 Kotlin 객체에서는 const 키워드를 사용해 @JvmField와 동일한 효과를 얻는다.
    }





    //"Static" values and functions from Kotlin
    //Java에서 클래스의 정적 멤버는 인스턴스없이 액세스할 수 있다. 따라서 팩토리 메서드나 클래스 상수에 유용하다.
    //Java에서 Kotlin의 companion object를 사용하려면, 컴파일러가 추가적인 작업을 해야 한다.
    companion object {
        const val sampleFirstLine = "123 Fake Street"
        //sampleFirstLine은 단순한 String이기 때문에 JSONKeys 객체에서처럼 const 키워드를 사용할 수 있다.

        @JvmStatic //Java 용 클래스에서 정적 메서드를 사용할 때 Companion 이름을 사용할 필요 없다는 것을 알려준다.
        fun canadianSample(type: AddressType): Ch19_Address {
            return Ch19_Address(sampleFirstLine,
                    "4th floor",
                    "Vancouver",
                    "BC",
                    "A3G4B2",
                    type,
                    "Canada")
        }
    }
}
