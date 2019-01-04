fun main(args: Array<String>) {

    //Type conversion
    var integer: Int = 100
    var decimal: Double = 12.5

//    integer = decimal // Type mismatch: inferred type is Double but Int was expected
    // JS등에서는 자동 변환을 할 수도 있지만, Kotlin에서는 오류가 난다.
    integer = decimal.toInt() //이렇게 명시적으로 변환 해 주어야 한다. Int로 변환 시에는 소수점을 버린다.

    //Operators with mixed types
    val hourlyRate: Double = 19.5
    val hoursWorked: Int = 10
    val totalCost: Double = hourlyRate * hoursWorked
//    val totalCost: Double = hourlyRate * hoursWorked.toDouble()
    //이렇게 명시적으로 변환할 수도 있지만, 산술 연산에서 혼합 유형의 경우(예상할 수 있는 경우) 자동으로 변환할 수 있다.

    //Type inference
    //컴파일러가 유형을 추론할 수 있는 경우 자료형을 명시적으로 적어줄 필요 없다.
    val typeInferredInt = 42//Int //커서를 변수(상수)에 두고 control + shift + p 를 누르면, 형을 확인할 수 있다.
    val typeInferredDouble = 3.14159 //Double

    val wantADouble = 3 //Double형을 만들고 싶지만, 타입 추론은 Int로 된다.
    //이런 경우에는 다음과 같이 Double로 표현해 줄 수 있다.
//    val actuallyDouble = 3.toDouble() //형 변환
//    val actuallyDouble: Double = 3.0 //명시적 선언

//    val actuallyDouble: Double = 3 //Swift와 달리, 이와 같은 명시적 선언은 허용되지 않는다.




    //Strings in Kotlin

    //Characters and strings
    val characterA: Char = 'a' //Char는 단일 문자만 표현할 수 있다.
    //Char 형식은 작은 따옴표로 묶어 단일 문자를 저장한다.
    val stringDog: String = "Dog" //string literal //String은 여러 문자를 표현할 수 있다.
    //String 형식은 큰 따옴표로 묶어 다중 문자를 저장한다. //Swift와 다르다.

//    val stringDog = "Dog" //타입 추론을 자동으로 할 수도 있다.

    //Concatenation
    var message = "Hello" + " my name is " //더하기 연산자로 문자열을 합칠 수 있다.
    val name = "Dick" //상수로 선언하면, 수정을 할 수 없다.
    message += name // "Hello my name is Dick"

    val exclamationMark: Char = '!'
    message += exclamationMark // "Hello my name is Dick!"
    //String과 Char을 합치는 경우에 명시적으로 형 변환할 필요 없다.

    //String templates
    message = "Hello my name is $name!" // "Hello my name is Dick!"
    //Kotlin에서는 interpolation 으로 $를 사용한다.

    val oneThird = 1.0 / 3.0
    val oneThirdLongString = "One third is $oneThird as a decimal." //String 뿐 아니라, 다른 유형을 작성할 때도 사용할 수 있다.
    val oneThirdLongStr = "One third is ${1.0 / 3.0} as a decimal." //literal이 아닌 경우 이렇게 직접 수식 결과를 표현할 수 있다.

    println(oneThirdLongString) //하지만 이렇게 표현하면, 0.3333... 이므로 굉장히 긴 문자열이 되는데, 따로 정밀도를 지정해 줄 수는 없다.
    println(oneThirdLongStr)




    //Multi-line strings
    val bigString = """
        |You can have a string
        |that contains multiple
        |lines
        |by
        |doing this.
        """.trimMargin() //""" """으로 multi line Strings를 만들 수 있다(Swift와 같다).
    //파이프 문자(|)를 사용하면, trimMargin()으로 선행 공백을 지울 수 없다.
    //trimMargin() 없이 그대로 """으로 끝내면, 보이는 그대로 String이 된다.
    println(bigString)




    //Pairs and Triples
    //Tuple이 Pair과 Triple로 나누어져 있다고 생각하면 된다.
    val coordinates: Pair<Int, Int> = Pair(2, 3) //Pair는 2개의 데이터를 표현한다.
    val coordinatesInferred = Pair(2, 3) //Pair도 타입 추정이 가능하다.
    val coordinatesWithTo = 2 to 3 //이렇게 표현할 수도 있다.

    val coordinatesDoubles = Pair(2.1, 3.5) //Pair<Double, Double>
    val coordinatesMixed = Pair(2.1, 3) //Pair<Double, Int>

    val x1 = coordinates.first //first, second로 Pair의 값에 접근할 수 있다.
    val y1 = coordinates.second

    val (x, y) = coordinates //이런 식으로 각 값을 가져와 대입해 줄 수도 있다. //x, y로 각각 할당된다.

    val coordinates3D = Triple(2, 3, 1)
    val (x3, y3, z3) = coordinates3D //상수를 선언하고 Triple의 각 요소를 순서대로 지정한다.
    //이를 풀어 쓰면, 아래와 같다.
//    val x3 = coordinates3D.first
//    val y3 = coordinates3D.second
//    val z3 = coordinates3D.third

    val (x4, y4, _) = coordinates3D //Pair 또는 Triple에서 특정 요소를 생략하려면, underscore로 대체하면 된다.




    //Number types
    //Byte, Short, Long 등을 사용할 수 있지만, Int를 사용하는 것이 일반적이다.
    //Double 또한, Float을 사용할 수 있지만, 최신 하드웨어는 Double에 최적화되어 있으므로 Double을 일반적으로 사용한다.
    val a: Short = 12
    val b: Byte = 120
    val c: Int = -100000
    val answer = a + b + c //Int




    //Any, Unit, and Nothing Types
    //Any 타입은 null을 제외하곤, 어떤 타입으로든 간주될 수 있다. Java의 Object 타입과 비슷하다.
    //Any는 타입의 최상위 객체, Root로 생각할 수 있다. 따라서 아래와 같이 써도 유효하다.
    val anyNumber: Any = 42
    val anyString: Any = "42"

    //Unit은 하나의 값만 나타내는 특별한 유형이다. 제네릭을 더 쉽게 사용할 수 있다는 점을 제외하면, Java의 void와 비슷하다.
    //모든 함수에서 명시적으로 유형을 반환하지 않으면 실제로는 Unit을 반환하게 된다.
    fun add() { //fuc add(): Unit 과 같다.
        val result = 2 + 2
        println(result)
    }


}