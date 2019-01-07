import kotlin.math.roundToInt
import kotlin.properties.Delegates

//Class와 Object에서 멤버로서 property를 가질 수 있다.
class Car(val make: String, val color: String)
//위의 Car 클래스에서는 String 타입의 make 와 color 라는 두 개의 property가 있다.




//Constructor properties
class Contact(var fullName: String, var emailAddress: String)
class Contact2(var fullName: String, val emailAddress: String)
//여기서 property는 fullName 과 emailAddress이다.

//Default values
class Contact3(
    var fullName: String,
    val emailAddress: String,
    var type: String = "Friend")
//default 값을 지정해 줄 수 있다. 생성시 직접 다른 값을 넣거나 이후에 변경하지 않으면 자동으로 default 값이 적용된다.




//Property initializers
//속성은 기본 생성자에서 literal과 value를 전달 받아, 기본 생성자를 벗어난 영역에서 초기화할 수도 있다.
class _Person(val firstName: String, val lastName: String) {
    val fullName = "$firstName $lastName"
    //여기서 fullName 속성은 기본 생성자에 전달된 값들을 사용해 초기화한다.
}

class Address {
    var address1: String //init 블록에서 초기화 된다.
    var address2: String? = null //속성을 선언할 때 초기화 된다.
    var city = "" //속성을 선언할 때 초기화 된다.
    var state: String //init 블록에서 초기화 된다.

    init { //생성자 없이 init 블록에서 속성의 값을 설정해 줄 수도 있다.
        address1 = ""
        state = ""
    }
}




//Custom accessors
//dot notation 외에도, getter, setter를 사용해 속성을 정의할 수도 있다. //Swfit의 Computed property와 비슷
//setter를 정의할 때, 해당 프로퍼티는 var로 선언되어야 한다.
class TV(var height: Double, var width: Double) {
    var diagonal: Int //height와 width는 Double이지만, Int로 반환한다.
        //Custom getter
        get() {
            val result = Math.sqrt(height * height + width * width)
            //height와 width가 있으면 대각선의 길이를 구할 수 있다.
            return result.roundToInt() //Int 반올림
        }
        //diagonal는 따로 값을 저장하지 않고 getter를 구현해 값을 반환하기만 한다.
        //getter 속성은 다른 속성과 마찬가지로 클래스 외부에서 액세스할 수 있다.

        //Custom setter
        //getter만 설정되어 있으면 읽기 전용 속성(read-only property)이 된다.
        //setter는 getter와 마찬가지로 값을 따로 저장하지 않기 때문에 다른 속성들을 이용해 간접적으로 설정한다.
        set(value) {
            //diagonal에 직접 값을 설정하면, height와 width를 재설정한다.
            val ratioWidth = 16.0
            val ratioHeight = 9.0
            val ratioDiagonal = Math.sqrt(ratioWidth * ratioWidth + ratioHeight * ratioHeight)

            height = value.toDouble() * ratioHeight / ratioDiagonal
            //Double로 변환해 계산한다.
            width = height * ratioWidth / ratioHeight

            //setter에는 return 문이 없으면 다른 stored property만 수정한다.
        }
}




//Companion object properties
//클래스 자체에 모든 인스턴스에서 공통적으로 사용하는 속성이 필요할 경우 companion object를 사용할 수 있다.
//이는 다른 언어의 static과 비슷하지만, 정확히 같지는 않다.
class Level(val id: Int, var boss: String, var unlocked: Boolean) {
    companion object {
        var highestLevel = 1
//        @JvmStatic var highestLevel = 1
        //JVM을 사용하는 Kotlin의 경우에는 @JvmStatic annotation을 사용하여 정적 getter, setter를 사용한 속성을
        //바이트 코드의 정적 필드로 만들 수 있다. 이렇게 하면 Java 코드에서 싱글톤을 사용하지 않아도 된다.
    }
}




