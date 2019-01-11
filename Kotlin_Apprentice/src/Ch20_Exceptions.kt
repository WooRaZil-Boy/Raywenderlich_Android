//What is an exception?
//예외는 프로그램 실행 중에 문제가 있음을 알리는 이벤트이다. Java의 Exception(모든 exception의 슈퍼 클래스) 으로 표현된다.
//Exception 은 crash를 일으키기에, 실행 중에 catch 되거나 혹은 아예 처음부터 발생되지 않도록 관리해야 한다.
//  ex. NullPointerException, IOException ..
//Error는 crash를 일으키지 않아 처리해 줄 필요는 없지만, 프로그램 실행에 심각한 문제를 일으킬 수 있으므로 해결해줘야 한다.
//  ex. 메모리 누수
//Exception과 Error 모두 Throwable을 확장한다. 따라서 JVM에서 thrown 되거나, throw 키워드를 사용해 코드에서 수동으로 문제를 알릴 수 있다.
//모든 Throwable 객체는 예외의 원인이 되는 Throwable 인스턴스와 stacktrace를 포함할 수 있다.

//fun main(args: Array<String>) {
//    someFunction()
//}
//
//fun someFunction() {
//    anotherFunction()
//}
//
//fun anotherFunction() {
//    oneMoreFunction()
//}
//
//fun oneMoreFunction() { //예외를 throw 한다.
//    throw Exception("Some exception")
//}

//프로그램은 함수 호출 체인으로 표현될 수 있다. p.324 //예외가 발생하면 이전 함수로 롤백하여 호출된 곳으로 보내 handler를 탐색한다.
//프로그램의 진입점인 main()에까지 거슬러 올라가도 해당 예외를 처리하는 handler가 없다면, 프로그램이 crash 된다.
//crash 되면서, 콘솔에는 예외의 stacktrace가 출력된다.
//stacktrace는 예외에 대한 자세한 설명이다. 예외와 관련된 함수 호출 목록, 호출 순서, 호출된 파일 행 번호를 출력한다.
//stacktrace를 사용하면, 예외가 발생한 정확한 위치를 찾을 수 있다. 프로그램이 crash 되지 않게 하려면 예외를 처리해야 한다.




fun main(args: Array<String>) {
    val spaceCraft = SpaceCraft()
    SpacePort.investigateSpace(spaceCraft)
}




//Difference between Java and Kotlin exceptions

//Checked exceptions
//Java에는 checked와 unchecked 두 가지 예외가 있다. checked 예외는 처리되거나, throws 키워드로 메서드 뒤에 선언된다.
//검사되지 않은 예외는 무시할 수 있지만 처리하지 않으면 프로그램이 종료된다.
//반대로, Kotlin에서 모든 예외는 unchecked로, 강제로 처리하거나 선언해서는 안 된다. 예외가 발생하면 프로그램이 종료된다.

//try as an expression
//Kotlin에서 try-catch 구조는 표현이다. 즉, try-catch 블록에서 값을 가져올 수 있으면 변수와 동일시 할 수 있다.

//val date: Date = try {
//        Date(userInput) //userInput을 parse 한다.
//    } catch (exception: IllegalArgumentException) {
//        Date() //userInput에 오류가 있다면 Date()를 생성해(현재 시간) 사용한다.
//    }
//}
