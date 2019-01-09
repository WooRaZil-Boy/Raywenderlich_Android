//코드를 중앙 집중화하면, 버그를 예방할 수 있다. 여러 장소에서 같은 일을 수행하더라도 실제 시행이 일어나는 곳은 한 곳이 되기 때문이다
//Kotlin에서는 generic으로 이를 구현할 수 있다. 제네릭 프로그래밍의 일반적인 개념은 객체에 작업을 할 때, 어떤 유형인지 정확히 알 필요 없다는 것이다.
//제네릭으로 결합하고 단순화시킬 수 있다.




//Extension functions on types with generic constraints
//일반적으로 그냥 리스트를 출력하면, 모든 요소를 한 줄에 출력한다. 이를 수정해, 한 행에 한 요소씩 출력하는 함수를 만든다.
//fun List<String>.toBulletedList(): String { //main 함수 밖에서 선언하면 다른 곳에서도 사용할 수 있다.
//    //extension으로 구현
//    val separator = "\n - "
//    return this.map { "$it" }.joinToString(separator, prefix = separator, postfix = "\n")
//} //이는 String에서는 잘 동작하지만, Any 타입의 객체가 있는 List에서는 제대로 작동하지 않는다.

//    fun List<Any>.toBulletedList(): String {}
//여기에서 부터 차근차근 생각해서 구현해 보면 된다. 모든 타입에 대해 구현되므로 제네릭을 사용해 주면 된다.
//    fun List<T>.toBulletedList(): String {}
//여기에서 컴파일러가 T에 대해 알수 없기 때문에 제네릭 매개변수임을 알려주면 된다.
//    fun <T> List<T>.toBulletedList(): String {}
fun <T> List<T>.toBulletedList(): String {
    val separator = "\n - "
    return this.map { "$it" }.joinToString(separator, prefix = separator, postfix = "\n")
}
//결론적으로 이와 같은 제네릭 메서드를 구현해 줄 수 있다.




//Creating your own generic constraints
//사용자 정의 객체에 제네릭 제약 조건을 추가해 줄 수 있다. 이렇게 구현하면 중앙 집중식으로 코드를 관리할 수 있다.

//Interfaces
//인터페이스를 사용하면, 클래스 계층 구조와 같은 정보를 선언해 줄 수 있다.
interface Checkable {
    fun checkIsOK(): Boolean
}

