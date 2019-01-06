fun main(args: Array<String>) {
    //Kotlin에는 함수 외에도 코드를 재사용 가능한 블록으로 나눌 수 있는 람다라는 또 다른 객체가 있다.
    //이는 배열이나 맵과 같은 collection을 다룰 때 유용하다. 람다는 단순히 이름 없는 함수로 변수에 할당하고 다른 값과 같이 전달할 수 있다.
    //Swift의 Closure와 같다.

    


    //Lambdas basics
    //람다는 익명함수이다. 자체 범위 내에서 변수와 상수를 "close over" 할 수 잇기 때문에 클로저라 한다.
    //이는 람다가 중첩된 함수로 작동하는 주변 컨텍스트의 변수 또는 상수에 액세스해 저장하고 조작할 수 있음을 의미한다.
    //람다 본문에 사용되는 변수와 상수는 람다에 의해 캡쳐된다.
    //람다 자체가 이름이 없기 때문에 사용하기 위해서는 변수 또는 상수에 할당해야 한다.

    var multiplyLambda: (Int, Int) -> Int //자료형은 (Int, Int) -> Int
    //함수의 선언과 동일하다. 람다는 함수 유형이다.
    multiplyLambda = { a: Int, b: Int -> Int //람다 할당
        a * b 
    }
    //함수 선언과 유사하지만 약간의 차이가 있다. : 대신 -> 로 반환 형식을 나타낸다. 이후 본문이 시작되며 마자믹 식의 값을 반환한다.

    val lambdaResult = multiplyLambda(4, 2) // 8 //정의된 람다를 함수와 같이 사용할 수 있다.
    //람다는 함수와 달리 인수에 이름을 사용할 수 없다. multiplyLambda(a = 4, b = 2) 이런 식으로 쓸 수 없다.

    //Shorthand syntax
    //람다는 간소화된 함수이다. 구문을 줄일 수 있는 여러 가지 방법이 있다.
    multiplyLambda = { a, b -> //Kotlin의 타입 추론을 사용해 타입을 명시하지 않고 구문을 단축시킬 수 있다.
        a * b //return value의 type을 추론할 수 있다.
    }

    //it keyword
    //매개 변수가 하나인 경우, it 키워드로 더 짧게 줄일 수 있다.
    var doubleLambda = { a: Int ->
        2 * a 
    }
    //위의 람다를 아래와 같이 줄일 수 있다.
    doubleLambda = { 2 * it } //하나의 매개 변수를 it으로 처리한다. 
    //Swiftdptj $와 같다. 하지만 Swift에서는 $로 매개변수를 모두 가져올 수 있지만, Kotlin 람다에서는 하나의 매개변수만 가능하다.
    val square: (Int) -> Int = { it * it } //제곱은 이렇게 표현할 수 있다.

    //Lambdas as arguments
    fun operateOnNumbers(a: Int, b: Int, operation: (Int, Int) -> Int): Int { //operateOnNumbers는 Int를 반환한다.
        //함수의 인자로 (Int, Int) -> Int 타입의 람다를 사용한다.
        val result = operation(a, b)
        println(result)
        return result
    }

    //실제 람다를 사용한 함수의 호출은 다음과 같이 할 수 있다.
    val addLambda = { a: Int, b: Int ->
        println("ADDLAMBDA")
        a+b 
    }

    operateOnNumbers(4, 2, operation = addLambda) // 6 
    //레이블 없이 operateOnNumbers(4, 2, addLambda) 로도 가능하다.
    //람다는 단순히 이름 없는 함수이다. 매개변수로 함수를 전달할 수도 있으므로 람다역시 가능하다.

    fun addFunction(a: Int, b:Int) = a + b
    operateOnNumbers(4, 2, operation = ::addFunction) // 6  
    //레이블 없이 operateOnNumbers(4, 2, ::addFunction) 로도 가능하다.
    //매개변수가 람다인지 함수인지 상관없이 동일한 결과가 나온다. 자료형만 같으면 된다.

    operateOnNumbers(4, 2, operation = { a: Int, b: Int -> //inline lambda
        //람다를 정의하고, 로컬 변수 또는 상수에 할당할 필요 없이 인자로 넘기는 부분에 람다를 선언해 줄 수 있다.
        a+b 
    })

    operateOnNumbers(4, 2, { a, b -> //위 코드를 이렇게 줄일 수 있다.
        a+b 
    })

    operateOnNumbers(4, 2, operation = Int::plus) 
    //+ 연산자는 두 개의 인수를 사용해 하나의 결과 반환하는 Int 클래스의 연산자 함수인 plus()이다. 이를 사용해 작성할 수도 있다.

    operateOnNumbers(4, 2) { a, b -> //trailing lambda
        //람다가 마지막 인수인 경우 trailing 구문으로 처리할 수 있다. //Swift의 trailing closure와 같다.
        //레이블을 제거하고 중괄호를 밖으로 빼냈다는 점을 제외하면 이전 람다 코드와 같다.
        a+b 
    }

    //Lambdas with no meaningful return value
    //람다는 마지막 줄을 return한다. 하지만 모든 클로저가 값을 반환해야 하는 것은 아니다.

    var unitLambda: () -> Unit = { //매개변수를 사용하지 않고 Unit 객체만 반환하는 람다를 정의한다. //() -> Unit 타입
        //빈 괄호는 매개 변수가 없음을 의미한다. 람다의 반환값이 없는 경우에 Unit을 반환 유형으로 사용하면 된다.
        //이는 실제로는 의미없는 값을 반환한다.
        println("Kotlin Apprentice is awesome!")
    }
    unitLambda()

    var nothingLambda: () -> Nothing = { throw NullPointerException() }
    //정말로 람다가 아예 값을 반환하지 않도록 하려면 Nothing 타입을 사용하면 된다. //예외 처리의 경우 주로 사용된다.

    //Capturing from the enclosing scope
    //람다는 범위 내에서 변수와 상수에 액세스할 수 있다. scope는 엔티티(변수, 상수 등)에 액세스 할 수 있는 범위를 나타낸다.
    var counter = 0
    val incrementCounter = {
        counter += 1 //람다 외부의 변수를 사용한다.
        //람다는 변수와 동일한 범위에서 정의되었기 때문에 해당 변수에 액세스할 수 있다.
    }
    incrementCounter()
    incrementCounter()
    incrementCounter()
    incrementCounter()
    incrementCounter() //counter는 5가 된다.

    fun countingLambda(): () -> Int {
        var counter = 0
        val incrementCounter: () -> Int = { //람다는 매개변수 없이 Int를 반환한다.
            counter += 1
            counter 
        }
        return incrementCounter //람다를 반환한다.
    }
    val counter1 = countingLambda()
    val counter2 = countingLambda()
    println(counter1()) // > 1
    println(counter2()) // > 1
    println(counter1()) // > 2
    println(counter1()) // > 3
    println(counter2()) // > 2
    //각각 갭쳐한 값이 다르기 때문에 함수를 호출할 때 마다 다른 counter를 얻는다.




    //Custom sorting with lambdas
    val names = arrayOf("ZZZZZZ", "BB", "A", "CCCC", "EEEEE")
    names.sorted() // A, BB, CCCC, EEEEE, ZZZZZZ

    val namesByLength = names.sortedWith(compareBy { 
        //sortedWith에 람다를 지정해 줘서 배열 정렬에 대한 세부 정보를 변경할 수 있다.
        -it.length //그냥 it.length 으로 하면, 길이가 작은 순서대로 정렬된다.
        //-를 붙이면 길이가 긴 순서부터 정렬
    })
    println(namesByLength) // > [ZZZZZZ, EEEEE, CCCC, BB, A]




    //Iterating over collections with lambdas
    //Kotlin에서 collection은 함수형 프로그래밍과 과련된 함수들을 구현해 두었다.
    //각 요소를 변형하거나 특정 요소 필터링 등이 포함된다. 이러한 함수에 람다를 대신 사용할 수 있다.
    val values = listOf(1, 2, 3, 4, 5, 6)
    values.forEach { //forEach로 collection을 iterating(반복) 한다.
        println("$it: ${it * it}")
    }
    // > 1: 1
    // > 2: 4
    // > 3: 9
    // > 4: 16
    // > 5: 25
    // > 6: 36

    var prices = listOf(1.5, 10.0, 4.99, 2.30, 8.19)
    val largePrices = prices.filter { //filter로 조건에 해당하는 값만 필터링할 수 있다.
        it > 5.0 //true가 되는 모든 요소를 필터링해서 가져온다.
    }
    // > [10.0, 8.19]
    //filter로 반환된 배열은 새로운 배열로 원본에 영향을 미치지 않는다.

    val salePrices = prices.map { //각 요소에 코드를 실행하고 순서가 유지된 결과를 반환한다.
        it * 0.9 
    }
    // > [1.35, 9.0, 4.4910000000000005, 2.07, 7.3709999999999996]

    val userInput = listOf("0", "11", "haha", "42")
    val numbers = userInput.map { //map을 사용해 유형을 변경할 수도 있다.
        it.toIntOrNull() //Int로 변환하거나 실패하면 null이 된다.
    }
    println(numbers) // > [0, 11, null, 42] //List<Int?> 유형이 된다.

    val numbers2 = userInput.mapNotNull { //mapNotNull을 사용해, null이 아닌 요소만을 가져올 수 있다.
        //null을 제외한다는 점을 빼면 map과 유사하다.
        it.toIntOrNull() //null이 아닌 요소들 중 Int로 변환한다.
    }
    println(numbers2) // > [0, 11, 42] //List<Int> 유형이 된다.

    var sum = prices.fold(0.0) { a, b -> //fold는 시작값을 가지고 람다 식을 적용한다.
        //Swift의 reduce와 같다.
        a + b
    }
    println(sum) // > 26.980000000000004

    sum = prices.reduce { a, b -> //reduce 는 collection의 첫 요소를 시작값으로 사용한다. 그 외의 연산은 fold와 같다.
        a + b
    }
    println(sum) // > 26.980000000000004

    val stock = mapOf(1.5 to 5, 10.0 to 2, 4.99 to 20, 2.30 to 5, 8.19 to 30)
    var stockSum = 0.0
    stock.forEach {
        stockSum += it.key * it.value
    }
    println(stockSum) // > 384.5
}