//Kotlin에서의 Object는 Java나 Swift에서의 개념과는 다소 다른 새로운 키워드이다.
//Kotlin은 OOP를 지원하는데, 하나의 인스턴스만 생성할 수 있는 Custom 유형을 나타내기 위해 object를 사용한다.
//객체를 사용해, 익명 객체(anonymous object)를 생성할 수 있는데 이 때마다 여러 인스턴스가 생성되어 혼란스러워 질 수 있다.
//object가 클래스스 인스턴스인지, 익명 객체로 생성된 단일 인스턴스인지 엔티티를 결정할 때 컨텍스트에 의존해야한다.
//object 키워드를 사용하면, 일반적인 패턴을 쉽게 구현할 수 있다.




//Singletons
//싱글톤 패턴은 단일 인스턴스를 갖도록 제한하는 경우 사용된다.
//싱글톤은 전역 상태이기 때문에 다른 스레드에서 싱글톤에 액세스할 때는 주의해야 하며 필요한 경우에만 사용하는 것이 좋다.

//Named objects
//Kotlin의 object 키워드를 사용하면, 단일 인스턴스만 갖는 유형을 정의해 줄 수 있다.
//object로 정의된 타입은 생성자를 가질 수 없다. 객체의 인스턴스가 하나뿐이므로 다른 인스턴스를 생성하기 위한 생성자가 필요 없기 때문이다.
//싱글톤 패턴을 이해하기 위해, object 키워드로 작성되어 컴파일된 Kotlin 코드가 Java로 어떻게 번역되는지 살펴보는 것이 좋다.
//IntelliJ IDEA에서는 Kotlin 바이트 코드를 Java로 디 컴파일할 수 있다.




//Getting started
//Tools ▸ Kotlin ▸ Show Kotlin Bytecode 를 선택하면, Kotlin Bytecode를 살펴볼 수 있다.
//Kotlin Bytecode 창에서 Decompile 버튼을 누르면 디컴파일된 Java가 새 편집 창에 열린다.

//Singleton use cases
//싱글톤 패턴 사용의 예시는 데이터 집합에 registry이다.
data class _Student(val id: Int, val firstName: String, val lastName: String) {
    var fullName = "$lastName, $firstName"
}

object StudentRegistry { //싱글톤
    val allStudents = mutableListOf<_Student>()

    fun addStudent(student: _Student) {
        allStudents.add(student)
    }

    fun removeStudent(student: _Student) {
        allStudents.remove(student)
    }

    fun listAllStudents() {
        allStudents.forEach {
            println(it.fullName) //_Student가 data class 이므로 이미 toString()을 구현해 뒀다.
        }
    }
}
//object를 사용해 mutable list에 학생 목록을 유지 관리하는 registry를 만들 수 있으며, registry에서 학생을 추가 제거하고 이름을 출력할 수 있다.

//또 다른 사용 예는 object를 사용하여 여러 위치에서 참조해야 하는 상수 및 메서드에 대한 name space를 제공해 주는 것이다.
object JsonKeys {
    const val JSON_KEY_ID = "id"
    const val JSON_KEY_FIRSTNAME = "first_name"
    const val JSON_KEY_LASTNAME = "last_name"
}
//이 경우, 싱글톤에 상수 변수를 사용해 충돌 가능성을 줄일 수 있다.

//Comparison to classes
//object가 생성자는 사용할 수 없지만, class와 많은 공통점이 있다.
//• object는 속성 및 메서드를 가질 수 있다.
//• object의 속성은 선언 전이나, 초기화 블록에서 사용하기 전에 초기화해야 한다.
//• object는 class를 상속하고 interface를 구현할 수 있다.




//Using static members
//Kotlin에는 Java와 Swift에서와 달리 static 키워드가 없다. static은 다른 언어에서 모든 클래스 인스턴스에 공통적인 멤버를 나타내기 위해 사용한다.
//Kotlin은 companion object을 대신 사용한다.