class Mover<T: Checkable>( //Mover 클래스에 제네릭 제약 조건을 지정해 준다. //Checkable을 구현한 제네릭이어야 한다.
        //이 클래스 인스턴스를 생성할 때 T 유형에 실제 타입을 채워 넣는다.
        thingsToMove: List<T>, //생성자에서 제네릭 제약 조건과 동일한 T 유형의 List를 받는다.
        val truckHeightInInches: Int = (12 * 12)
) {

    private var thingsLeftInOldPlace = mutableListOf<T>()
    private var thingsInTruck = mutableListOf<Any>() //제네릭 container가 해당 유형이 아니므로 Any로 해줘야 한다.
    private var thingsInNewPlace = mutableListOf<T>()
    //데이터 처리를 위한 mutableList

    private var thingsWhichFailedCheck = mutableListOf<T>() //checkIsOK()에서 실패한 객체들을 담는 list

    init { //초기화 블록. 인스턴스가 생생될 때마다 호출된다.
        thingsLeftInOldPlace.addAll(thingsToMove) //생성자로 받아온 List를 thingsLeftInOldPlace에 추가해 준다.
    }

    fun moveEverythingToTruck(startingContainer: Container<T>?) { //Nullalbe 컨테이너를 인수로 받는다.
        var currentContainer = startingContainer //인수는 상수로 변경할 수 없으므로 변수에 복사해 둔다.

        while (thingsLeftInOldPlace.count() > 0) {
            //while로 thingsLeftInOldPlace의 요소를 thingsInTruck으로 이동 시킨다.
            val item = thingsLeftInOldPlace.removeAt(0)

//            if (item is BreakableThing) {
//                if (!item.isBroken) {
//                    thingsInTruck.add(item)
//                    println("Moved your $item to the truck!")
//                } else {
//                    println("Could not move your $item to the truck")
//                }
//            } else {
//                thingsInTruck.add(item)
//                println("Moved your $item to the truck!")
//            }
            //아이템이 어떤 타입인지 확인해 각 타입에 맞춘 코드를 적용해 줄 수 있다.
            //하지만 이 방법은 문제가 되는 몇 가지 사항이 있다.
            //• 제네릭 제약 조건을 가진 클래스는 T가 어떤 유형이고, 이떤 작업을 하는지 알 필요 없다.
            //  따라서 내부에서 상세한 타입을 나눠 주면, 제네릭을 사용하는 의미가 퇴색된다.
            //• 많은 로직이 반복된다. 코드를 복사 - 붙여넣기 하면, 어떤 버그가 있을 시 같이 적용되므로 문제가 될 수 있다.
            //이에 대한 해결책은 제네릭 형식을 더 제한하여, 일반적인 슈퍼 클래스가 아니 타입으로 범위를 좁혀주는 것이다.
            //인터페이스로 이를 구현해 줄 수 있다.

            if (item.checkIsOK()) { //인터페이스로 구분해 준다. //제네릭 제약조건으로 Checkable을 구현해야 하므로 오류가 나지 않는다.
                if (currentContainer != null) { //컨테이너가 null인지 확인한다.
                    if (!currentContainer.canAddAnotherItem()) { //현재 컨테이너가 가득 찼는지 확인한다.
                        moveContainerToTruck(currentContainer)
                        currentContainer = currentContainer.getAnother()
                    }

                    currentContainer.addItem(item) //아이템 추가
                    println("Packed your $item!")
                } else {
                    thingsInTruck.add(item)
                    println("Moved your $item to the truck!")
                }
            } else {
                thingsWhichFailedCheck.add(item)
                println("Could not move your $item to the truck :[")
            }
        }

        currentContainer?.let { moveContainerToTruck(it)} //nullable을 해제해 준다.
    }

    private fun moveContainerToTruck(container: Container<T>) {
        thingsInTruck.add(container)
        println("Moved a container with your ${container.contents().toBulletedList()} to the truck!")
    }

    fun moveEverythingIntoNewPlace() {
        //Reified type parameters
        //Reified generic type를 사용하면 제네릭을 사용하면서, 해당 유형에 대한 정보를 가지고 있는다.

        //표준 라이브러리의 함수를 살펴 보면 // inline fun <reified R> Iterable<*>.filterIsInstance(): List<R>
        //• inline 선언은 컴파일러에게 이 메서드에 대한 모든 호출이 inline으로 컴파일되어야 함을 알려준다.
        //  따라서 제네릭 타입의 정보에 접근할 수 있다.
        //• reified 선언은 해당 유형의 정보를 가지고 있는 제네릭이다. 성능이 저하될 수 있으므로 명시적으로 사용해야 한다.
        //• Iterable<*>은 Star projection의 반복이다. 제네릭 형식을 사용하여 반복할 수 있는 모든 요소는 이 함수를 사용할 수 있다.
        //• 반환 유형은 Reified generic type을 리스트 요소로 가지고 있는 List<R>이다.

//    val breakableThings = thingsInTruck.filterIsInstance<BreakableThing>()
//    break //debug 용도
//    val items = thingsInTruck.filterIsInstance<T>() //반환 유형이 List<BreakableThings>로 제대로 표현된다.

//        val items = thingsInTruck.filterIsInstance<T>()
        //하지만, 여기서 T는 컴파일러가 이 시점에 도달할 때 Type erasure로 삭제되기 때문에 정보를 얻을 수 없다.
//        val containers = thingsInTruck.filterIsInstance<Container<*>>()
        //유형은 List<Any?> 이며, 해당 유형을 사용하려면 타입 변환을 해야 한다.

        val containers = thingsInTruck.filterIsInstance<Container<T>>()
        //여기에서는 특정 유형의 Container를 가져와야 한다는 충분한 정보가 있으므로 작동한다.
        for (container in containers) {
            thingsInTruck.remove(container)
            while (container.canRemoveAnotherItem()) {
                val itemInContainer = container.removeItem()
                //Container<T> 이므로, itemInContainer의 유형이 T가 되어 문제가 없다.
                println("Unpacked your $itemInContainer!")
                tryToMoveItemIntoNewPlace(itemInContainer)
            }
        }

        while (thingsInTruck.count() > 0) {
            @Suppress("UNCHECKED_CAST") //적절한 유형이 아니라 null이 반환되는 경우 경고가 발생한다.
            //이 주석을 추가해 주면 IDE의 경고를 무시할 수 있다.
            val item = thingsInTruck.removeAt(0) as? T //타입 보장을 위해 캐스팅을 해야 한다.
//            if (item is T) {} //아이템이 제네릭 T인지 검사한다. 하지만 컴파일러는 Type erasure로 실제 T타입을 알수 없기 때문에 오류가 난다.
//            if (item is Container<T>) {} //Container로 지정을 해 줘도 T 타입을 알 수 없기 때문에 오류가 난다.
            //star projection으로 이를 해결해 줄 수 있다.

            //Star projection
//            if (item is Container<*>) { //Container<*>의 의미는 어떤 유형이든 Container임을 컴파일러에게 알려주는 것이다.
//                //Container에 정의된 메서드에 대해 액세스할 수 있다.
//                //하지만, 해당 메서드를 사용할 때, 유형이 T가 아닌 Any?가 된다.
//                //Star projection은 제네릭에 대한 정보를 얻는 게 아니라 Nullable 로 가정해서 가져온다.
//                //따라서 Container에 어떤 유형이 있는지 알 수 없다.
//                val itemInContainer = item.removeItem()
//            }
            //위 코드는 제대로 작동하지만 Any? 로 타입을 제대로 가져올 수 없다.

            //Reified type parameters
            //Reified generic type를 사용하면 제네릭을 사용하면서, 해당 유형에 대한 정보를 가지고 있는다.

            //표준 라이브러리의 함수를 살펴 보면 // inline fun <reified R> Iterable<*>.filterIsInstance(): List<R>
            //• inline 선언은 컴파일러에게 이 메서드에 대한 모든 호출이 inline으로 컴파일되어야 함을 알려준다.
            //  따라서 제네릭 타입의 정보에 접근할 수 있다.
            //• reified 선언은 해당 유형의 정보를 가지고 있는 제네릭이다. 성능이 저하될 수 있으므로 명시적으로 사용해야 한다.
            //• Iterable<*>은 Star projection의 반복이다. 제네릭 형식을 사용하여 반복할 수 있는 모든 요소는 이 함수를 사용할 수 있다.
            //• 반환 유형은 Reified generic type을 리스트 요소로 가지고 있는 List<R>이다.

            if (item != null) {
                tryToMoveItemIntoNewPlace(item)
            } else {
                println("Something in the truck was not of the expected generic type: $item")
            }
        }
    }

    private fun tryToMoveItemIntoNewPlace(item: T) { //item을 검
        if (item.checkIsOK()) {
            thingsInNewPlace.add(item)
            println("Moved your $item into your new place!")
        } else {
            thingsWhichFailedCheck.add(item)
            println("Could not move your $item into your new place :[")
        }
    }

    fun finishMove() { //인쇄
        println("OK, we're done! We were able to move your:${thingsInNewPlace.toBulletedList()}")
        if (!thingsWhichFailedCheck.isEmpty()) {
            println("But we need to talk about your:${thingsWhichFailedCheck.toBulletedList()}")
        }
    }
}

