//다른 사용자 정의 유형과 달리 인터페이스는 직접 인스턴스화 되지 않는다.
//대신 구체적인 유형이 따르는 청사진을 정의한다. 인터페이스를 사용하면 구체적인 유형이 구현하는 공통 속성 및 메서드를 정의할 수 있다.
//인터페이스와 다른 사용자 정의 유형간의 주요 차이점은 인터페이스 자체가 상태를 포함할 수 없다는 것이다.




//Introducing interfaces
interface Vehicle { //인터페이스는 구현을 포함할 필요 없다. 즉, 인스턴스화 할 수 없다. 대신 다른 유형의 속성과 메서드를 정의할 수 있다.
    fun accelerate()
    fun stop()
}




//Methods in interfaces
enum class Direction {
    LEFT, RIGHT
}

interface DirectionalVehicle {
    fun accelerate()
    fun stop()
    fun turn(direction: Direction)
    fun description(): String
}

interface OptionalDirectionalVehicle { //인터페이스에서 선언된 메서드는 클래스 메서드와 마찬가지로 default 매개변수를 포함할 수 있다.
    fun turn(direction: Direction = Direction.LEFT)
}

//Default method implementations
interface SpaceVehicle { //Java8 에서와 마찬가지로 인터페이스 메서드에 대한 기본 구현을 정의할 수 있다.
    //Swift의 Protocol을 extension으로 기본 구현하는 것과 비슷하다.
    fun accelerate()
    fun stop() {
        println("Whoa, slow down!")
    }
}

//Properties in interfaces
interface VehicleProperties { //인터페이스에는 속성도 정의해 줄 수 있다.
    val weight: Int // abstract
    val name: String
        get() = "Vehicle"
}
//하지만 인터페이스는 자체적으로 상태를 유지할 수 없으므로 stored property를 정의할 수 없으며, 추상화 하거나 computed property로 구현해야 한다.




//Interface inheritance
//다른 인터페이스를 상속하는 인터페이스를 정의할 수 있다. 클래스에서의 상속과 유사하다.
interface WheeledVehicle: Vehicle {
    val numberOfWheels: Int
    var wheelSize: Double
}
//WheeledVehicle 인터페이스를 준수하려면, WheeledVehicle의 멤버와 Vehicle의 멤버를 모두 정의해야 한다.




//Implementing multiple interfaces
//클래스는 하나의 상위 클래스만 상속받을 수 있다(단일 상속). 하지만 인터페이스는 원하는 만큼 구현해 줄 수 있다.
interface Wheeled {
    val numberOfWheels: Int
    val wheelSize: Double
}




//Interfaces in the standard library
//Comparable
//comparable은 인스턴스를 다른 인스턴스와 비교하는 게 사용되는 연산자 함수를 선언한다.
interface SizedVehicle {
    var length: Int
}