//Delegated properties
//단순히 literal을 전달하거나 default 값으로 초기화하는 단순한 모델 외에도, 다른 객체에 전달하거나 지연된 초기화가 필요한 경우가 있다.
//또는 속성이 변경되는 경우 observer를 통해 이를 통지받도록 할 수도 있다. 이러한 경우에는 by 키워드를 사용해 delegated property를 사용하면 된다.

//Observable properties
//위에서 생성한 Level의 경우, 플레이어가 새로운 레벨을 해제할 때, highestLevel이 자동으로 설정되도록 하는 것이 유용하다.
//이 경우, delegated property를 사용해 속성이 변경될 때 콜백을 받을 수 있다.
class DelegatedLevel(val id: Int, var boss: String) {
    companion object {
        var highestLevel = 1
    }

    var unlocked: Boolean by Delegates.observable(false) { //by 키워드로 옵저버를 추가해 줄 수 있다.
        //Delegates.observable() 를 사용해 해당 속성에 observer를 지정해 준다.
        _, old, new -> //첫 인수는 property object 자체이다(여기서는 undersocre로 생략). 값이 변경되면 람다가 콜백되며 새 값을 갖는다.
        if (new && id > highestLevel) {
            highestLevel = id //레벨이 최고 레벨인 경우 업데이트 한다.
        }
        println("$old -> $new")
    }
}

//Limiting a variable
//delegated property를 사용해 변수의 값을 제한해 줄 수도 있다.
class LightBulb {
    companion object {
        const val maxCurrent = 40
    }

    var current by Delegates.vetoable(0) { //by 키워드로 변수의 값을 제한해 줄 수 있다.
        //Delegates.vetoable() 를 사용해 해당 속성을 제한한다. 람다는 boolean을 반환한다. //Swift guard와 비슷
        _, _, new ->
        if (new > maxCurrent) {
            println("Current too high, falling back to previous setting.")
            false
        } else {
            true
        }
        //true가 될 때에만 값을 할당한다.
    }
    //delegated property observer와 getter, setter는 다르다.
    //delegated property에는 커스텀하게 액세스할 수 있는 방법이 없다.
}

//Lazy properties
//lazy는 프로필 사진을 다운로드 하거나 복잡한 계산을 하는데 유용할 수 있다.
class Circle(var radius: Double = 0.0) { //lazy도 by 키워드로 선언해 준다.
    val pi: Double by lazy { //Lazy property는 클래스 인스턴스가 생성될 때 해당 계산이 실행되지 않는다.
        //해당 속성이 처음 사용될 때 값을 할당한다.
        ((4.0 * Math.atan(1.0 / 5.0)) - Math.atan(1.0 / 239.0)) * 4.0
    }

    val circumference: Double
        get() = pi * radius * 2
}




//lateinit
//클래스 인스턴스가 생성될 때 속성에 값이 없다는 것을 나타내려면 lateinit 키워드를 사용하면 된다.
class Lamp {
    lateinit var bulb: LightBulb
    //Lamp 클래스의 인스턴스를 생성할 때, bulb에는 값이 없다.
    //이렇게 나중에 속성을 변경해야 하는 경우, var이 아닌 lateinit var를 사용한다.
    //lateinit로 선언된 속성은 클래스 인스턴스가 초기화 될때 해당 속성에 값이 없다.
}




//Extension properties
//Kotlin은 extension property를 사용해, 클래스 정의를 변경하지 않고 새로운 속성을 추가해 줄 수 있다.
//extension property는 custom accessor만 정의할 수 있다(Swift의 extension이 computed property만 생성 가능한 것과 동일).
//Swift의 extension과 비슷하지만, 따로 코드 영역이 있지는 않다.
val Circle.diameter: Double //클래스 이름에 확장할 property를 추가한다.
    get() = 2.0 * radius