class CheapThing ( //기본 유형 T가 될 수 있는 class
        val name: String
): Checkable {

    override fun toString(): String {
        return name
    }

    override fun checkIsOK(): Boolean = true //checkIsOK 재정
}

class BreakableThing(
        val name: String,
        var isBroken: Boolean = false
): Checkable {

    fun smash() {
        isBroken = true
    }

    override fun toString(): String {
        return name
    }

    override fun checkIsOK(): Boolean {
        return !isBroken
    }
}




//Generic interfaces
//제네릭 인터페이스는 제네릭 형식으로 제한되는 인터페이스이다.
interface Container<T> { //인터페이스를 구현하는 클래스가 생성될 때마다 인터페이스에 전달되는 제네릭 형식이 필요하다.
    fun canAddAnotherItem(): Boolean
    fun addItem(item: T)
    //컨테이너에 다른 항목을 추가할 수 있는 지 확인한 후, 추가할 컨테이너의 제네릭 유형 항목을 전달한다.

    fun canRemoveAnotherItem(): Boolean
    fun removeItem(): T
    //컨테이너에서 제거할 항목이 있는지 확인 한 다음 컨테이너에서 제거된 일반 유형의 항목을 반환한다.

    fun getAnother(): Container<T>
    //비어 있는 새 컨테이너를 생성하기 위한 Factory 메서드. canAddAnotherItem()로 확인한다.

