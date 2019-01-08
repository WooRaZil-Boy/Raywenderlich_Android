//클래스는 객체 지향 프로그래밍의 기본으로 사용된다.
//클래스에는 inheritance, overriding, polymorphism, composition 등의 구성이 있어 객체 지향을 구현하는 데 적합하다.
//이러한 특성은 구조, 계층구조, 메모리 수명 주기 등을 함께 고려해야 한다.




//Sealed classes
//봉인(Sealed) 클래스는 특정 유형의 값이 특정 제한된 하위 유형 집합에서만 올 수 있도록 할 때 유용하다.
//이 클래스를 사용하면, 유형에 엄격한 계층 구조를 정의할 수 있다.
//봉인 클래스 자체는 추상 클래스이며 인스턴스화 할 수 없다.
//봉인 클래스는 열거(enum) 클래스와 매우 유사하다. 열거 클래스는 여러 인스턴스를 가질 수 있고 상태를 가지는 하위 유형을 허용한다.
sealed class Shape { //sealed 클래스는 local에서 선언될 수 없다.
    //열거(enum) 클래스와 달리, 봉인 클래스는 각 유형의 인스턴스를 여러 개 만들 수 있다.
    class Circle(val radius: Int): Shape()
    class Square(val sideLength: Int): Shape()
}




