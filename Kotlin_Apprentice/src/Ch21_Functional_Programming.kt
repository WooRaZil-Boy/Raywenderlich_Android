import java.util.*

//What is functional programming?
//OOP의 핵심은 클래스와 인스턴스에 속성과 메서드가 있다는 것이다. 함수형 프로그래밍은 side effect가 없는 함수 사용을 기반으로 한다.
//side effect는 시스템의 상태를 변경하는 것이다. ex. 화면에 무언가를 출력하거나 객체의 속성값을 변경하는 것
//클래스 인스턴스가 메시지를 서로 주고 받아 내부 상태를 변경하기 때문에 일반적으로 OOP에서는 side effect가 흔하다.
//함수형 프로그래밍의 또 다른 특성은 함수가 일급 객체(first-class citizen) 라는 것이다.
//많은 함수형 프로그래밍 언어는 참조 투명성(referential transparency)이라는 개념이 있다.
//동일한 입력이 주어지면 함수가 항상 동일한 출력 값을 내야한다는 것을 의미한다. 이런 함수를 pure function이라 한다.
//함수형 프로그래밍 언어에서 함수는 비 함수형 프로그래밍 언어에서의 함수보다 수학의 함수와 더 비슷하다.
//Java 8 이전의 Java와 달리 Kotlin은 OOP 및 함수형 프로그래밍을 사용하여 소프트웨어를 별도로 작성하거나 결합하여 코드를 효율적이고 유연하게 작성할 수 있다.





//Robot battle!
class Robot(val name: String) {
    var strength: Int = 0
    private var health: Int = 100
    private var random: Random = Random() //상속 없이 extension으로 새로운 기능을 추가해 줄 수 있다.
    var isAlive: Boolean = true

    init {
        strength = random.randomStrength() //extension 메서드
        report("Created (strength $strength)")
    }

    infix fun attack(robot: Robot) {
        //infix키워드를 사용하는 경우 점과 괄호 없이 호출해 줄 수 있어, 특정한 경우 더 읽기 쉬워진다.
        //ex. firstRobot attack secondRobot
        val damage = random.randomDamage(strength) //extension 메서드
        robot.damage(damage.toInt())
    }

    fun damage(damage: Int) {
        val blocked = random.randomBlock() //extension 메서드

        if (blocked) {
            report("Blocked attack")
            return
        }

        health -= damage
        report("Damage -$damage, health $health")

        if (health <= 0) {
            isAlive = false
        }
    }

    fun report(message: String) {
        println("$name: \t$message")
    }
}




object Battlefield {
    inline fun beginBattle(firstRobot: Robot, secondRobot: Robot, onBattleEnded: Robot.() -> Unit) {
        //마지막 매개변수의 타입을 (Robot) -> Unit 에서 Robot.() -> Unit
        var winner: Robot? = null
        battle(firstRobot, secondRobot)
        winner = if (firstRobot.isAlive) firstRobot else secondRobot
        onBattleEnded(winner) //매개변수로 받아온 함수를 실행한다.
        //extension function은 확장 클래스의 인스턴스를 암시적으로 받는다.
    }

    tailrec fun battle(firstRobot: Robot, secondRobot: Robot) {
        //tailrec 키워드를 사용해, 재귀함수의 stack overflow를 방지한다.
        //tailrec 키워드를 사용하면, Kotlin은 성능 최적화를 위해 적절한 loop로 재귀를 대체한다.
        firstRobot.attack(secondRobot)

        if (secondRobot.isAlive.not()) { //!를 사용한 것과 같다.
            return
        }

        secondRobot.attack(firstRobot)

        if (firstRobot.isAlive.not()) {
            return
        }

        battle(firstRobot, secondRobot) //재귀
        //재귀 함수는 루프를 대체하며, 함수형 프로그래밍에서는 일반적이다.
        //재귀 함수는 함수 호출 스택의 제한을 초과하는 stack overflow에 취약하다.
    }
}




