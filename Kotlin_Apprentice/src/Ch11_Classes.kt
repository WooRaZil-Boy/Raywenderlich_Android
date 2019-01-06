import java.util.* //Random을 사용하기 위해 import

//클래스는 객체 지향 프로그래밍의 초석이며 데이터(프로퍼티)와 동작(메서드)를 가지고 있다.



//Creating classes
class Person(var firstName: String, var lastName: String) { //클래스 뒤에 바로 기본 생성자를 추가한다.
    //중괄호 사이에 있는 모든 것이 클래스 멤버이다.
    val fullName //get()을 구현해 Swift의 computed property처럼 사용할 수 있다.
        get() = "$firstName $lastName"
}




//Reference types
class SimplePerson(val name: String)

//Methods and mutability
class Grade(val letter: String, val points: Double, val credits: Double)

class Student(
        val firstName: String,
        val lastName: String,
        val grades: MutableList<Grade> = mutableListOf(), //default 값으로 빈 List
        var credits: Double = 0.0) { //생성자

    fun recordGrade(grade: Grade) { //메서드
        grades.add(grade) //MutableList는 val로 선언되었더라 요소를 추가해 줄 수 있다.
        //해당 인스턴스가 val로 참조되는 것과 상관없다.
        credits += grade.credits
        //var로 정의되었기 때문에 변경 가능하다. 해당 인스턴스가 val로 참조되는 것과 상관없다.
    }
}




