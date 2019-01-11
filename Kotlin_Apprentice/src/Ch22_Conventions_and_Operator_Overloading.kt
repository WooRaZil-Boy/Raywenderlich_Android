//What is operator overloading?
//operator overloading은 주로 Syntactic sugar이며, 사용자 정의 데이터 유형에 수학 연산자를 사용하는 것이다.

//val fluffy = Kitten("Fluffy")
//val snowflake = Kitten("Snowflake")
//takeHome(fluffy + snowflake)
//Kitten 클래스 인스턴스가 두 개 있고, + 연산자를 사용한다.




//Getting started
class Company(val name: String) {
    val departments: ArrayList<Department> = arrayListOf()
    val allEmployees: List<Employee>
        get() = arrayListOf<Employee>().apply {
            departments.forEach {
                addAll(it.employees) }
            sort()
        }

    //Binary operator overloading
    operator fun plusAssign(department: Department) { //+=
        departments.add(department)
    }

    operator fun minusAssign(department: Department) { //-=
        departments.remove(department)
    }
}

class Department(val name: String = "Department") : Iterable<Employee> {
    val employees: ArrayList<Employee> = arrayListOf()

    //Binary operator overloading
    operator fun plusAssign(employee: Employee) { //+=
        employees.add(employee)
        println("${employee.name} hired to $name department")
    }

    operator fun minusAssign(employee: Employee) { //-=
        if (employees.contains(employee)) {
            employees.remove(employee)
            println("${employee.name} fired from $name department")
        }
    }

    //Handling collections
    operator fun get(index: Int): Employee? { //인덱스로 직원 액세스
        return if (index < employees.size) { //index가 해당 범위에 속하는 지 확인
            employees[index]
        } else {
            null
        }
    }

    operator fun set(index: Int, employee: Employee) { //인덱스로 직원 설정
        if (index < employees.size) {
            employees[index] = employee
        }
    }

    operator fun contains(employee: Employee) = employees.contains(employee) //해당 부서에 직원이 속해 있는지 여부

    //Handling collections
    override fun iterator() = employees.iterator()
    //반복자를 사용하려면, Iterable 인터페이스를 구현하고, iterator()를 재정의해야 한다.

}

class Employee(val company: Company, val name: String, var salary: Int) : Comparable<Employee> {
    operator fun inc(): Employee { //++
        salary += 5000
        println("$name got a raise to $$salary")
        return this
    }

    operator fun dec(): Employee { //--
        salary -= 5000
        println("$name's salary decreased to $$salary")
        return this
    }

    //Binary operator overloading
    operator fun plusAssign(increaseSalary: Int) { //+=
        salary += increaseSalary
        println("$name got a raise to $$salary")
    }

    operator fun minusAssign(decreaseSalary: Int) { //-=
        salary -= decreaseSalary
        println("$name's salary decreased to $$salary")
    }

    //Handling collections
    override operator fun compareTo(other: Employee): Int {
        //range 연산자를 사용하기 위해, 먼저 정렬의 방식을 정의해 줘야 한다.
        //Comparable 인터페이스를 구현하고, compareTo() 함수를 재정의해 줘야 한다.
        //compareTo() 함수를 operator 키워드로 표시하면 비교 연산자(<, >, = 등)를 사용할 수 있다.
        return when (other) {
            this -> 0
            else -> name.compareTo(other.name)
        }
    }

    operator fun rangeTo(other: Employee): List<Employee> {
        val currentIndex = company.allEmployees.indexOf(this)
        val otherIndex = company.allEmployees.indexOf(other)

        if (currentIndex >= otherIndex) { //시작 인덱스는 마지막 인덱스 보다 작아야 한다.
            return emptyList()
        }

        return company.allEmployees.slice(currentIndex..otherIndex)
        //currentIndex ~ otherIndex 사이의 요소를 가져온다.
    }
}




//Using conventions
//Kotlin에서 overloaded operator를 사용하는 것은 convention의 예이다.
//Kotlin에서 convention은 특정 방식으로 함수를 선언하고 사용하는 약속이며, 여기서는 연산자와 함께 함수를 사용할 수 있다.
//이전에 infix 키워드를 사용하여 함수를 표시할 때 convension을 사용했다. convension에 따라 함수 괄호를 생략하고 점과 괄호 없이 함수를 호출한다.

//Unary operator overloading
//+a, --a, a++, a+ 등의 증감 연산자를 사용하려면, 해당 클래스에 inc() 함수를 구현해 줘야 한다. 각 연산자와 함수는 다음과 같다.
//  ++a : inc()
//  --a : dec()
//  -a  : unaryMinus()
//  +a  : unaryPlus()
//  !a  : not()

//Binary operator overloading
//마찬가지로 이항 연산자도 구현해 줄 수 있다.
//  +=  : plusAssign()
//  -=  : minusAssign()




//Handling collections
//연산자 오버로딩은 collection 작업 시에도 매우 유용하다.
//get(), set(), contain() 등이 있다.