fun main(args: Array<String>) {
    //Introducing interfaces
//    val vehicle = Vehicle() // Error: Interface Vehicle does not have constructors
    //인터페이스는 인스턴스화 할 수 없다. 대신 인터페이스를 사용하여 다른 유형의 속성과 메서드를 정의할 수 있다.

    //Interface syntax
    //인터페이스는 class 또는 object에서 구현될 수 있으며, 다른 유형이 인터페이스를 구현할 때 인터페이스에 정의된 속성과 매서드를 정의해야 한다.
    //한 유형이 인터페이스의 모든 멤버를 구현하면, 인터페이스를 준수한다(구현했다)라고 한다.
    class Unicycle: Vehicle { //Vehicle을 구현하는 클래스
        //상속의 구문과 동일하다.
        var peddling = false

        override fun accelerate() {
            peddling = true
        }

        override fun stop() {
            peddling = false
        }
        //메서드 구현시 override 키워드를 써줘야 한다.
    }

    //Methods in interfaces
    class OptionalDirection : OptionalDirectionalVehicle {
        override fun turn(direction: Direction) { //인터페이스에 default 값이 지정되어 있다.
            println(direction)
        }
    }

    val car = OptionalDirection()
    car.turn() // > LEFT //인터페이스의 default 값이 호출된다.
    car.turn(Direction.RIGHT) // > RIGHT

    //Default method implementations
    class LightFreighter: SpaceVehicle {
        override fun accelerate() {
            println("Proceed to hyperspace!")
        }
    }

    val falcon = LightFreighter()
    falcon.accelerate() // > Proceed to hyperspace!
    falcon.stop() // > "Whoa, slow down!
    //stop은 기본으로 구현이 되어 있기 때문에 따로 구현을 하지 않아도 된다.

    //하지만 필요하다면 재정의할 수도 있다.
    class Starship: SpaceVehicle {
        override fun accelerate() {
            println("Warp factor 9 please!")
        }

        override fun stop() { //기본 구현된 stop()을 재정의 해준다.
            super.stop() //반드시 super를 호출해야 하는 것은 아니다.
            println("That kind of hurt!")
        }
    }

    val enterprise = Starship()
    enterprise.accelerate() // > Warp factor 9 please!
    enterprise.stop()
    // > Whoa, slow down!
    // > That kind of hurt!"

    //Properties in interfaces
    class Car: VehicleProperties {
        override val weight: Int = 1000 //추상 속성에 값을 대입하거나, 구현을 할 수 있다.
    }

    class Tank: VehicleProperties {
        override val weight: Int //추상 속성에 값을 대입하거나, 구현을 할 수 있다.
            get() = 10000

        override val name: String
            get() = "Tank"
    }
    //인터페이스 속성 구현 시에는 override 키워드를 써줘야 한다.




    //Interface inheritance
    //다른 인터페이스를 상속하는 인터페이스를 정의할 수 있다. 클래스에서의 상속과 유사하다.
    class Bike: WheeledVehicle { //WheeledVehicle 인터페이스를 준수하려면, WheeledVehicle의 멤버와 Vehicle의 멤버를 모두 정의해야 한다.
        var peddling = false
        var brakesApplied = false
        override val numberOfWheels = 2 //WheeledVehicle
        override var wheelSize = 622.0 //WheeledVehicle

        override fun accelerate() { //Vehicle
            peddling = true
            brakesApplied = false
        }

        override fun stop() { //Vehicle
            peddling = false
            brakesApplied = true
        }
    }
    //클래스와 마찬가지로, 상위 인터페이스와 하위 인터페이스는 is-a 관계가 된다.




    //Implementing multiple interfaces
    class Tricycle: Wheeled, Vehicle { //인터페이스는 다중 구현이 가능하다.
        //Wheeled과 Vehicle을 모두 구현해 주면 된다.
        var peddling = false
        var brakesApplied = false

        override val numberOfWheels: Int
            get() = 3

        override val wheelSize: Double
            get() = 100.0

        override fun accelerate() {
            peddling = true
            brakesApplied = false
        }

        override fun stop() {
            peddling = false
            brakesApplied = true
        }
    }




    //Interfaces in the standard library
    //Kotlin 표준 라이브러리는 인터페이스를 광범위하게 사용한다. Kotlin에서 인터페이스를 잘 사용하면, 깨긋하게 분리된 "Kotliny" 코드를 작성할 수 있다.

    //Iterator
    //Kotlin의 리스트, 맵 및 다른 collection type은 모두 Iterator 인스턴스에 대한 액세스를 제공한다.
    //Iterator는 Kotlin 표준 라이브러리에 정의된 인터페이스이며, collection의 다음 요소를 제공하는 next() 메서드와
    //collection 요소가 더 있는 지 여부를 boolean으로 반환하는 hasNext()가 있다.
    //Iterator 인터페이스를 준수하 in 을 사용하여 컬렉션을 반복할 수 있다.
    val cars = listOf("Lamborghini", "Ferrari", "Rolls-Royce")
    val numbers = mapOf("Brady" to 12, "Manning" to 18, "Brees" to 9)

    for (car in cars) {
        println(car)
    }

    for (qb in numbers) {
        println("${qb.key} wears ${qb.value}")
    }
    //여기서 List와 Map의 반복자는 약간의 차이가 있다. List는 Collection을 구현하고, Collection이 Iterable 인터페이스를 준수한다.
    //반면, Map에는 extension으로 iterator 함수가 있다.

    //Comparable
    //comparable은 인스턴스를 다른 인스턴스와 비교하는 게 사용되는 연산자 함수를 선언한다.
    class Boat : SizedVehicle, Comparable<Boat> { //Comparable을 구현한다.
        override var length: Int = 0
        override fun compareTo(other: Boat): Int { //대소 비교를 정의해 주면 된다.
            return when {
                length > other.length -> 1
                length == other.length -> 0
                else -> -1
            }
            //length에 따라 상대 크기를 나타내는 Int를 반환한다.
        }
    }

    val titanic = Boat()
    titanic.length = 883

    val qe2 = Boat()
    qe2.length = 963

    println(titanic > qe2) // > false
    //Comparable을 구현하면, 부등호로 크기를 비교할 수 있다.
}