fun onBattleEnded(winner: Robot) {
    winner.report("Won!")
}




//First-class and higher-order functions
//함수형 프로그래밍의 주된 컨셉은 일급 함수이다. 이는 다른 요소와 같이 함수도 조작할 수 있다는 것을 의미한다.
//즉, 함수를 다른 함수의 인수로 전달하고, 함수에서 함수를 반환하거나 변수에 할당할 수 있다.
//함수를 매개 변수로 받거나 함수를 반환하는 함수를 고차 함수(higher-order)라고 한다.

//Function types
//함수를 매개변수로 받거나 반환하는 함수를 선언하려면 함수 유형을 지정해야 한다. 괄호 안에 매개 변수 유형을 쉼표로 구분하여 정의하고 -> 뒤에 반환형을 적는다.
//ex. (Int, Int) -> Float //매개 변수를 사용하지 않고 의미 있는 값을 반환하지 않는 함수 유형은 () -> Unit 이다.

//Passing a function as an argument

//Returning functions
//함수를 인수로 전달하는 것과 마찬가지로 다른 함수에서 함수를 반환할 수도 있다.
fun someFunction(): () -> Int {
    return ::anotherFunction //다른 함수를 반환한다.
}

fun anotherFunction(): Int {
    return Random().nextInt()
}




//Lambdas
//람다는 함수 리터럴로, 할당하거나 함수의 인수나 반환형으로 사용할 수 있다.
val pow1 = { base: Int, exponent: Int -> Math.pow(base.toDouble(), exponent.toDouble()) }
//람다 식은 항상 중괄호로 정의된다. 매개 변수의 이름과 유형을 선언하고 -> 부호 뒤에 본문을 써준다.
//람다 안에서 return 키워드를 사용할 필요 없으며, 반환 유형을 추정하기 때문에 명시적으로 써줘야 할 필요 없다.

val pow2: (Int, Int) -> Double = { base, exponent -> Math.pow(base.toDouble(), exponent.toDouble()) }
//명시적으로 람다 유형을 선언할 수 있지만, 대괄호 안에 매개 변수 유형을 지정할 필요 없다.

val root: (Int) -> Double = { Math.sqrt(it.toDouble()) }
//람다에 하나의 매개변수만 있으면 이름을 지정해 줄 필요 없이 it 키워드를 사용할 수 있다.

//How do lambdas work?
//모든 람다에 대해 Kotlin 컴파일러는 추상 클래스인 Lambda를 확장하고, Function1과 같은 인터페이스를 구현하는 별도의 클래스를 생성한다.
//Function1 인터페이스는 람다 매개 변수의 수에 따라 (Function1, Function2, etc) 등으로 대체된다.

//Closures
//람다는 클로저 역할을 한다. 즉, 자체 외부에서 정의된 변수에 액세스하고 수정할 수 있다.
//Java와 달리 외부에서 선언된 변수를 클로저 내에서 수정할 수도 있다.

//var result = 0
//val sum = { a: Int, b: Int ->
//    result = a + b //이렇게 외부에서 선언된 result 값을 변경할 수 있다.
//}




//Extension functions
//특정 클래스를 extension할 때, 직접 상속하는 것은 좋은 방법이 아닐 때가 있다. 해당 클래스가 이미 다른 클래스를 extension 했더나 open 되지 않았을 수 있다.
fun Random.randomStrength(): Int {
    return nextInt(100) + 10
}

fun Random.randomDamage(strength: Int): Int {
    return (strength * 0.1 + nextInt(10)).toInt()
}

fun Random.randomBlock(): Boolean {
    return nextBoolean()
}




//Lambdas with receivers
//extension function을 위한 receiver를 지정한 것 처럼, 람다에서도 가능하다.




//Anonymous functions
//Anonymous function은 보통의 함수와 비슷하지만 이름이 없다. 변수를 호출하려면 변수에 할당하거나 인수로 다른 함수에 전달해야 한다.