fun main(args: Array<String>) {
    // Creating classes
    //클래스 이름을 사용하고 생성자에 인수를 전달하여 인스턴스를 생성할 수 있다.
    val john = Person(firstName = "Johnny", lastName = "Appleseed")
    //val john = Person("Johnny", "Appleseed") //레이블 없이 이렇게 생성할 수도 있다(순서는 맞춰 줘야 한다).
    println(john.fullName) // > Johnny Appleseed




    //Reference types
    //Kotlin에서 클래스 인스턴스는 변경 가능한 객체이다. 클래스는 참조 유형으로 클래스 타입의 변수는 실제 인스턴스를 저장하지 않고, 메모리 위치에 대한 참조를 저장한다.
    var var1 = SimplePerson(name = "John")
    var var2 = var1
    //클래스는 참조 유형이므로, var1과 var2는 동일한 메모리의 위치를 참조한다.

    //The heap vs. the stack
    //클래스와 같은 참조 유형을 생성하면 실제 인스턴스는 힙이라는 메모리 영역에 저장된다.
    //그 외의 대부분은 스택이라는 메모리 영역에 저장된다. 힙과 스택은 프로그램 실행에 필수적인 역할을 한다.
    //• 시스템은 스택을 사용해 실행 스레드에 아무것도 저장하지 않는다. 이는 CPU가 엄격하게 관리하고 최적화한다.
    //  함수가 변수를 생성하면 스택은 변수를 저장한 다음 함수가 종료될 때 변수를 삭제한다. 스택은 잘 구성되어 있기 때문에 효율적이고 빠르다.
    //• 시스템은 힙을 사용하여 참조 유형의 인스턴스를 저장한다. 힙은 일반적으로 시스템이 메모리 블록을 요청하고 동적으로 할당할 수 있는 메모리 풀이다.
    //  힙의 라이프 사이클은 유연하고 역동적이다. 스택처럼 자동으로 데이터를 삭제하지 않기 때문에 추가 작업이 필요하다. 스택에 비해 생성 및 제거 속도가 느리다.

    //클래스 인스턴스를 생성할 때 코드는 인스턴스 자체를 저장하기 위해 힙의 메모리 블록을 요청한다. 이후 해당 메모리 주소를 스택의 명명된 변수에 저장한다.

    //Working with references
    //클래스는 참조 유형이므로 변수에 할당하면 인스턴스를 복사하지 않고 참조만 복사된다.
    var homeOwner = john
    john.firstName = "John"

    println(john.firstName)      // > John
    println(homeOwner.firstName) // > John
    //클래스는 참조 유형이므로 john 변수의 이름을 바꿨지만, homeOwner의 이름도 변경된다(동일한 객체를 참조하고 있다).

    //Object identity
    //Kotlin에서는 === 연산자를 사용해 객체의 id를 비교할 수 있다.
    john === homeOwner // true
    //== 연산자가 두 값이 같은지 확인하는 것 처럼 === 연산자는 두 개의 메모리 주소를 비교해 참조 값이 동일한지 반환한다.
    //즉, 힙의 동일한 데이터 블록을 가리키는 지 판단한다.

    val impostorJohn = Person(firstName = "John", lastName = "Appleseed")
    john === homeOwner // true
    john === impostorJohn // false
    impostorJohn === homeOwner // false

    //여기서 변수를 재 할당하면, 참조하는 인스턴스가 변경된다.
    homeOwner = impostorJohn
    john === homeOwner // false

    homeOwner = john
    john === homeOwner // true

    //== 연산자로 식별할 수 없는 경우 유용하게 사용할 수 있다.
    var imposters = (0..100).map { //List<Person> 타입이 된다. //101개의 Person 객체를 만들어 List에 담는다.
        Person(firstName = "John", lastName = "Appleseed")
    }

    imposters.map {
        it.firstName == "John" && it.lastName == "Appleseed"
    }.contains(true) // true
    //이 경우 == 연산자는 각 객체들을 식별할 수 없다. === 연산자로 식별해야 한다.

    println(imposters.contains(john)) // > false //메모리 주소로 판단하기 때문에 false가 된다.

    val mutableImposters = mutableListOf<Person>()
    mutableImposters.addAll(imposters) //추가
    mutableImposters.contains(john) // false //메모리 주소로 판단하기 때문에 false가 된다.
    mutableImposters.add(Random().nextInt(5), john) //주어진 인덱스(랜덤)에 john 객체를 삽

    println(mutableImposters.contains(john)) // > true

    val indexOfJohn = mutableImposters.indexOf(john) //메모리 주소로 john의 index를 가져온다.
    if (indexOfJohn != -1) { //-1인 경우는 존재하지 않는 경우
        mutableImposters[indexOfJohn].lastName = "Bananapeel"
    }
    println(john.fullName) // > John Bananapeel

    //Methods and mutability
    val jane = Student(firstName = "Jane", lastName = "Appleseed")
    val history = Grade(letter = "B", points = 9.0, credits = 3.0)
    var math = Grade(letter = "A", points = 16.0, credits = 4.0)

    jane.recordGrade(history)
    jane.recordGrade(math)
    //jane 인스턴스가 val이라도 메서드를 이용해 프로퍼티들을 변경할 수 있다.

    //Mutability and constants
    //상수를 정의할 때 상수의 값은 변경할 수 없다. 참조 유형에서의 이 값은 메모리 주소를 의미한다.
    //따라서 인스턴스를 상수로 받은 경우에는 해당 인스턴스의 프로퍼티를 변경 불가능한 것이 아니라, 인스턴스 자체의 주소를 변경 불가능한 것이다.

//    jane = Student(firstName = "John", lastName = "Appleseed") // Error: jane is a `val` constant

    //jane을 var로 선언했다면, 다른 인스턴스를 할당할 수 있다.
    var janeVar = Student(firstName = "Jane", lastName = "Appleseed")
    janeVar = Student(firstName = "John", lastName = "Appleseed")
    //이 경우에는 이전 객체에 참조하던 메모리는 해제된다.

    //클래스의 프로퍼티가 값 타입인 경우 상수로 선언하면, 값을 변경할 수 없지만
    //해당 프로퍼티가 참조 타입인 경우에는 마찬가지로 메모리 주소를 변경 불가능한 것으로 값은 변경할 수 있다.




    //Understanding state and side effects
    jane.credits // 7

    math = Grade(letter = "A", points = 20.0, credits = 5.0)
    jane.recordGrade(math)
    jane.credits // 12, not 8!
    //클래스 인스턴스에서 공유되는 참조에 대한 side effect에 주의해야 한다.




    //Data classes
    //두 객체가 동일한지 비교하는 하고, 쉡게 데이터를 출력할 수 있는 메서드를 추가한 Student 클래스 정
    class Student(var firstName: String, var lastName: String, var id: Int) {
        override fun hashCode(): Int {
            val prime = 31
            var result = 1

            result = prime * result + firstName.hashCode()
            result = prime * result + id
            result = prime * result + lastName.hashCode()

            return result
        }

        override fun equals(other: Any?): Boolean { //== 연산의 Boolean 빈횐 방식을 정의한다.
            if (this === other)
                return true

            if (other == null)
                return false

            if (javaClass != other.javaClass)
                return false

            val obj = other as Student?

            if (firstName != obj?.firstName)
                return false

            if (id != obj.id)
                return false

            if (lastName != obj.lastName)
                return false

            return true
        }

        override fun toString(): String { //toString을 구현하면, println 함수에서 해당 객체 출력시에 보여지는 문자열을 정의할 수 있다.
            return "Student (firstName=$firstName, lastName=$lastName, id=$id)"
        }

        fun copy(firstName: String = this.firstName, lastName: String = this.lastName, id: Int = this.id)
                = Student(firstName, lastName, id) //함수가 한줄로 끝날 때는 = 로 표현할 수 있다.
        //값을 복사한다. 메모리 주소까지 복사하진 않는다.
    }

    val albert = Student(firstName = "Albert", lastName = "Einstein", id = 1)
    val richard = Student(firstName = "Richard", lastName = "Feynman", id = 2)
    val albertCopy = albert.copy()

    println(albert)  // > Student (firstName=Albert, lastName=Einstein, id=1)
    println(richard) // > Student (firstName=Richard, lastName=Feynman, id=2)
    println(albert == richard) // > false
    println(albert == albertCopy) // > true
    println(albert === albertCopy) // > false

    //위와 같은 클래스는 매우 일반적이고 자주 사용되므로, Kotlin에서는 data class를 따로 제공한다.
    data class StudentData(var firstName: String, var lastName: String, var id: Int)
    //class 키워드 앞에 data를 추가해 주기만 하면 data class가 정의된다. 이외에는 일반 클래스의 정의와 같다.
    //즉 위에 새로 정의된 Student 클래스를 한 줄로 동일하게 생성할 수 있다.

    val marie = StudentData("Marie", "Curie", id = 1)
    val emmy = StudentData("Emmy", "Noether", id = 2)
    val marieCopy = marie.copy()

    println(marie) // > StudentData(firstName=Marie, lastName=Curie, id=1)
    println(emmy)  // > StudentData(firstName=Emmy, lastName=Noether, id=2)
    println(marie == emmy) // > false
    println(marie == marieCopy) // > true
    println(marie === marieCopy) // > false

    //Destructuring declarations
    val (firstName, lastName, id) = marie
    //데이터 클래스 내부의 데이터를 추출해 올 수 있다. 각 변수에 속성 값이 할당 된다.

    println(firstName) // > Marie
    println(lastName)  // > Curie
    println(id)        // > 1
    //for loop로 맵에 전달하는 등에 유용하게 사용할 수 있다.
}