//Creating companion objects
class Scientist private constructor(
        val id: Int,
        val firstName: String,
        val lastName: String) { //생성자 //주 생성자를 private로 만든다. 팩토리 메서드로만 객체를 생성할 수 있다.
    companion object Factory { //static //object가 싱글톤 패턴을 생성하는 키워드이므로, 이 안에 정적 변수와 정적 메서드를 정의해 주면 된다.
        //companion object 뒤에 Custom한 이름(Factory)을 사용할 수도 있다.
        var currentId = 0 //새 Scientist가 생성되면 +1 되어 새 id값을 만드는 데 사용한다.

        fun newScientist(firstName: String, lastName: String): Scientist { //Factory 패턴
            //주 생성자가 private로, 이 팩토리 메서드에서만 인스턴스를 생성 가능하다.
            //따라서 currentId의 값이 유효하게 증가되는 것이 보장된다.
            currentId += 1
            return Scientist(currentId, firstName, lastName)
        }
    }

    var fullName = "$firstName $lastName"
}

object ScientistRepository { //Scientist data repository //싱글톤
    val allScientists = mutableListOf<Scientist>()

    fun addScientist(student: Scientist) {
        allScientists.add(student)
    }

    fun removeScientist(student: Scientist) {
        allScientists.remove(student)
    }

    fun listAllScientists() {
        allScientists.forEach {
            println("${it.id}: ${it.fullName}")
            //_Student와 달리 Scientist가 일반 class 이므로 toString()을 따로 구현하지 않았다.
            //따라서 직접 출력 방법을 정의해 준다.
        }
    }
}




//Using anonymous objects
//Anonymous class는 Java에서 하위 클래스 없이 기존 클래스의 동작을 재정의하고 인터페이스를 구현하는 데 사용한다.
//컴파일러는 이름을 지정할 필요 없는 단일 익명 인스턴스를 생성한다.
//Kotlin에서는 object를 사용하여 이를 구현할 수 있다.
interface Counts { //인터페이
    fun studentCount(): Int
    fun scientistCount(): Int
}





fun main(args: Array<String>) {
    //Singleton use cases
    val marie = _Student(1, "Marie", "Curie")
    val albert = _Student(2, "Albert", "Einstein")
    val richard = _Student(3, "Richard", "Feynman")

    StudentRegistry.addStudent(marie)
    StudentRegistry.addStudent(albert)
    StudentRegistry.addStudent(richard)

    StudentRegistry.listAllStudents()
    // > Curie, Marie
    // > Einstein, Albert
    // > Feynman, Richard

    //StudentRegistry를 class로 생성했다면, 나타냈다면, 여러 개의 인스턴스를 생성할 수 있어 서로 일치하지 않는 여러 registry가 존재할 수 있다.
    //하지만, object를 사용하면 하나의 registry가 보장된다.




    //Using static members
    val emmy = Scientist.newScientist("Emmy", "Noether")
    val isaac = Scientist.newScientist("Isaac", "Newton")
    val nick = Scientist.newScientist("Nikola", "Tesla")
    //팩토리로 인스턴스를 생성한다.

    ScientistRepository.addScientist(emmy)
    ScientistRepository.addScientist(isaac)
    ScientistRepository.addScientist(nick)

    ScientistRepository.listAllScientists()
    // 1: Emmy Noether
    // 2: Isaac Newton
    // 3: Nikola Tesla

    //Companion naming and accessing from Java
    //companion object 뒤에 Custom한 이름을 사용할 수도 있다.
    val galileo = Scientist.Factory.newScientist("Galileo", "Galilei")
    //Java에서 Kotlin의 companion object에 접근할 때는 반드시 위와 같이 써야 한다.
    //하지만, Kotlin에서는 Scientist.newScientist("Galileo", "Galilei") 와 같이 암시적으로 호출할 수 있다.




    //Using anonymous objects
    val counter = object : Counts { //익명 object
        //인터페이스 메서드 재정
        override fun studentCount(): Int {
            return StudentRegistry.allStudents.size
        }

        override fun scientistCount(): Int {
            return ScientistRepository.allScientists.size
        }
    }
    //싱글톤과 달리, 앱을 생성할 때마다 객체의 버전이 달라진다.

    println(counter.studentCount()) // > 3
    println(counter.scientistCount()) // > 3
}