    fun contents(): List<T>
    //컨테이너에 있는 항목의 유형별 목록에 액세스하기 위한 list
}




//Type erasure

//Reified type parameters
class CardboardBox: Container<BreakableThing> { //BreakableThing 제네릭 유형의 Container 구
    private var items = mutableListOf<BreakableThing>() //item 저장하기 위한 list

    override fun contents(): List<BreakableThing> {
        //T가 BreakableThing으로 대체되었으므로 CardboardBox의 item 목록을 복사하여 반환한다.
        return items.toList()
    }

    override fun canAddAnotherItem(): Boolean { //CardboardBox가 두 가지 item만 가질 수 있도록 제한
        return items.count() < 2
    }

    override fun addItem(item: BreakableThing) { //추가
        items.add(item)
    }

    override fun canRemoveAnotherItem(): Boolean { //삭제 할 수 있는 지 여부 확인
        return items.count() > 0
    }

    override fun removeItem(): BreakableThing { //삭
        val lastItem = items.last()
        items.remove(lastItem)
        return lastItem
    }

    override fun getAnother(): Container<BreakableThing> { //다른 CardboardBox 생
        return CardboardBox()
    }
}




//Type erasure
//제네릭 형식이 클래스나 인터페이스로 전달 되면 제네릭 제약 조건에 대한 정보만 컴파일러에서 실제 유지되며, 제네릭의 구체적인 형식에 대한 정보는 저장되지 않는다.
//이를 Type erasure라고 한다. 일반 제약 조건에서 준수해야 하는 인터페이스가 있으면 인터페이스에 정의된 모든 함수를
//일반 유형을 사용하는 인터페이스 또는 클래스에서 사용할 수 있다.




//Generic type variance (a.k.a., in and out declarations) //p.286
//제네릭을 사용하는 클래스나 인터페이스로 선언할 수 있는 두 가지 유형의 variance이 있다.
//• in variance는 제네릭 형식이 매개 변수나 다른 형식으로 전달되는 경우에만 사용됨을 의미한다.
//• out variance는 제네릭 형식이 반환 값이나 자신의 형식에서 오는 다른 것에 사용됨을 의미한다.
//이 variance를 사용해, 제네릭 제약 조건과 제네릭으로 할 수 있는 작업을 제한하고 컴파일러에게 명확하게 알려 줄 수 있다.

//• Covariant : <out T>의 유형이다. T는 반환값의 일부일 수 있기 때문에 동일한 제네릭 형식의 관계는 상위, 하위 클래스와 비슷하다.
//  Int는 Number의 하위 유형이므로 List<Int>를 List<Number> 유형의 변수에 입력할 수 있다.
//• Contravariant : <in T>의 유형이다. T는 매개변수로만 사용되므로, 상위, 하위 클래스의 역 관계를 생각할 수 있다.
//  Number는 Int의 상위 유형이므로 Comparable<Number> 를 Comparable<Int> 에 할당할 수 있다.
//• Invariant : <T>의 유형이다. T 개체를 가져와 반환하기 때문에 동일한 제네릭 형식만을 사용해야 한다.




