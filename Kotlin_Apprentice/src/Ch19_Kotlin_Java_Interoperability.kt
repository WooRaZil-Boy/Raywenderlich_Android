fun main(args: Array<String>) {
    //Kotlin은 JVM에서 실행되도록 설계된 언어이다. 즉, 기본적으로 Kotlin 컴파일러의 ouput은 Java가 실행되는 곳이면 어디에서나 실행할 수 있는 Byte code이다.
    //JVM에서 코드를 실행할 수 있다는 것은 단일 코드 베이스에 Java와 Kotlin을 동시에 쓰거나, Java로 작성된 기존 라이브러리로 작업하는 것도 가능하다는 의미이다.
    //Kotlin은 Java 코드와 상호 작용을 보다 쉽게 할 수 있도록 설계된 여러 기능이 있다.
    //Kotlin에서 Java 클래스를 사용할 수 있고, "Kotliny"한 스타일을 유지하면서 Java 코드에서 Kotlin 클래스 및 기타 코드를 사용할 수도 있다.




    //Mixing Java and Kotlin code
    //Kotlin은 Java와 상호 작용할 수 있다.

    //Getters and setters
    val user = Ch19_User() //Java 클래스임에도 Kotlin에서 클래스 생성하는 것과 구문이 동일하다.
    //Java 클래스라고 해서 new 키워드를 사용해 인스턴스를 생성할 필요 없다.
    user.firstName = "Bob"
    user.lastName = "Barker"
//    user.city = "Los Angeles"
//    user.country = "United States"
    //Java 메서드를 사용한다고 setFirstName() 등 으로 호출할 필요 없다. Kotlin이 적절하게 매핑해 놓았다.
    //Java 에는 Nullable이 없기 때문에 자료형은 String!이 된다. Nullable이 되도록 코드를 추가해 줄 수도 있다.

    println("User info:\n$user")
    //Java의 User 클래스에서 toString() 부분에 제대로 출력된다.

    //Adding a Kotlin class as a Java property
    //Kotlin에서 새 클래스를 만든 다음, Java의 클래스에서 직접 사용할 수도 있다.
    val billingAddress = Ch19_Address("123 Fake Street",
            "4th floor",
            "Los Angeles",
            "CA",
            "90291",
            AddressType.Billing)
    println("Billing Address:\n$billingAddress\n")

    user.addOrUpdateAddress(billingAddress)

    val shippingAddress = Ch19_Address("987 Unreal Drive",
            null,
            "Burbank",
            "CA",
            "91523",
            AddressType.Shipping) //AddressType.Billing 등으로 바꾸었을 때 유효성 검사가 제대로 되야 한다.

    user.addOrUpdateAddress(shippingAddress)
    println("User info after adding addresses:\n$user")

    //Free functions
    println("Shipping Label:")
    printLabelFor(user, AddressType.Shipping)




    //Java nullability annotations
    //Java8 에서는 null을 안전하게 사용하기 위해 Optional을 도입했다. 하지만, Kotlin과 Java 사이에 Nullable을 처리할 때 주석을 사용해야 한다.
    //이러한 주석을 사용하면 주어진 객체가 nullable인지 여부에 상관없이 nullability에 first-class support를 가진 JVM language 를 나타낼 수 있다.
    val anotherUser = Ch19_User() //User 클래스는 Java에서 작성되었다.

//    anotherUser.addresses = null
    //하지만 명시적으로 null을 대입하면 오류가 발생한다. 컴파일러는 nullable을 오류가 있거나 발생할 수 있음을 가정한다.
    //따로 nullable을 명시해 주지 않았으므로 anotherUser.addresses.count()를 입력하면, addresses 가 null이 아닐 것이라 판단한다.

    println("Another User has ${anotherUser.addresses.count()} addresses")
    //User.java에서 address 변수를 빈 ArrayList로 초기화 했으므로 null이 아니어야 한다.

    println("Another User first name: ${anotherUser.firstName ?: "(not set)"}")
    //기본값을 설정하지 않았으므로 null이 된다.





    //"Static" values and functions from Kotlin
    println("Sample First Line: ${Ch19_Address.sampleFirstLine}")
    println("Sample Canadian Address:\n${Ch19_Address.canadianSample(AddressType.Billing)}")
}
