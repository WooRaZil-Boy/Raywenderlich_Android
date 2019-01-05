fun main(args: Array<String>) {
    //Fuction을 사용해, 반복되는 코드를 복사 붙여넣기 하는 대신 함수를 실행해 줄 수 있다.

    //Function basics
    fun printMyName() { //fun 키워드로 함수를 선언 한다. cf.Swift 에서는 func
        println("My name is Dick Lucas.")
    }
    printMyName() //함수 호출

    //Function parameters
    fun printMultipleOfFive(value: Int) { //int 형의 value 파라미터(매개 변수)
        println("$value * 5 = ${ value * 5 }")
    }
    printMultipleOfFive(10)

    //parameter와 argument는 자주 혼동되는 용어이다.
    //함수 선언에서 parameter를 사용하고, 호출할 때 parameter에 argument를 전달한다.

    fun printMultipleOf(multiplier: Int, andValue: Int) { //두 개의 parameter가 있는 함수
        println("$multiplier * $andValue = ${multiplier * andValue}")
    }
    printMultipleOf(4, 2)
    printMultipleOf(multiplier = 4, andValue = 2) //호출 시에 명명된 인수를 사용할 수 있다.
    printMultipleOf(andValue = 2, multiplier = 4) //named arguments를 사용할 때는 순서에 상관없이 인수를 사용할 수 있다.

    fun printMultipleOfDefault(multiplier: Int, value: Int = 1) { //default parameter를 설정해 준다. 따로 값을 지정해 주지 않으면 1.
        //대부분의 매개 변수가 특정 값이 될 것으로 예상될 때 기본값을 가지는 것이 유용할 수 있으며, 코드를 단순하게 관리할 수 있다.
        println("$multiplier * $value = ${multiplier * value}")
    }
    printMultipleOfDefault(4)

    //Return values
    fun multiply(number: Int, multiplier: Int): Int { //반환 값을 : 뒤에 적어줘서 값을 반환할 수 있다.
        return number * multiplier //return으로 반환한다.
    }

    fun multiplyAndDivide(number: Int, factor: Int): Pair<Int, Int> { //Pair 타입으로 여러 값을 반환해 줄 수 있다.
        return Pair(number * factor, number / factor)
    }
    val (product, quotient) = multiplyAndDivide(4, 2)

    fun multiplyInferred(number: Int, multiplier: Int) = number * multiplier
    //함수가 한 줄로 표현 가능한 경우에는 중괄호, 반환유형, return을 모두 생략하고 = 로 반환값을 지정해줄 수 있다.

    //Parameters as values
    //함수의 매개 변수는 기본적으로 상수로, 수정할 수 없다(Swift에서도 마찬가지). val로 선언되었다고 생각할 수 있으며 값을 재할당할 수 없다.
    //매개변수의 값을 변경할 수 있으면, 값을 명확하게 확신할 수 없고, 잘못된 데이터 입출력으로 이어질 수 있다.
    fun incrementAndPrint(value: Int): Int {
        val newValue = value + 1 //매개 변수를 변경하여 사용하려면, 새 변수를 선언하여 간접적으로 변수를 지정해 줘야 한다.
        println(newValue)
        return newValue
    }

    //Overloading
    fun getValue(value: Int): Int {
        return value + 1
    }

    fun getValue(value: String): String {
        return "The value is $value"
    }

    //위와 같이 동일한 이름의 두 함수를 사용할 때, overloading이라 한다.
    //하지만, 컴파일러가 두 함수의 차이를 알 수 있어야 하므로 주로 매개변수에서 차이가 난다.
    //• 매개 변수의 수가 다르다.
    //• 매개 변수의 타입이 다르다.
    //반환 유형만 다른 경우에는 같은 함수로 취급되어 오류가 난다. // Conflicting overloads error
    //Overloading은 관련성이 있는 유사한 동작에 대해서만 주의해서 사용해야 한다.




    //Functions as variables
    //Kotlin에서 함수는 Int와 String과 같은 하나의 Type(데이터 유형)이다. 따라서 값을 할당할 수 있다.
    fun add(a: Int, b: Int): Int {
        return a + b
    }

    var function = ::add  //method reference operator인 :: 을 사용하여 할당한다.
    //타입은 (Int, Int) -> Int이 된다.
    function(4, 2) //add 함수를 사용하는 것과 마찬가지로 변수 function을 사용할 수 있다.

    fun subtract(a: Int, b: Int) : Int { //type은 (Int, Int) -> Int로, add와 같다.
        return a - b
    }

    function = ::subtract //타입이 같으므로 변수에 할당해 줄 수 있다.
    function(4, 2) //subtract의 결과와 같다.

    //변수에 함수를 할당할 수 있으므로, 다른 함수의 인자로 전달해 줄 수도 있다.
    fun printResult(function: (Int, Int) -> Int, a: Int, b: Int) { //세 개의 파라미터가 있다.
        //첫 번째 파라미터의 자료형은 (Int, Int) -> Int 로 함수가 올 수 있다.
        val result = function(a, b)
        print(result)
    }
    printResult(::add, 4, 2)
    //이렇게 함수를 다른 함수로 전달하는 것은 매우 유용하며 재사용 가능한 코드를 작성하는 데 큰 도움이 된다.
    //이러한 방식은 함수형 프로그래밍의 특성이기도 하다.

    //The land of no return
    //절대로 return 되지 않도록 설계된 함수도 있다. ex.응용 프로그램을 종료시키는 함수
    //잠재적으로 오류있는 상태로 계속 진행하는 대신 충돌을 일으켜 종료시키는 것이 더 좋을 때도 있다.
    //또 다른 예는 이벤트 루프를 처리하는 것이다. 이벤트 루프는 사용자가 입력한 내용을 화면에 표시하는 것으로
    //사용자가 보낸 요청을 처리한 다음 이벤트를 응용 프로그램에 전달한다. 그러면 정보가 화면에 표시된다.
    //이러한 루프가 계속해서 돌아가면서 사용자의 이벤트 입력을 기다리는 것을 이벤트 루프라 한다.
    //Android에서는 main thread(UI thread)에서 이런 작업을 처리한다.
    fun infiniteLoop(): Nothing { //함수의 반환 형식을 Nothing으로 설정하면, 함수에서 반환하는 것이 없음을 명시적으로 나타낼 수 있다.
        //이렇게 선언하면, 컴파일러가 함수의 반환형이 없다는 것을 미리 알 수 있기 때문에, 코드 생성시 최적화를 할 수 있다.
        while (true) {

        }
    }

    //Writing good functions
    //함수는 많은 작업을 하는 것이 아니라 하나의 간단한 작업을 하는 것으로 분할하는 것이 좋다.
    //이렇게 하면 쉽게 다른 함수들을 구성해 하나의 로직을 만들 수 있다. 좋은 함수는 같은 input에 대해 동일한 ouput이 나오도록 해야 한다.
}