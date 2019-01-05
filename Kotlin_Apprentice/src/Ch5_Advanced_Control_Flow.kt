fun main(args: Array<String>) {
    //Ranges
    //Range는 정수 시퀀스의 범위를 표현할 수 있다. 일반적으로 for loop에서 많이 사용한다.
    val closedRange = 0..5 //closed range
    //두 개의 점 (..)은 범위가 닫혀 있음을 나타낸다. 여기서는 0, 1, 2, 3, 4, 5까지를 나타내게 된다.
    //Swift에서는 ... 으로 나타낸다.
    val halfOpenRange = 0 until 5 //half-open range //0, 1, 2, 3, 4
    //Swift에서는 ..< 으로 나타낸다.
    val decreasingRange = 5 downTo 0 //감소하는 범위 //5, 4, 3, 2, 1, 0




    //For loops
    //While보다 일반적으로 흔히 사용된다.
    val count = 10
    var sum = 0
    for (i in 1..count) { //1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        sum += i  //50
    }
    //i 상수는 루프 범위 내에서만 사용 가능하다.

    sum = 1
    var lastSum = 0
    repeat(10) { //위의 loop에서 i를 사용할 필요 없을 때, 반복의 횟수를 알고 있다면 repeat으로 표현 가능하다.
        val temp = sum
        sum += lastSum
        lastSum = temp
    }

    sum = 0
    for (i in 1..count step 2) { //step을 사용하면, 필요한 i만 건너뛰며 실행할 수 있다. //1, 3, 5, 7, 9
        //여기서는 1부터 시작하므로, 2칸씩 건너뛰어 홀수일 때만 실행하게 된다.
        sum += i
    }

    sum = 0
    for (i in count downTo 1 step 2) { //10에서 1까지 줄어들면서 step 2 //10, 8, 6, 4, 2
        sum += i
    }

    //Labeled statements
    //continue를 사용하면, loop를 완전히 벗어나지 않고 특정 반복만 건너 뛴다. 현재의 반복을 종료하고 다음 반복을 시작한다. 
    sum = 0 //p.94 
    for (row in 0 until 8) { 
        if (row % 2 == 0) { //짝수일 때 건너 뛴다.
            continue //continue와 break는 while, for에서 모두 사용할 수 있다.
        }

        for (column in 0 until 8) {
            sum += row * column
        }
    }

    sum = 0 //p.95
    rowLoop@ for (row in 0 until 8) { //레이블을 사용할 수 있다. //Swift에서는 rowLoop: 로 레이블을 붙인다.
        columnLoop@ for(column in 0 until 8) {
            if (row == column) {
                continue@rowLoop //continue와 @rowLoop 사이에 공백이 있으면 안 된다. //Swift에서는 continue rowLoop 로 쓴다.
            }
            sum += row * column
        }
    }




    //when expressions
    val number = 10
    when (number) { //when 자체로 흐름 제어를 할 수도 있다. Switch라 생각하면 된다.
        0 -> println("Zero") //0인 경우
        else -> println("Non-zero") //0이 아닌 경우 //switch의 default
        //Java의 경우와 다르게, 일치하는 첫 번째 조건에서만 실행되기 때문에, break를 쓸 필요 없다(Swift switch와 비슷).
    }

    val string = "Dog"
    when (string) { //정수 아닌 다른 유형을 사용할 수도 있다.
        "Cat", "Dog" -> println("Animal is a house pet.")
        else -> println("Animal is not a house pet.")
    }

    //Returning values
    //when에서 각 분기에 따른 다른 값을 반환해 값을 할당해 줄 수 있다.
    val numberName = when (number) {
        2 -> "two"
        4 -> "four"
        6 -> "six"
        8 -> "eight"
        10 -> "ten"
        else -> { //한 줄로 끝나는 게 아니라, 블록을 활용해 여러 줄을 쓸 수도 있다.
            println("Unknown number")
            "Unknown"
        }
    }

    //Advanced when expressions
    //when(다른 언어의 Switch)는 if-else를 간결하게 표현할 수 있다.
    val hourOfDay = 12
    var timeOfDay: String
    timeOfDay = when (hourOfDay) {
        0, 1, 2, 3, 4, 5 -> "Early morning"
        6, 7, 8, 9, 10, 11 -> "Morning"
        12, 13, 14, 15, 16 -> "Afternoon"
        17, 18, 19 -> "Evening"
        20, 21, 22, 23 -> "Late evening"
        else -> "INVALID HOUR!"
    }
    println(timeOfDay)

    //Range를 사용해 표현할 수도 있다.
    timeOfDay = when (hourOfDay) { //Swift에서와는 다르게, Range를 사용할 때 항상 in 키워드를 함께 쓴다고 생각하면 된다.
        in 0..5 -> "Early morning"
        in 6..11 -> "Morning"
        in 12..16 -> "Afternoon"
        in 17..19 -> "Evening"
        in 20..23 -> "Late evening"
        else -> "INVALID HOUR!"
    }

    when {
        number % 2 == 0 -> println("Even")
        else -> println("Odd")
    }

    //부울 연산과 when을 활용할 수도 있다.
    val x = 0
    val y = 0
    val z = 0
    when {
        x == 0 && y == 0 && z == 0 -> println("Origin")
        y == 0 && z == 0 -> println("On the x-axis at x = $x")
        x == 0 && z == 0 -> println("On the y-axis at y = $y")
        x == 0 && y == 0 -> println("On the z-axis at z = $z")
        else -> println("Somewhere in space at x = $x, y = $y, z = $z")
    }

    when { //when에서 표현식이 없는 경우 else분기가 필요하지 않다. 일치하는 조건이 없는 경우 아무것도 실행하지 않는다.
        x == y -> println("Along the y = x line.")
        y == x * x -> println("Along the y = x^2 line.")
    }
}