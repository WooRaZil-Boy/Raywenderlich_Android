fun main(args: Array<String>) {
    //Comparison operators
    val yes: Boolean = true //Boolean으로 선언해 준다. cf. Swift에서는 bool
    val no: Boolean = false

    //타입 추론이 가능하다.
//    val yes = true
//    val no = false

    //Boolean operators
    //부울은 값을 비교하는 데 사용된다.
    val doesOneEqualTwo = (1 == 2) //Kotlin에서는 항등 연산자를 ==로 표현한다.
    val doesOneNotEqualTwo = (1 != 2) //!=로 두 값이 같지 않은 지 확인한다.
    val alsoTrue = !(1 == 2) //접두어로 ! 를 입력하면 부울 값을 토글한다.

    val isOneGreaterThanTwo = (1 > 2)
    val isOneLessThanTwo = (1 < 2)
    //<=, >= 연산자도 있다.

    //Boolean logic
    val and = true && true //모든 부울이 모두 참일 때만 true
    val or = true || false //하나 이상의 부울이 참일 때 true

    val andTrue = 1 < 2 && 4 > 3
    val andFalse = 1 < 2 && 3 > 4
    val orTrue = 1 < 2 || 3 > 4
    val orFalse = 1 == 2 || 3 == 4

    val andOr = (1 < 2 && 3 > 4) || 1 < 4 //여러 개를 조합해 이와 같이 복잡한 연산을 할 수도 있다.

    //String equality
    //똑같이 항등연산자 == 를 사용해서 문자열을 비교할 수 있다.
    val guess = "dog"
    val dogEqualsCat = guess == "cat" //false

    val order = "cat" < "dog" //true //사전 배열 순으로 크기를 비교한다.




    //The if expression
    if (2 > 1) { //true 인 경우에만 if 절을 실행한다. //Swift에서와 달리 반드시 괄호로 조건절을 묶에 줘야 한다.
        println("Yes, 2 is greater than 1.")
    }

    val animal = "Fox"
    if (animal == "Cat" || animal == "Dog") { //true일 경우 실행
        println("Animal is a house pet.")
    } else { //true가 아닐 경우 실행
        println("Animal is not a house pet.")
    }

    //한 줄에 if-else를 모두 표현해 줄 수도 있다.
    val a = 5
    val b = 10

    val min: Int
    if (a < b) {
        min = a
    } else {
        min = b
    }
    //이는 아래와 같다.
//    val min = if (a < b) a else b

    val max: Int
    if (a > b) {
        max = a
    } else {
        max = b
    }
    //이는 아래와 같다.
//    val max = if (a > b) a else b

    //이렇게 특정 프로그래밍 언어에 대해 예상되는 방식으로 작성하는 것을 idiomatic(관용구) 코드라고 한다.
    //기본 라이브러리에 min(), max()가 있으므로 이를 활용할 수 있다.

    //else-if 를 중첩해 줄 수도 있다.
    val hourOfDay = 12
    val timeOfDay = if (hourOfDay < 6) {
        "Early morning"
    } else if (hourOfDay < 12) {
        "Morning"
    } else if (hourOfDay < 17) {
        "Afternoon"
    } else if (hourOfDay < 20) {
        "Evening"
    } else if (hourOfDay < 24) {
        "Late evening"
    } else { //위의 모든 조건이 나올 수 있는 경우를 포함한다면, else를 사용하지 아도 된다.
        "INVALID HOUR!"
    }
    println(timeOfDay)
    //만족하는 조건이 나올 때까지 여러 조건을 하나씩 실행한다. 여러 조건에 true라도, 처음 해당하는 true 조건 코드만 실행된다.

    //Short circuiting
    //Short circuiting은 여러 중첩된 AND, OR 연산이 있을 때 중요한 개념이다.
    if (1 > 2 && animal == "Matt Galloway") {
        // ...
    }
    //위의 경우에서, 첫 번째 조건인 1 > 2가 거짓이므로, 뒤의 조건에 상관없이 전체 표현은 false가 된다.
    //따라서 두 번째 조건은 검사하지 않고 바로 false로 판단해 넘어간다.

    if (1 < 2 || animal == "Matt Galloway") {
        // ...
    }
    //마찬가지로, 첫 번째 조건 1 < 2 가 참이므로, 뒤의 조건에 상관없이 전체 표현은 true가 된다.
    //따라서 두 번째 조건은 검사하지 않고 바로 true로 판단해 넘어간다.

    //Encapsulating variables
    //표현식에 중괄호로 범위를 표현해 변수를 캡슐화 한다.
    var hoursWorked = 45
    var price = 0
    if (hoursWorked > 40) {
        val hoursOver40 = hoursWorked - 40 //Encapsulating variables
        price += hoursOver40 * 50
        hoursWorked -= hoursOver40
    }
    price += hoursWorked * 25
    println(price)
//    println(hoursOver40) //범위를 벗어났으므로 오류 //Unresolved reference: 'hoursOver40'
    //그러나 부모 범위에 있는 변수와 상수는 이에 상관없이 사용할 수 있다(여기에서는 hoursWorked, price).




    //Loops

    //While loops
    //While 루프는 조건이 참일 때 해당 코드 블록을 반복한다.
    //반복이 될 때마다 조건을 확인한다. true이면 루프가 실행되면서 다음 반복으로 넘어가고, false이면 중지된다.
//    while (true) {} //이는 항상 참이므로 무한 루프가 된다.
    var sum = 1
    while (sum < 1000) { //sum이 1000보다 작을 때 반복한다.
        sum = sum + (sum + 1)
    }
    //iteration 9, sum = 1023에서 종료된다.

    //Repeat-while loops
    //do-while loop 라고도 한다. while과 다른 점은 루프의 끝에서 조건을 검사한다는 것이다.
    sum = 1
    do {
        sum = sum + (sum + 1)
    } while (sum < 1000)
    //조건에 따라 일반 while과 결과가 다를 수 있다. do-while은 최소 1번의 실행이 보장된다.

    //Breaking out of a loop
    sum = 1
    while (true) {
        sum = sum + (sum + 1)
        if (sum >= 1000) {
            break //break를 사용해서 loop의 실행을 중지할 수 있다.
        }
    }
}