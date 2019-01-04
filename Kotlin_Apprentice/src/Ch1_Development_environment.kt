fun main(args: Array<String>) { //main 이라는 단일 Kotlin 함수 작성
    println("Hello, Kotlin!") //println 으로 출력
}
//코드 실행하는 가장 쉬운 방법은 왼쪽 작은 녹색 상자를 클릭해서 run 하는 것이다.
//Kotlin 컴파일러가 코드를 구문 분석하고, 바이트 코드로 변환한 뒤, local JVM에서 코드를 실행한다.
//코드를 한 번 실행한 이후에는 도구 모음의 활성 프로젝트 구성이 생기고, 도구 모음의 실행 단추로 코그를 실행할 수 있다.




//Java and the JDK
//Java는 "Write Once, Everywhere Run"의 크로스 플랫폼 언어로 만들어졌다.
//각 플랫폼에서 원시 기계 코드로 컴파일하는 대신 Java는 바이트 코드라는 형식으로 컴파일 된다.
//바이트 코드는 Java Virtual Machine 위에서 실행된다. JVM은 실제 머신 위에 있는 레이어로 생각할 수 있다.
//가상 시스템에서 바이트 코드로 실행하면 여러 유형의 컴퓨터 시스템에서 Java 코드 및 응용 프로그램을 공유할 수 있다.
//Kotlin은 Java와 100%상호 호환이 가능하도록 만들어졌다.
//Kotlin 코드가 Kotlin 컴파일러로 Java 호환 바이트 코드로 변환되므로 Kotlin 코드가 JVM에서 실행될 수 있다.
//JRE는 Java 응용 프로그램만 실행할 수 있고, 새 응용 프로그램을 빌드하는 도구는 포함하지 않는다.
//따라서 JRE 뿐 아니라 JDK도 다운로드 해 설치해야 한다.


//Kotlin JVM projects
//Kotlin 코드와 main() 함수를 포함한다(필요한 경우에는 main() 함수 외부에 코드를 추가해야하는 경우도 있다).

//Gradle projects
//Gradle은 external dependency를 적용하는 라이브러리이다.
//Gradle은 Android Studio에서도 사용된다.