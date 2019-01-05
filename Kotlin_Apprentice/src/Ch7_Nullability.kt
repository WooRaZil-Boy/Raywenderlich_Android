fun main(args: Array<String>) {
    //Kotlin에서 Int, String 등의 자료형을 선언하는 경우에는 값이 있음을 보증한다.
    //Nullable 유형은 값이 없을 수도 있음을 타나내는 자료형이다.




    //Introducing null
    //프로그래밍 시에 때로는 값이 없을 수도 있는 자료들을 고려해야 한다.
    var name = "Dick Lucas"
    var age = 24
    var occupation = "Software Developer & Author"
    //여기서 실업 상태가 된다면, 직업 변수는 값이 "없게" 된다. //빈 문자열로 대체할 수 있지만, null이 더 나은 해결책이다.

    //Sentinel values
    //값이 없는 것과 같은 특수한 조건을 나타내는 유효한 값을 Sentinel value라고 한다.
    //일반적으로 Nullable이 없는 언어에서는 빈 문자열 등으로 표현한다.
    var errorCodeLiteral = 0
    //서버에서 오류 코드를 표현한다고 할 때, Nullable 로 표현할 수 없는 경우, 0 으로 표현한다 생각해 보자.
    //실제 0은 유효한 코드일 수도 있고, 서버가 변경되는 경우 나타내는 의미가 달라질 수도 있다. 이런 경우 doc을 확인해 보지 않으면 알기 힘들다.

    //위와 같은 두 경우에서 "값이 없음"을 나타낼 수 있다면 더 유연하게 대처할 수 있다.
    //null은 값이 없는 경우를 의미하며, Kotlin에서는 null 가능성을 처리할 수 있는 새로운 유형(nullable type)을 사용한다.
    //이를 사용해 모호한 값을 제거할 수 있다. Swift의 Optional과 같은 개념이다.




    //Introducing nullable types
    //Nullabled은 값이 없음을 나타내기 위한 Kotlin의 해답이다. Nullable은 실제 값이거나 null이다.
    //상자를 생각해 볼 수 있다. 상자는 값을 포함할 수도 없을 수도 있지만, 상자는 존재하고, 열어서 확인해 봐야 한다. //p.116
    //반대로 일반 유형인 String, Int 등은 상자 없이 null이 아닌 항상 그 자체의 값을 가지고 있다.
    //슈뢰딩거의 고양이를 생각해 볼 수도 있다.

    var errorCode: Int? //nullable의 선언은 일반 자료형 타입 뒤에 ?를 추가한다.
    //errorCode의 타입은 Int? 으로 Int 일수도, null 일 수도 있는 타입이다.
    //nullable인 타입 추론으로 선언할 수 없고 항상 명시적으로 선언해 줘야 한다.
    errorCode = 100 //Int 값 설정
    errorCode = null //null 설정
    //Nullable 상자는 항상 존재한다. 상자에 실제 변수를 채우거나 null을 할당해 비울 수 있다.




    //Checking for null
    //제한적으로 nullable type을 non-null type처럼 사용할 수 있다.
    var result: Int? = 30
    println(result) //30
    //위와 같은 경우에는 30을 출력한다. 
//    println(result + 1)
    //하지만 이 경우에는 박스 안의 상수 자체에 정수를 추가하려 시도하기 때문에 오류가 난다.

    //Not-null assertion operator
    var authorName: String? = "Dick Lucas"
    var authorAge: Int? = 24

    //상자에서 nullable을 제거하고 일반 type으로 값을 가져오는 방법에는 두 가지가 있다.
    //첫 번째는 not-null assertion operator인 이중 느낌표(!!)을 사용하는 것이다.
    val ageAfterBirthday = authorAge!! + 1 //!!는 상자에서 값을 꺼내라는 것을 나타낸다.
    //ageAfterBirthday 유형은 Int?가 아닌 Int가 된다.
    println("After their next birthday, author will be $ageAfterBirthday")
    //하지만, not-null assertion operator은 신중하게 사용해야 한다. non-null type이 확실할 때만 사용한다.
     authorAge = null
//    println("After two birthdays, author will be ${authorAge!! + 2}")
    //null-pointer exception 오류가 난다. 런타임에 오류가 발생하기 때문에 crash 된다.

    //Smart casts
    //두 번째 방법으로 특정 조건 하에서 null이 값을 가지고 있는 지 검사한 후, non-null type처럼 사용할 수 있다.
    var nonNullableAuthor: String
    var nullableAuthor: String?
    if (authorName != null) { //값이 포함되어 있는 경우
        nonNullableAuthor = authorName //String
    } else { //값이 포함되어 있지 않는 경우
        nullableAuthor = authorName //String?
    }
    //smart casts를 사용하는 것이 not-null assertions보다 훨씬 안전하다.




    //Safe calls
    //하지만 모든 nullable에 일일이 smart cast를 적용하는 것은 과다할 때가 있다.
    //이런 경우에는 safe call를 사용할 수 있으며 ?. 연산자를 사용한다.
    val nameLength = authorName?.length
    println("Author's name has length $nameLength.")
    // > Author's name has length 10.

    //이를 chain으로 연결할 수도 있다.
    val nameLengthPlus5 = authorName?.length?.plus(5)
    println("Author's name length plus 5 is $nameLengthPlus5.")
    // > Author's name length plus 5 is 15.

    //Safe call 중에 해당 Nullable이 null이면 null을 반환하고 종료한다. 따라서 Safe call의 반환형식은 Nullable이다.

    //The let() function
    //let() 함수를 사용해, nullable을 처리할 수도 있다.
    authorName?.let { //Swift의 optional을 해제할 때 if let 과 비슷하다.
        nonNullableAuthor = authorName
    }




    //Elvis operator
    //Nullable의 값에 상관없이 non-nullable-type을 할당하고자 할때 사용한다.
    var nullableInt: Int? = 10
    var mustHaveResult = nullableInt ?: 0 //null인 경우 0, null이 아닌 경우 10 //Int type이 된다.
    //var mustHaveResult = if (nullableInt != null) nullableInt else 0 //이 코드와 같다.
    //?: 를 90도로 회전해서 보면 엘비스와 닮았기에 엘비스 연산자라고 한다.
}