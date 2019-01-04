//How a computer works
//중앙 처리 장치 (CPU)가 초당 수천만 번 처리되는 것이 컴퓨터.
//CPU는 레지스터라는 작은 메모리 유닛이 있다. 여기에 숫자가 저장되고
//CPU는 RAM(Random Access Memory, 컴퓨터 주 메모리)에서 레지스터로 숫자를 읽어들인다.
//반대로 레지스터에 저장된 숫자를 RAM에 다시 쓸 수 있다.
//Kotlin은 Java Virtual Machine(JVM)의 위에서 실행 되기 때문에 컴파일러와 OS 사이에 별도의 레이어가 있다.
//Kotlin 컴파일러는 바이트 코드(bytecode)를 생성한다. 바이트 코드는 JVM에서 실행되고 그 과정에서 원시 코드(native cod)로 변환된다.
//JVM 없이 Kotlin을 원시 코드로 직접 컴파일 할 수도 있다.

//Representing numbers
//컴퓨터에서 모든 정보는 숫자로 표현된다. 이미지도 수천 수백 만 개의 픽셀로 이루어져 있는데,
//픽셀은 RGB의 숫자로 이루어져 있다. 컴퓨터에서는 2진수(Binary numbers)를 사용한다.
//하나의 2진수인 비트(bit)는 0 혹은 1을 나타낸다. 4비트는 니블(nibble), 8비트가 1바이트(byte)이다.
//CPU의 비트는 처리할 수 있는 숫자를 나타낸다. 더 큰 숫자를 처리할 수 있지만, 분할해야 한다.
//32-bit CPU 에서는 이진수 11111111111111111111111111111111까지 표현할 수 있고, 이는 4,294,967,295 이다.
//이진수로 모든 것을 표현하는 것은 가독성에 문제가 있으므로 보통 16진수로 표현한다(0 ~ 9 + a ~ f).
//32비트의 최대값을 16진수로 나타내면 ffffffff이 된다. 더 직관적이다.

//How code works
//algorithm을 구현하는 방법을 풀어 쓴 것을 의사코드(pseudo-code)라 한다.
//컴파일러는 작성된 코드를 해석하고, CPU가 실행할 수 있는 명령어로 변환한다.

import kotlin.math.*

fun main(args: Array<String>) {
    //Getting started with Kotlin

    //Code comments
    //Kotlin도 다른 언어와 마찬가지로 컴파일러에서 무시되는 주석을 쓸 수 있다.
    //주석은 '/'를 두 번 입력해서 사용한다(single line comment).
    //여러 줄 주석은 /* */ 를 사용한다(multi-line comment).
    //Kotlin에서는 주석의 중첩도 가능하다
    /* This is a comment.

        /* And inside it
        is
        another comment.
        */

    Back to the first.
    */

    //Printing out
    //Kotlin에서는 println을 사용해 출력을 할 수 있다.
    println("Hello, Kotlin Apprentice reader!")




    //Arithmetic operations

    //Simple operations
    println(2 + 6)  //OK
    println(2+6)    //OK
    println(2 +6)   //OK
    println(2+ 6)   //OK
    //Kotlin에서는 공백이 들어가도 된다(Swift에서는 한쪽에만 공백이 있는 경우 오류가 난다).

    //Decimal numbers
    println(22 / 7) //이 결과는 3이다. 표현식을 정수로만 사용하면 Kotlin도 결과를 정수로 만들기 때문이다.
    println(22.0 / 7.0) //이렇게 해야 소수의 결과를 얻게 된다.

    //The remainder operation
    //나머지 연산은 %로 한다.
    println(28 % 10) //8
    println(28.0 % 10.0) //8.0
    println("%.0f".format(28.0 % 10.0)) //format specifier를 지정하여 출력할 수 있다.

    //Shift operations
    //Kotlin에서는 Shift left: shl, Shift right: shr 로 쓴다이는 피연산자 사이에 삽입하는 중위 함수이다(infix function).
    //14(00001110) 를 2자리 만큼 shl 하면 56(00111000)이 된다.
    println(1 shl 3)
    println(32 shr 2)
    //두 개 모두 8이 된다.
    //Shift operation은 곱셈, 나눗셈을 쉽게 한다. 예전에는 이렇게 처리하는 것이 훨씬 빨랐지만, 현재는 임베디드 시스템이 아닌 이상 사용할 일이 잘 없다.




    //Math functions
    //standard library인 math를 import 해 와야 한다. main 함수 위에 import kotlin.math.* 를 입력해 준다.
    println(sin(45 * PI / 180)) // 0.7071067811865475
    println(cos(135 * PI / 180)) // -0.7071067811865475
    println(sqrt(2.0)) // 1.414213562373095
    println(max(5, 10)) // 10
    println(min(-5, -10)) // -10
    println(max(sqrt(2.0), PI / 2))




    //Naming data
    //Kotlin에서는 각 데이터를 참조할 때 사용할 수 있는 이름을 지정할 수 있다. 여기에는 데이터의 종류를 나타내는 유형이 있다.

    //Constants
    val number: Int = 10
    val pi: Double = 3.14159 //Doble은 Float 정확도의 두 배 정도 되기 때문에 Double이라 한다.
    //Float이 더 적은 메모리를 차지하지만, 일반적으로 숫자에 대한 메모리 사용은 크지 않아 대부분 Double을 사용한다.
    //number = 0 // Val cannot be reassigned : 상수는 변경할 수 없다. 따라서 변경되지 않는 값에 유용하다(ex. 생일).

    //Variables
    var variableNumber: Int = 42 //데이터를 변경해야 할 때는 변수를 사용한다(ex. 계좌 잔고).
    variableNumber = 0
    variableNumber = 1_000_000 //Swift와 같이 언더스코어로 자리수를 표시해 줄 수 있다.

    //Using meaningful names
    //변수나 상수는 의미하는 바를 명확히 나타내도록 이름 지어야 한다. camel case name을 사용한다.

    //Increment and decrement
    var counter: Int = 0
    counter += 1 // counter = 1, counter = counter + 1 와 같다.
    counter -= 1 // counter = 0, counter = counter - 1 와 같다.

    counter = 10
    counter *= 3  // same as counter = counter * 3 // counter = 30
    counter /= 2  // same as counter = counter / 2 // counter = 15
}

const val reallyConstant: Int = 42 //함수 밖 최상위 레벨에 const 키워드를 추가하면, compile-time constant이 된다.
//const로 선언된 value는 String, Int, Double과 같은 기본 유형으로 초기화해야 한다. //Java, Swift의  static