//Battlefield.beginBattle(firstRobot, secondRobot, fun(robot) {
//    robot.report("Won!")
//})
//beginBattle 에서 위와 같이 사용할 수 있다.




//Returning from lambdas
//람다 안에서 return을 사용하면, 외부 함수를 호출한 곳으로 돌아간다. 즉, 람다를 반환하면 외부 함수도 반환된다.
fun calculateEven1() {
    var result = 0

    (0..20).forEach {
        if (it % 3 == 0) return
        if (it % 2 == 0) result += it
    }
    //람다의 return 문이 calculateEven()실행을 멈추기 때문에 result가 출려되지 않는다.

    println(result)
}

//외부 함수까지 멈추지 않게 해야 한다면, qualified return이 필요하다.
fun calculateEven2() {
    var result = 0

    (0..20).forEach {
        if (it % 3 == 0) return@forEach //해당 루프에서만 return 된다. continue를 사용하는 것과 비슷하다.
        if (it % 2 == 0) result += it
    }

    println(result)
}

//위의 함수는 아래의 함수와 같다.
fun calculateEven3() {
    var result = 0

    (0..20).forEach loop@{
        if (it % 3 == 0) return@loop //loop 레이블로 돌아간다.
        if (it % 2 == 0) result += it
    }

    println(result)
}

//익명 함수로 대체하면 해당 함수만 반환하기 때문에 같은 결과를 얻을 수도 있다.
fun calculateEven() {
    var result = 0

    (0..20).forEach(fun(value) {
        if (value % 3 == 0) return
        if (value % 2 == 0) result += value
    })

    println(result)
}




//Inline functions
//다른 함수로 전달하기 위해 각 람다에 대해 Kotlin 컴파일러는 FunctionN을 확장하는 클래스를 생성한다.
//이 경우, 여러 번 수행하면 메모리 사용이 증가하여 응용 프로그램 성능에 영향을 줄 수 있다.
//이런 동작을 피하기 위해, 호출 시에 inline 키워드를 사용해 줄 수 있다.
//이를 사용하면, 추가 클래스가 생성되지 않고 해당 함수와 reciver 람다의 호출이 해당 코드로 대체된다.
//이 방법은 Kotlin 람다 오버헤드에 대한 해결책이긴 하지만, 내부적으로 컴파일 시에 코드의 길이를 증가시킨다.
//매개 변수로 람다를 받지 않는 함수의 경우, 추가 클래스를 생성할 필요 없기 때문에 inline 키워드가 쓸모 없는 경우가 대부분이다.
//그 경우에는 IDE에서 warning이 표시된다.
//@Suppress("NOTHING_TO_INLINE") 을 사용해 해당 경고를 무시할 수 있다.
//또한 함수의 크기가 큰 경우, inline을 사용하면 코드 생성이 더 길어지므로 성능에 문제를 끼칠 수 있다.
//이 경우 함수를 여러 개의 작은 함수로 나눠 필요한 부분에만 inline 키워드를 사용하는 것이 좋다.

//noinline
//고차 함수에서 일부 람다 매개 변수를 inline할 필요 없다면, 람다를 noinline으로 표시할 수 있다.
//함수의 모든 람다 매개 변수에 noinline 키워드를 추가하면, inline은 무의미해 진다.
//그 경우, Kotlin 컴파일러가 추가 클래스를 생성하지 않으므로 함수를 inline할 필요가 없어진다.

//crossinline
//crossinline은 해당 코드 범위 외에서 반환되서는 안 되는 람다 매개 변수에 지정해 준다(로컬이 아닌 곳에서 반환값이 사용되지 않는).
//이는 람다를 받는 함수가 다른 람다 내부에서 이를 호출하는 경우 유용하다.




//Tail recursive functions
//어떤 함수가 tail call 에서 다시 호출되는 경우, tail-recursive 라고 한다. 이 경우, tailrec 키워드를 사용할 수 있으며,
//Kotlin은 성능 최적화를 위해 적절한 loop로 재귀를 대체한다. 이렇게 처리하면, 재귀로 인한 stack overflow를 방지할 수 있다.