fun main(args: Array<String>) {
    //Introducing inheritance
    data class Grade(val letter: Char, val points: Double, val credits: Double)

//    class Person(var firstName: String, var lastName: String) {
//        fun fullName() = "$firstName $lastName"
//    }
//
//    class Student(var firstName: String, var lastName: String,
//                  var grades: MutableList<Grade> = mutableListOf<Grade>()) {
//        fun recordGrade(grade: Grade) {
//            grades.add(grade)
//        }
//    }
    //Person과 Student에 중복된 코드가 많다. Student is a Person 관계가 성립되기 때문이다.
    //이 경우, 상속으로 동일한 관계를 간결하게 나타낼 수 있다.
    open class Person(var firstName: String, var lastName: String) {
        //open 키워드는 해당 클래스가 상속 받을 수 있도록 열려 있음을 명시해 준다.
        fun fullName() = "$firstName $lastName"
    }

    open class Student(firstName: String, lastName: String,
                  var grades: MutableList<Grade> = mutableListOf<Grade>())
        //생성자에서 해당 매개변수를 똑같이 추가해 줘야 한다. 이후, Person 생성자에 인수로 전달된다.
        //단, 부모 클래스에서 var 키워드로 이미 정의되어 있기 때문에, 자식 클래스의 생성자에서 매개변수에 직접 명시해 줄 필요 없다.
        : Person(firstName, lastName) { //상속하는 클래스를 붙여준다.
        open fun recordGrade(grade: Grade) {
            grades.add(grade)
        }
    }

    //상속으로 자식 클래스는 부모 클래스에 선언된 속성과 메서드를 자동으로 가져온다.
    //중복된 코드를 제거해서 부모 클래스의 모든 속성과 메서드가 있는 자식 클래스 인스턴스를 생성할 수 있다.
    val john = Person(firstName = "Johnny", lastName = "Appleseed")
    val jane = Student(firstName = "Jane", lastName = "Appleseed")

    john.fullName() // Johnny Appleseed
    jane.fullName() // Jane Appleseed

    //자식 클래스는 자식 클래스에 정의된 속성과 메서드 또한 가지고 있다.
    val history = Grade(letter = 'B', points = 9.0, credits = 3.0)
    jane.recordGrade(history)
//    john.recordGrade(history) // john is not a student!

    //하위 클래스의 규칙은 단순하다.
    //• Kotlin은 단일 상속만 지원한다.
    //• Kotlin은 open class만 상속할 수 있다.
    //• 상속의 깊이에는 제한이 없다. 즉, 하위 클래스를 부모 클래스로 하는 또 다른 하위 클래스를 생성할 수 있다.
    open class BandMember(firstName: String,lastName: String) :
            Student(firstName, lastName) {
        open val minimumPracticeTime: Int
            get() { return  2 }
    }

    class OboePlayer(firstName: String, lastName: String):
            BandMember(firstName, lastName) {
        // This is an example of an override, which we’ll cover soon.
        override val minimumPracticeTime: Int = super.minimumPracticeTime * 2
    }
    //OboePlayer → BandMember → Student → Person의 계층 구조를 가진다.

    //Polymorphism
    //위의 관계는 다형성을 보여준다. 다형성은 문맥에 따라 객체를 다르게 처리하는 개념이다.
    //OboePlayer는 OboePlayer 타입이지만, Person에서 파생되었으므로 타입이기도 하다.
    //따라서, OboePlayer 인스턴스를 Person를 사용해야 하는 모든 곳에서 사용할 수 있다.
    fun phonebookName(person: Person): String {
        return "${person.lastName}, ${person.firstName}"
    }

    val person = Person(firstName = "Johnny", lastName = "Appleseed")
    val oboePlayer = OboePlayer(firstName = "Jane", lastName = "Appleseed")

    phonebookName(person)     // Appleseed, Johnny
    phonebookName(oboePlayer) // Appleseed, Jane
    //oboePlayer는 Person이기도 하기에, phonebookName의 파라미터로 쓸 수 있다.
    //함수 내부에서는, Person 인스턴스로 판단하기 때문에, Person에 정의된 속성이나 메서드만 사용 가능하다.
    //다형성을 사용하여, 각 인스턴스를 컨텍스트에 따라 다르게 처리할 수 있다.

    //Runtime hierarchy checks
    var hallMonitor = Student(firstName = "Jill", lastName = "Bananapeel")
    hallMonitor = oboePlayer
    //oboePlayer를 hallMonitor에 할당할 수 있다. 하지만, hallMonitor는 Student 이기 때문에, Student의 속성과 메서드만 사용 가능하다.

    //is 연산자를 사용해 해당 인스턴스가 상속 계층의 일부인지 여부를 확인할 수 있다.
    println(hallMonitor is OboePlayer) // true
    println(hallMonitor !is OboePlayer) // !is는 "not-is"를 의미한다.
    println(hallMonitor is Person) // true //Person은 OboePlayer 보다 상위 계층 구조에 있으므로 true

    //is는 확인을 할 뿐이고, as로 타입을 변환할 수 있다.
    //• as: 안전하지 않은 형 변환. 상위 계층의 유형으로 캐스팅할 때 사용한다.
    //• as?: 안전한 형 변환. 하위 계층 유형으로 캐스팅할 때 사용할 수 있다. 형 변환이 실패하면 null을 반환한다.
//    (oboePlayer as Student).minimumPracticeTime
    //minimumPracticeTime은 OboePlayer의 변수 이므로 캐스팅 이후에는 사용할 수 없다.
    (hallMonitor as? BandMember)?.minimumPracticeTime //4 //캐스팅 할 수 없다면 null을 반환한다.
    //minimumPracticeTime는 BandMember의 속성이므로 캐스팅 이후 사용할 수 있다.

    //as 연산자는 static dispatch 혹은 컴파일 시 특정 작업을 선택해야 할 때 사용할 수 있다.
    //함수의 이름은 같지만, 매개변수의 타입이 다른 두 함수가 있다.
    fun afterClassActivity(student: Student): String {
        return "Goes home!"
    }

    fun afterClassActivity(student: BandMember): String {
        return "Goes to practice!"
    }

    //oboePlayer는 Student와 BandMember의 자식 클래스 이므로 두 함수의 인자로 모두 사용할 수 있다.
    //이 때 각 타입에 맞춰 캐스팅하여 사용할 수 있다.
    afterClassActivity(oboePlayer) // Goes to practice!
    afterClassActivity(oboePlayer as Student) // Goes home!

    //Inheritance, methods and overrides
    //자식 클래스는 부모 클래스에 정의된 모든 속성과 메서드를 자체적으로 재정의할 수 있다(반드시 해야 하는 것은 아니다).
    class StudentAthlete(firstName: String, lastName: String):
            Student(firstName, lastName) {
        val failedClasses = mutableListOf<Grade>()

        override fun recordGrade(grade: Grade) { //recordGrade 메서드를 재정의한다.
            //override를 생략하면 오류가 난다.
            super.recordGrade(grade)

            if (grade.letter == 'F') {
                failedClasses.add(grade)
            }
        }

        val isEligible: Boolean //자체 속성
            get() = failedClasses.size < 3
    }

    val math = Grade(letter = 'B', points = 9.0, credits = 3.0)
    val science = Grade(letter = 'F', points = 9.0, credits = 3.0)
    val physics = Grade(letter = 'F', points = 9.0, credits = 3.0)
    val chemistry = Grade(letter = 'F', points = 9.0, credits = 3.0)

    val dom = StudentAthlete(firstName = "Dom", lastName = "Grady")
    dom.recordGrade(math)
    dom.recordGrade(science)
    dom.recordGrade(physics)
    println(dom.isEligible) // > true
    dom.recordGrade(chemistry)
    println(dom.isEligible) // > false

    //Introducing super
    //super 키워드는 this와 비슷하지만, 해당 클래스의 부모 클래스에서 메서드를 호출한다.
    //항상 필요한 것은 아니지만, Kotlin의 메서드를 재정의할 때, super를 사용하는 것은 중복되는 코드를 제거하는 데 중요하다.

    //When to call super
    class StudentAthlete2(firstName: String, lastName: String): Student(firstName, lastName) {
        var failedClasses = mutableListOf<Grade>()

        override fun recordGrade(grade: Grade) {
            //위에서와 달리, 재정의된 recordGrade() 메서드를 grade가 기록될 때마다 failedClasses를 다시 계산한다.
            var newFailedClasses = mutableListOf<Grade>()
            for (grade in grades) {
                if (grade.letter == 'F') {
                    newFailedClasses.add(grade)
                }
            }
            failedClasses = newFailedClasses

            super.recordGrade(grade)
            //super를 마지막에 호출하기 때문에 grade.letter가 F인 경우에는 제대로 failedClasses가 업데이트 되지 않는다.
            //반드시는 아니지만 일반적으로 override할 때, super 메서드를 먼저 호출하는 것이 좋다.
            //그래야 하위 클래스에서의 side effect를 막을 수 있으며, 하위 클래스는 상위 클래스의 구현 세부 정보를 알 필요가 없다.
        }

        val isEligible: Boolean
            get() = failedClasses.size < 3
    }

    //Preventing inheritance
    //특정 클래스의 하위 클래스 상속을 허용하지 않으려면 open 키워드를 빼면 된다.
    //이는 Java나 Swift 등의 다른 언어에서 상속을 해야 하는 경우 특정 키워드를 추가해 줘야 하는 것과 반대이다.
    //Kotlin에서는 open 키워드를 추가해야지만, 상속이 가능하다.
    class FinalStudent(firstName: String, lastName: String):
            Person(firstName, lastName)

//    class FinalStudentAthlete(firstName: String, lastName: String)
//        : FinalStudent(firstName, lastName) // Build error!

    open class AnotherStudent(firstName: String, lastName: String)
        : Person(firstName, lastName) {
        open fun recordGrade(grade: Grade) {}
        //마찬가지로, open 키워드를 사용한 매서드만 override가 가능하다.
        fun recordTardy() {}
    }

    class AnotherStudentAthlete(firstName: String, lastName: String)
        : AnotherStudent(firstName, lastName) {
        override fun recordGrade(grade: Grade) {} // OK
//        override fun recordTardy() {} // Build error! recordTardy is final
    }
    //이렇게 하면, 컴파일러에게 해당 클래스나 메서드가 상속될 수 없음을 알려, 서브 클래스나 재정의 메서드를 찾을 필요가 없으므로 성능이 향상된다.

    //Abstract classes
    //특정 상황에서만 클래스가 인스턴스화 되지 못하도록 할 수 있다. 이렇게 하면, 모든 하위 클래스에 공통적인 속성과 메서드를 정의할 수 있다.
    //기본, 상위 클래스가 아닌 하위 클래스만 인스턴스를 생성할 수 있다. 이러한 부모 클래스를 추상 클래스라 한다.
    //abstract 키워드로 선언된 클래스는 기본적으로 상속될 수 있다(open). 추상 클래스에서는 본문이 없는 추상 메서드를 선언할 수도 있다.
    //추상 메서도는 서브 클래스에서 재정의되어야 한다.
    abstract class Mammal(val birthDate: String) { //추상 클래스
        //추상 클래스는 interface와 밀접한 관련이 있다.
        abstract fun consumeFood() //추상 메서드 //하위 클래스에서 override되어야 한다.
    }

    class Human(birthDate: String): Mammal(birthDate) {
        override fun consumeFood() {
            // ...
        }

        fun createBirthCertificate() {
            // ...
        }
    }

    val human = Human("1/1/2000")
//    val mammal = Mammal("1/1/2000") // Error: 추상 클래스는 인스턴스화될 수 없다.




    //Sealed classes
    //봉인(Sealed) 클래스는 특정 유형의 값이 특정 제한된 하위 유형 집합에서만 올 수 있도록 할 때 유용하다.
    //이 클래스를 사용하면, 유형에 엄격한 계층 구조를 정의할 수 있다.
    //봉인 클래스 자체는 추상 클래스이며 인스턴스화 할 수 없다.
    //봉인 클래스는 열거(enum) 클래스와 매우 유사하다. 열거 클래스는 여러 인스턴스를 가질 수 있고 상태를 가지는 하위 유형을 허용한다.
    val circle1 = Shape.Circle(4)
    val circle2 = Shape.Circle(2)
    val square1 = Shape.Square(4)
    val square2 = Shape.Square(2)

    fun size(shape: Shape): Int {
        return when (shape) { //when을 사용하여 다른 하위 유형을 구별할 수 있다.
            is Shape.Circle -> shape.radius
            is Shape.Square -> shape.sideLength
        }
    }

    println(size(circle1)) // radius of 4
    println(size(square2)) // sideLength of 2




    //Secondary constructors
    //생성자 키워드는 기본 생성자에서는 생략이 가능하다.
    //기본 생성자 외에 custom constructor가 필요할 때는 constructor 키워드를 사용해야 한다.
    open class Shape {
        constructor(size: Int) { //기본 생성자 외에 custom constructor가 필요할 때는 constructor 키워드를 사용해야 한다.
            // ...
        }

        constructor(size: Int, color: String) : this(size) { //this 키워드를 사용하여 다양한 생성자를 호출할 수 있다.
            // ...
        }
    }

    class Circle : Shape {
        constructor(size: Int) : super(size) {
            // ...
        }

        constructor(size: Int, color: String) : super(size, color) { //super로 상위 생성자를 호출할 수 있다.
            // ...
        }
    }




    //Nested and inner classes
    //두 클래스가 서로 밀접하게 관련되어 있을 때 다른 클래스의 범위 내에서 하나의 클래스를 정의할 수 있다.
    //이렇게 하면 한 클래스가 다른 클래스 내에서 name space로 나누어 진다.
    class Car(val carName: String) {
        inner class Engine(val engineName: String) { //중첩된 클래스가 다른 멤버에 액세스할 수 있게 하려면 inner 키워드를 사용해야 한다.
            //Engine를 사용하려면, _Car.Engine으로 참조해야 한다.
            //클래스가 다른 클래스 안에 중첩되어 있으면, 기본적으로 클래스의 다른 멤버에 액세스 할 수 없다.
            //inner로 내부 클래스를 선언해 줘야, 상위 클래스(여기서는 Car)의 다른 멤버에 액세스할 수 있다.
            override fun toString(): String {
                return "$engineName in a $carName" //inner 키워드가 없으면, carName에 접근할 수 없어 오류가 난다.
            }
        }
    }

    val mazda = Car("mazda")
    val mazdaEngine = mazda.Engine("rotary")
    println(mazdaEngine) // > rotary engine in a mazda




    //Visibility modifiers
    //open 키워드는 클래스 계층에서 재정의 여부를 결정하지만, 가시성(visibility) 수정자는 클래스의 접근을 제한한다.
    //• public : 하위 클래스, 다른 파일, 다른 프로젝트 모듈 등 모든 곳에서 액세스할 수 있다. default 값이다.
    //• private : 클래스의 경우에는 동일한 클래스 내에서만 액세스 가능. 함수 등 클래스 이외의 경우에는 같은 파일 내에서만 액세스할 수 있다.
    //• protected : 클래스 계층 구조의 하위 클래스에서만 액세스 가능하다.
    //• internal : 동일한 모듈 내에서만 액세스 가능하다.
    //일반적으로 최대한 제한해 두는 것이 좋다. 그래야 클래스 간의 책임이 명확하게 유지되고, 상태가 변하지 않는다.
    data class Privilege(val id: Int, val name: String)

    open class User(val username: String, private val id: String, protected var age: Int)
    //username은 명시된 visibility modifier가 없으므로 default인 public이 된다.
    //id는 private 이므로, 해당 User 클래스 내에서만 참조할 수 있다.
    //age는 protected이기 때문에 하위 클래스에서도 참조할 수 있다.

    class PrivilegedUser(username: String, id: String, age: Int): User(username, id, age) {
        private val privileges = mutableListOf<Privilege>()

        fun addPrivilege(privilege: Privilege) {
            privileges.add(privilege)
        }

        fun hasPrivilege(id: Int): Boolean {
            return privileges.map { it.id }.contains(id)
        }

        fun about(): String {
//            return "$username, $id" // Error: id is private
            return "$username, $age" // OK: age is protected
        }
    }

    val privilegedUser = PrivilegedUser(username = "sashinka", id = "1234", age = 21)
    val privilege = Privilege(1, "invisibility")

    privilegedUser.addPrivilege(privilege)
    println(privilegedUser.about()) // sashinka, 21




    //When and why to subclass
    data class Sport(val name: String)

    class Student2(firstName: String, lastName: String): Person(firstName, lastName) {
        var grades = mutableListOf<Grade>()
        var sports = mutableListOf<Sport>()
    }
    //Student와 StudentAthlete의 관계에서, StudentAthlete의 모든 특성을 Student에 넣을 수도 있다.
    //StudentAthlete가 아닌 일반 Student는 단순히 빈 sports 배열을 가지게 할 수 있다.

    //Single responsibility
    //그러나 소프트웨어 개발에서 단일 책임 원칙으로 서브 클래싱 여부를 판단해야 한다.
    //단일 책임 원칙이란 모든 클래스는 하나의 책임만 가지며, 그 책임을 완전히 캡슐화해야 한다는 것을 뜻한다.
    //단일 책임 원칙에 따르면, sports는 StudentAthlete 에만 의미있는 속성이므로
    //StudentAthlete 하위 클래스를 만드는 것이 더 나은 모델화 방법이다.

    //Strong types
    //서브 클래싱은 추가적인 타입을 만든다.
    //Kotlin의 type system을 사용하면, 일반 Student가 아닌 StudentAthlete를 기반으로 속성이나 메서드를 선언해 줄 수 있다.
    class Team {
        var players = mutableListOf<StudentAthlete>()
        //StudentAthlete이므로, Student 객체로 추가할 수 없다.

        val isEligible: Boolean
            get() {
                for (player in players) {
                    if (!player.isEligible) {
                        return false
                    }
                }
                return true
            }
    }

    //Shared base classes
    //상호 배타적인 동작을 공유하는 기본 클래스를 여러 번 서브 클래싱 할 수 있다.
    open class Button { //누를 수 있는 버튼
        fun press() {
        }
    }

    class Image //버튼에 그려질 이미지

    class ImageButton(val image: Image): Button() //이미지를 보여주는 버튼

    class TextButton(val text: String): Button() //텍스트를 보여주는 버튼
    //이미지 버튼과 텍스트 버튼은 press()라는 메서드를 공통으로 가지고 있지만, 매커니즘은 고유하게 구현해야 한다.

    //Extensibility
    //코드의 동작을 확장하는 경우 하위 클래스를 만들어야 하는 경우도 있다.

    //Identity
    //클래스 계층 구조에서 어떻게 모델링 하는 지 이해하는 것이 중요하다.
    //타입 간에 어떤 인스턴스가 할 수 있는 행동을 공유하는 것이 목적이라면, 서브 클래싱보다 인터페이스가 더 나을 수 있다.
}