fun main(args: Array<String>) {
    //Constructor properties
    val contact = Contact(fullName = "Grace Murray", emailAddress = "grace@navy.mil")
    //클래스 기본 생성자에 인수로 값을 전달하여 인스턴스를 생성한다.

    val name = contact.fullName // Grace Murray
    val email = contact.emailAddress // grace@navy.mil
    //dot notation으로 개별 속성에 액세스할 수 있다.

    contact.fullName = "Grace Hopper" //변수로 된 프로퍼티는 값을 바꿀 수 있다.
    val grace = contact.fullName // Grace Hopper

    var contact2 = Contact2(fullName = "Grace Murray", emailAddress = "grace@navy.mil")
    //프로퍼티의 값을 변경하지 않으려면, var 대신 val을 사용해 상수로 속성을 정의할 수 있다.
    //여기선 emailAddress가 상수로 되어 있기 때문에 초기화 이후 emailAddress의 값을 변경할 수 없다.
//    contact2.emailAddress = "grace@gmail.com" // Error: Val cannot be reassigned




    //Property initializers
    val person = Person("Grace", "Hopper")
    person.fullName // Grace Hopper //fullName 속성은 기본 생성자에 전달된 값들을 사용해 초기화한다.

    val address = Address() //빈 생성자를 호출 해, 해당 속성을 모두 초기화 할 수 있다.




    //Custom accessors

    //Custom getter
    val tv = TV(height = 53.93, width = 95.87)
    val size = tv.diagonal // 110
    //diagonal는 따로 값을 저장하지 않고 getter를 구현해 값을 반환하기만 한다.
    //getter 속성은 다른 속성과 마찬가지로 클래스 외부에서 액세스할 수 있다.

    tv.width = tv.height
    val diagonal = tv.diagonal // 76

    //Custom setter
    tv.diagonal = 70

    println(tv.height) // 34.32...
    println(tv.width)  // 61.01...




    //Companion object properties
    val level1 = Level(id = 1, boss = "Chameleon", unlocked = true)
    val level2 = Level(id = 2, boss = "Squid", unlocked = false)
    val level3 = Level(id = 3, boss = "Chupacabra", unlocked = false)
    val level4 = Level(id = 4, boss = "Yeti", unlocked = false)

//    val highestLevel = level3.highestLevel // Error: Unresolved reference
    //highestLevel은 Companion object property 이므로 인스턴스에서 액세스할 수 없다.
    val highestLevel = Level.highestLevel // 1
    //대신 Class 자체로 액세스해야 한다.

    //Companion object property를 사용하면, 앱 또는 알고리즘 코드에서 동일한 속성 값을 검색할 수 있다.
    //다른 위치에서 액세스할 수 있다.




    //Delegated properties

    //Observable properties
    val delegatedlevel1 = DelegatedLevel(id = 1, boss = "Chameleon")
    val delegatedlevel2 = DelegatedLevel(id = 2, boss = "Squid")
    println(DelegatedLevel.highestLevel) // 1

    delegatedlevel2.unlocked = true //observer로 자동으로 업데이트 된다.
    println(DelegatedLevel.highestLevel) // 2

    //Limiting a variable
    val light = LightBulb()
    light.current = 50 //maxCurrent를 벗어났으므로, initialValue인 0이 된다.
    var current = light.current // 0

    light.current = 40
    current = light.current // 40

    //Lazy properties
    val circle = Circle(5.0) //circle 인스턴스를 만들었지만, lazy property인 pi는 아직 계산되지 않은 상태이다.
    val circumference = circle.circumference // 31.42
    //해당 pi의 값이 필요해 질 때 계산을 하고 값을 할당한다.




    //lateinit
    val lamp = Lamp()
//    println(lamp.bulb) // Whoops! kotlin.UninitializedPropertyAccessException
    //lateinit로 선언했기 때문에 인스턴스를 초기화했을 때 아직 값이 없다.

    lamp.bulb = LightBulb() //값을 할당해 준다.




    //Extension properties
    val unitCircle = Circle(1.0)
    println(unitCircle.diameter) // > 2.0
    //클래스 내에 정의된 다른 속성과 마찬가지로 extension property에 접근할 수 있다.
}