fun main(args: Array<String>) {
    //Anatomy of standard library generic types

    //Lists
    //List의 기본 선언은 다음과 같다. //interface List<out E> : Collection<E>
    //• Collection 인터페이스를 구현하는 또 다른 인터페이스이다.
    //• 여기서 E 를 제네릭 타입이라고 한다. List의 기본 유형과 Collection의 유형이 같아야 함을 나타낸다.
    //•  out variance는 제네릭 형식이 반환 값이나 자신의 형식에서 오는 다른 것에 사용됨을 의미한다.
    //흔히 제네릭 타입에 T를 사용하기도 한다. 어떤 클래스명과 충돌하지 않는다면 어떤 이름을 사용해도 상관 없다(T, E 를 주로 사용).
    //이 제네릭 타입은 실제 코드가 실행될 때는 구체적인 유형으로 채워진다.
    val names: List<String> = listOf("Bob", "Carol", "Ted", "Alice") //Immutable
    println("Names: $names")
    val firstName = names.first() //fun <T> List<T>.first(): T
    println(firstName)
    //여기에서 T를 세 가지의 제네릭 타입으로 사용한다.
    //1. 첫 번째 <T>는 제네릭 형식으로 T를 사용하는 함수가 될 것임을 나타낸다.
    //2. 두 번째 <T>는 이 함수를 호출하는 List가 해당하는 일반 유형(T)의 객체만 포함하는 list임을 나타낸다.
    //3. 세 번째 T는 반환 값이 list의 요소 타입과 동일한 T 임을 나타낸다. <>가 없는 이유는 반환 타입이 단순한 T이기 때문이다.
    //여기서 T 타입이 무엇인지 신경 쓸 필요 없다. 세 곳 모두에서 같은 유형이라는 것을 의미할 뿐이다.

//    val names: List<String> = listOf("Bob", "Carol", "Ted", "Alice")
    //위와 같이 해당 유형을 명시하지 않아도, 컴파일러가 타입을 유추하기 때문에 명시적으로 선언하지 않아도 된다.

    val things = mutableListOf(1, 2)
//    things.add("Steve") //Kotlin이 타입 추론해, things가 <Int>라는 것을 알기 때문에 String을 추가하려면 오류가 난다.
    println("Things: $things")

    //여러 타입을 한 List에 넣고 싶다면 Any를 사용할 수 있다. 이는 Kotlin의 슈퍼 클래스이다. Any를 사용하려면 명시적으로 컴파일러에게 알려야 한다.
//    val things: MutableList<Any> = mutableListOf(1, 2)
//    val things = mutableListOf<Any>(1, 2)
    //위와 같이 Any 타입을 명시적으로 사용해 구현해 준다. 이후에 things.add("Steve") 로 다른 타입을 추가해 줄 수 있다.

    //Maps
    //key - value를 사용하는 맵의 선언은 다음과 같다 //interface Map<K, out V>
    //여기서 K와 V 는 다른 제네릭 타입을 나타낸다.
    val map = mapOf(
            Pair("one", 1),
            Pair("two", "II"),
            Pair("three", 3.0f)
    )
    //타입 추론으로 Map<String, Any>임을 알 수 있다.
//    val one = map.get(1)
//    val one = map[1]
    //키의 타입이 String 이므로 오류가 난다.

    //제네릭을 활용하는 맵의 또 다른 장점은 키 또는 값의 유형을 기반으로 작업을 수행할 수 있다는 것이다.
    //각 키 또는 값은 개별적으로 액세스할 수 있기 때문이다. 모든 키는 고유해야 하므로 Set<K>로 액세스할 수 있다.
    //값은 고유할 필요가 없으므로 Collection<V>로 반환된다. 위의 예시에서는 Set<String>,  Collection<Any>가 된다.
    val valuesForKeysWithE = map.keys
            .filter { it.contains("e") } //e를 포함하고 있는 키를 필터링한다.
            .map { "Value for $it: ${map[it]}" } //필터링 된 것으로 새 맵을 생성한다.
    println("Values for keys with E: $valuesForKeysWithE") //e를 포함하는 키로 필터링된 새 맵을 출력한다.
    //제네릭의 가장 큰 장점은 이런 기본 타입 외에, 사용자가 정의한 타입(class, object 등)을 사용할 수 있다는 데에 있다.




    //Extension functions on types with generic constraints
    println("Names: ${names.toBulletedList()}")
    println("Values for keys with E: ${valuesForKeysWithE.toBulletedList()}")




    //Creating your own generic constraints
    val cheapThings = listOf(
            CheapThing("Cinder Block table"),
            CheapThing("Box of old books"),
            CheapThing("Ugly old couch"))
    //Mover 생성자에 사용할 list를 생성해 준다. T 타입은 CheapThing이 된다.
    val cheapMover = Mover(cheapThings)

    cheapMover.moveEverythingToTruck(null)
    cheapMover.moveEverythingIntoNewPlace()
    cheapMover.finishMove()
    //Mover 클래스는 어떤 유형의 객체든 상관없이 작동한다.

    val television = BreakableThing("Flat-Screen Television")
    val breakableThings = listOf(
            television,
            BreakableThing("Mirror"),
            BreakableThing("Guitar")
    )
    val expensiveMover = Mover(breakableThings)

    expensiveMover.moveEverythingToTruck(CardboardBox())

    television.smash()

    expensiveMover.moveEverythingIntoNewPlace()
    expensiveMover.finishMove()
    //Mover 클래스는 어떤 유형의 객체든 상관없이 작동한다.




    //Generic type variance (a.k.a., in and out declarations)
    val ints = listOf(1, 2, 3) //List<out T> 으로 선언되어 있다.
    val numbers: List<Number> = ints //Number도 Int로 되므로 문제없이 작동한다.

//    val moreInts: List<Int> = numbers //하지만 반대로는 작동하지 않는다.
    //List<out T> 으로 선언되어 있어 Int가 Number로 반환될 수 없기 때문이다.

    val mutableInts = mutableListOf(1, 2, 3) //List와 달리 MutableList은 MutableList<T>로 선언되어 있다.
//    val mutableNumbers: MutableList<Number> = mutableInts
    //MutableList<T>이므로 항상 동일한 유형이어야 하기 때문이다. 하위 유형을 서로 바꿔서 사용할 수 없다.
    //따라서 T는 항상 하위 유형 또는 상위 유형이 아닌 고유한 유형이어야 한다.

    //Comparable의 인터페이스는 다음과 같다.
//    interface Comparable<in T> {
//        operator fun compareTo(other: T): Int
//    }

    fun compare(comparator: Comparable<Number>) {
        //Comparable<in T> 이므로 Int, Float 모두 값을 비교할 수 있다.
        val int: Int = 1
        comparator.compareTo(int)
        val float: Float = 1.0f
        comparator.compareTo(float)

        val intComparable: Comparable<Int> = comparator
        //Comparable<Int>로 할당을 하면, intComparable은 Comparable<Number>이 아니므로 하위유형과 비교할 수 없다.
        intComparable.compareTo(int)
        //Int는 Number의 하위 유형이므로 compareTo로 비교할 수 있다. 따라서 Comparable<Int>로 사용할 수 있다.
//        intComparable.compareTo(float) //Comparable<Int>이므로 Float을 비교할 수 없다.
    }

    //• Covariant : <out T>의 유형이다. T는 반환값의 일부일 수 있기 때문에 동일한 제네릭 형식의 관계는 상위, 하위 클래스와 비슷하다.
    //  Int는 Number의 하위 유형이므로 List<Int>를 List<Number> 유형의 변수에 입력할 수 있다.
    //• Contravariant : <in T>의 유형이다. T는 매개변수로만 사용되므로, 상위, 하위 클래스의 역 관계를 생각할 수 있다.
    //  Number는 Int의 상위 유형이므로 Comparable<Number> 를 Comparable<Int> 에 할당할 수 있다.
    //• Invariant : <T>의 유형이다. T 개체를 가져와 반환하기 때문에 동일한 제네릭 형식만을 사용해야 한다.
}
