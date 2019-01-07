//Object methods
//object도 멤버 함수인 메서드를 가질 수 있다.
//companion object property와 같이 companion object method를 사용해 모든 인스턴스에서 액세스할 수 있다.
//인스턴스가 아닌 클래스 자체의 companion object method를 호출한다.
//Object method는 특정 인스턴스가 아닌 클래스 자체의 일반적인 것에 대해 유용하다.
class MyMath {
    companion object { //companion을 사용하면, local class가 될 수 없다.
        fun factorial(number: Int): Int {
            return (1..number).fold(1) { a, b -> a * b } //fold를 사용해 모든 정수의 곱을 구한다.
        }
    }
}




fun main(args: Array<String>) {
    //메서드는 class 또는 object의 내부에 있는 함수이다.




    //Method refresher
    val numbers = arrayListOf(1, 2, 3)
    numbers.removeAt(numbers.lastIndex)
    //removeAt()이 메서드이다.

    println(numbers) // > [1, 2]

    //Comparing methods to getters and setters
    //custom accessor를 사용해, 메서드와 비슷한 효과를 낼 수 있다. 둘 중에서 선택하는 것은 스타일의 차이이기도 하지만 몇가지 고려해볼 점이 있다.
    //속성은 get, set을 할 수 있는 값이 있으며, 메서드는 작업을 수행한다. 메서드의 목적은 단일 값을 반환하는 것이다.
    //값을 얻는 것에 더불어 값을 설정할 지 여부를 생각한다. property는 setter를 가질 수 있다.
    //고려할 다른 점은 광범위한 계산이 필요하거나 데이터베이스에서 값을 읽어와야 하는 지 여부이다.
    //간단한 값의 경우, 메서드가 오히려 접근 시간과 리소스 비용이 많이 들 수 있다.
    //O(1)과 같이 시간 복잡도가 낮은 경우에는 custom accessor를 사용한 property로 구현하는 것이 좋다. p.205

    //Turning a function into a method
    val months = arrayOf(
            "January", "February", "March",
            "April", "May", "June",
            "July", "August", "September",
            "October", "November", "December"
    )

    class SimpleDate1(var month:String)

    fun monthsUntilWinterBreak(from: SimpleDate1): Int {
        return months.indexOf("December") - months.indexOf(from.month)
    }

    //여기서 monthsUntilWinterBreak 함수를 메서드로 변환하면 다음과 같이 된다.
    class SimpleDate2(var month:String) {
        fun monthsUntilWinterBreak(from: SimpleDate2): Int {
            return months.indexOf("December") - months.indexOf(from.month)
        }
    }

    //메서드에 대한 키워드는 따로 없다. class 또는 object 내의 함수일 뿐이다.
    //property와 마찬가지로 dot notation을 사용해 인스턴스의 메서드를 호출할 수 있다.
    val date2 = SimpleDate2("October")
    println(date2.monthsUntilWinterBreak(date2)) // > 2




    //Introducing this
    //클래스 정의는 청사진과 같고, 인스턴스는 실제 개체와 같다. 인스턴스 값에 액세스하려면 클래스 내부에서 this 키워드를 사용한다.
    //this 키워드는 현재 인스턴스에 대한 참조로 사용된다. //Swift에서는 self
    class SimpleDate3(var month:String) {
        fun monthsUntilWinterBreak(): Int { //매개변수를 제거
//            return months.indexOf("December") - months.indexOf(this.month) //this로 인스턴스의 값에 접근한다.
            return months.indexOf("December") - months.indexOf(month)
            //코드를 더 깔끔하게 유지하려면 this. 를 제거해도 된다. 인스턴스에 대한 참조를 나타내지만,
            //Kotlin이 자동으로 이해하므로 대부분의 경우에는 this를 사용하지 않아도 된다.
            //로컬 변수와 동일한 이름을 가진 속성을 명확히 구분해야 하는 경우에만 this를 사용한다.
        }
    }

    val date3 = SimpleDate3("September")
    date3.monthsUntilWinterBreak() // 3 //매개변수를 전달하지 않고 메서드를 호출한다.




    //Object methods
    MyMath.factorial(6) // 720
    //독립적인 함수로 만들어도 되지만, 클래스의 companion object method로 그룹화 할 수 있다.
    //클래스는 name space 역할을 한다.




    //Extension methods
    //extension property와 마찬가지로, 메서드도 기존의 class 혹은 object에 추가할 수 있다.
    class SimpleDate() {
        var month:String = "January"

        fun monthsUntilWinterBreak(): Int {
            return months.indexOf("December") - months.indexOf(month)
        }
    }

    fun SimpleDate.monthsUntilSummerBreak(): Int { //class 이름 뒤에 메서드 이름을 정의하여 추가하면 된다.
        val monthIndex = months.indexOf(month)
        return if (monthIndex in 0..months.indexOf("June")) {
            months.indexOf("June") - months.indexOf(month)
        } else if (monthIndex in months.indexOf("June")..months.indexOf("August")) {
            0
        } else {
            months.indexOf("June") + (12 - months.indexOf(month))
        }
    }

    val date = SimpleDate()
    date.month = "December"

    println(date.monthsUntilSummerBreak()) // > 6
    //extension 한 메서드를 일반 메서드처럼 호출할 수 있다.

    //사용자가 작성한 Class 외에, 이미 존재하고 있는 라이브러리나 프레임워크도 extension할 수 있다.
    fun Int.abs(): Int { //Kotlin 표준 라이브러리에 abs() 가 있다.
        return if (this < 0) -this else this
    }

    println(4.abs())    // > 4
    println((-4).abs()) // > 4

    //Companion object extensions
    //class에 companion object가 있는 경우, Companion을 사용해 extension method를 추가할 수 있다.
    fun MyMath.Companion.primeFactors(value: Int): List<Int> { //소인수 분해계산
        //MyMath에는 companion object가 이미 존재한다. className.Companion 으로 companion object를 확장할 수 있다.
        var remainingValue = value //매개변수로 전달된 값은 상수이므로, 따로 변수로 저장한다.
        var testFactor = 2 //소수를 계산하기 위해 2부터 시작하여 remainingValue로 나눈다.
        val primes = mutableListOf<Int>()

        while (testFactor * testFactor <= remainingValue) { //remainingValue 까지 반복하면서 소인수 분해를 진행한다.
            if (remainingValue % testFactor == 0) {
                primes.add(testFactor)
                remainingValue /= testFactor
            } else {
                testFactor += 1
            }
        }

        if (remainingValue > 1) {
            primes.add(remainingValue)
        }

        return primes
    }

    MyMath.primeFactors(81) // [3, 3, 3, 3]
}