//Adding ranges
//.. 연산자를 사용하여 지정된 범위의 목록을 얻을 수도 있다.
//이를 구현하려면, 해당 연산자(..)에서 항상 동일한 결과를 얻도록 목록을 정렬하는 방법을 정의해야 한다.

//다른 연산자에 대한 함수는 다음과 같다.
//  a + b       : pus(b)
//  a - b       : minus(b)
//  a * b       : times(b)
//  a / b       : div(b)
//  a % b       : rem(b)
//  a in b      : contains(a)
//  a[i]        : get(i)
//  a[i, j]     : get(i, j)
//  a[i] = b    : set(i, b)
//  a[i, j] = b : set(i, j, b)
//  a()         : invoke()
//  a(i)        : invoke(i)
//  a(i, j)     : invoke(i, j)
//  a..b        : rangeTo(b)
//  a += b      : plusAssign(b)
//  a -= b      : minusAssign(b)
//  a *= b      : timesAssign(b)
//  a /= b      : divAssign(b)
//  a %= b      : remAssign(b)
//  a == b      : equals(b)
//  a > b       : compareTo(b)
//  a < b       : compareTo(b)
//  a >= b      : compareTo(b)
//  a <= b      : compareTo(b)




//Operator overloading and Java
//Java에서는 Kotlin과 달리 사용자 정의 연산자 overloading을 지원하지 않는다.
//그러나 + 연산자는 실제로 standard Java에서 오버로드 된다.
//오버로드된 연산자는 코드를 단순화할 수 있지만, 특정 코드에서 정확히 어떻게 작동하는지 명확하지 않기 때문에 오해의 소지가 있다.
//항상 예기치 않게 작동하지 않도록 신경써야 한다.




//Delegated properties as conventions
//getValue(), setValue() 함수에 대한 convention을 사용하여 속성의 초기화를 다른 객체에 위임할 수 있다.

//class NameDelegate {
//    var name: String by NameDelegate() //name 속성을 가져오거나 설정하는 모든 호출은 NameDelegate의 getValue(), setValue()에 위임된다.
//    //복잡한 논리와 연산을 Delegated Class로 통합하는 데 유용하다. //Ch13의 Delegated properties 참고
//
//    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
//        // return existing value
//    }
//
//    operator fun setValue(thisRef: Any?, property: KProperty<*>, value:
//    String) {
//        // set received value
//    }
//}




fun main(args: Array<String>) {
    // your company
    val company = Company("MyOwnCompany")

    // departments
    val developmentDepartment = Department("Development")
    val qaDepartment = Department("Quality Assurance")
    val hrDepartment = Department("Human Resources")

    // employees
    var Julia = Employee(company, "Julia", 100_000)
    var John = Employee(company, "John", 86_000)
    var Peter = Employee(company, "Peter", 100_000)

    var Sandra = Employee(company, "Sandra", 75_000)
    var Thomas = Employee(company, "Thomas", 73_000)
    var Alice = Employee(company, "Alice", 70_000)

    var Bernadette = Employee(company, "Bernadette", 66_000)
    var Mark = Employee(company, "Mark", 66_000)




    //Using conventions

    //Unary operator overloading
    ++Julia //now Julia's salary is 105_000 //Julia = Julia.inc();

    --Peter // now Peter's salary is 95_000 //Peter = Peter.dec();

    //Binary operator overloading
    Mark += 2500 //Mark.plusAssign(2500);
    Alice -= 2000 //Alice.minusAssign(2000);

    company += developmentDepartment //company.plusAssign(developmentDepartment);
    company += qaDepartment //company.plusAssign(qaDepartment);
    company += hrDepartment //company.plusAssign(hrDepartment);

    developmentDepartment += Julia //developmentDepartment.plusAssign(Julia);
    developmentDepartment += John //developmentDepartment.plusAssign(John);
    developmentDepartment += Peter //developmentDepartment.plusAssign(Peter);

    qaDepartment += Sandra //qaDepartment.plusAssign(Sandra);
    qaDepartment += Thomas //qaDepartment.plusAssign(Thomas);
    qaDepartment += Alice //qaDepartment.plusAssign(Alice);

    hrDepartment += Bernadette //hrDepartment.plusAssign(Bernadette);
    hrDepartment += Mark //hrDepartment.plusAssign(Mark);
    qaDepartment -= Thomas //qaDepartment.minusAssign(Thomas);




    //Handling collections
    val firstEmployee = qaDepartment[0] //Nullable Employee 반환
    qaDepartment[0]?.plusAssign(1000) //get()의 반환유형이 Employee? 이므로 += 를 직접 사용할 수 없다.

    qaDepartment[1] = Thomas //set()

    if (Thomas !in qaDepartment) { //contain()을 구현한 후 사용해야 한다.
        println("${Thomas.name} no longer works here")
    }

    //Adding ranges
    print((Alice..Mark).joinToString { it.name }) // prints "Alice, Bernadette, John, Julia, Mark"
}