//Collections standard library
//Collections standard library의 함수를 사용해 함수형 프로그래밍을 구현할 수 있다.
val participants = arrayListOf<Robot>(
    Robot("Extra-Terrestrial Neutralization Bot"),
    Robot("Generic Evasion Droid"),
    Robot("Self-Reliant War Management Device"),
    Robot("Advanced Nullification Android"),
    Robot("Rational Network Defense Droid"),
    Robot("Motorized Shepherd Cyborg"),
    Robot("Reactive Algorithm Entity"),
    Robot("Ultimate Safety Guard Golem"),
    Robot("Nuclear Processor Machine"),
    Robot("Preliminary Space Navigation Machine")
)

val topCategory1 = participants.filter { it.strength > 80 } //함수형 프로그래밍으로 해당 객체들을 필터링해 줄 수 있다.
//loop를 적용하는 것보다 훨씬 간결하다.

//함수형 프로그래밍을 활용하면, 여러 변환을 동시에 적용할 수 있다.
val topCategory2 = participants
    .filter { it.strength > 80 } //필터링
    .take(3) //첫 3개 요소만 가져온다.
    .sortedBy { it.name } //요소들을 이름순으로 정렬한다.
//함수형 프로그래밍은 side effect가 없는 함수에 관한 개념이다. 위의 코드는 이에 부합한다.
//적용한 filter, take, sortedBy 함수들은 어떤 방식으로든 원래의 목록을 수정하지 않고, 새로운 ArrayList를 반환하다.




//Infix notation
//함수가 member function, extension function, 하나의 인수만 받는 함수의 경우 infix 키워드로 표시해 줄 수 있다.
//이 경우, 점과 괄호 없이 호출해 줄 수 있어, 특정한 경우 더 읽기 쉬워진다.




//Sequences
//Kotlin에서는 시퀀스를 사용하여 lazy collection을 생성할 수 있다. 따라서 크기가 확정되지 않은 collection을 조작할 수 있다.
//해당 collection은 잠재적으로 무한할 수 있다.
val random = Random()
val sequence = generateSequence { //generateSequence를 사용해 시퀀스를 생성한다. type() -> T의 람다를 인수로 받는다.
    random.nextInt(100) //0 ~ 100 범위에서 임의의 int 반환
}
val sequenceCollection = sequence
    .take(15) //첫 15개의 요소만 가져온다. //이때, 해당 시퀀스가 15번 실행된다.
    .sorted() //정렬
    .forEach { println(it) } //각 요소 출력

//시퀀스는 함수형 프로그래밍에서 수학적 문제 해결을 위해 사용할 수 있다.
val factorial = generateSequence(1 to 1) {
    it.first + 1 to it.second * (it.first + 1)
    //factorial은 n * n-1을 계속해야 하므로 다음 결과를 위해 이전 결과를 저장하는 것이 좋다. Pair를 사용할 수 있다.
    //첫 필드에는 인덱스를 저장하고, 두 번째 필드에는 현재 인덱스의 fatorial을 저장한다.
}
val result = factorial.take(10).map { it.second }.last()
//10! 를 구한다.
//위의 내용은 memoization 로 알려진 방법이다.




fun main(args: Array<String>) {
    //Robot battle!
    val firstRobot = Robot("Experimental Space Navigation Droid")
    val secondRobot = Robot("Extra-Terrestrial Air Safety Droid")



    //Lambda
//    val onBattleEnded = { winner: Robot -> winner.report("Won!") }
    //람다를 사용하여 매개변수로 쓰일 함수를 지정해 줄 수 있다.
//    Battlefield.beginBattle(firstRobot, secondRobot, onBattleEnded)

    //위의 식을 아래와 같이 쓸 수도 있다.
    Battlefield.beginBattle(firstRobot, secondRobot) {
        report("Win!")
    }
}






