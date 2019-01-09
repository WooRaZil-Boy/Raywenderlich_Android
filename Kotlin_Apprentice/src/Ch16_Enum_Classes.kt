import java.util.*

//때로는 제한된 수의 값에서 골라야 하는 경우도 있다. 허용되는 모든 값의 목록을 만들고 각 값을 열거할 수 있다.
//Kotlin에서 다른 언어와 마찬가지로 열거형은 고유한 특수 형식으로 여러 가지 값을 가질 수 있다.
//하지만 Kotlin에서의 큰 차이점은 enum class를 생성하여 열거형을 만든다는 점이다.




//Creating your first enum class
enum class DayOfTheWeek(val isWeekend: Boolean = false) { //enum class도 생성자를 가질 수 있다. default로 false를 설정해 준다.
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday(true),
    Sunday(true);
    //열거형의 요소는 쉼표로 구분되고, 마지막 요소에는 세미콜론을 사용하는 것이 좋다.
    //실제로는 마지막 요소도 쉼표로 작성해도 상관 없지만, 컴파일러가 목록의 끝에 도달했음을 알게하기 위해 명시적으로 적어주는 것이 좋다.




    //Enum class properties and functions
    //enum 클래스도 다른 클래스와 마찬가지로 속성과 메서드를 가질 수 있다. 생성자의 일부로 전달되도록 할 수도 있다.
    //특정 인스턴스에 의존하지 않는 작업을 위한 companion object도 역시 가질 수 있다.
    companion object {
        fun today(): DayOfTheWeek { //Java의 Calendar 클래스를 사용해 현재 날짜에 대한 정보를 가져와 enum으로 변환한다.
            val calendarDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            //Java의 Calendar shared instance로 요일(DAY_OF_WEEK)을 가져온다.
            var adjustedDay = calendarDayOfWeek - 2
            //Java의 Calendar 클래스는 일요일 부터 시작하고, 인덱스는 1부터 시작하므로 색인을 조정해 준다.
            val days = DayOfTheWeek.values() //enum의 요소를 가져온다.
            if (adjustedDay < 0) { //조정한 값이 0 보다 작다면, 문제가 있는 것이다.
                adjustedDay += days.count() //이 경우에는 존재하는 것이 확실한 값으로 값을 대입해 준다.
            }
            val today = days.first { it.ordinal == adjustedDay }
            //람다를 사용하는 first로 모든 요일의 목록에서 일치하는 첫 번째 요소를 반환한다.
            return today
        }
    }

    //특정 인스턴스에만 의존하는 메서드를 생성할 수도 있다.
    fun daysUntil(other: DayOfTheWeek) : Int {
        if (this.ordinal < other.ordinal) { //전달 된 값의 index가 더 큰 경우
            return other.ordinal - this.ordinal
        } else { //전달 된 값의 index가 작거나 같은 경우
            return other.ordinal - this.ordinal + DayOfTheWeek.values().count() //음수가 되지 않도록 요일을 추가해 준다.
        }
    }
}




//Sealed classes vs. enum classes

//Creating a sealed class
sealed class AcceptedCurrency {
    abstract val valueInDollars: Float
    //sealed class에 추상 속성을 추가하고, 각 하위 클래스에서 이를 재정의할 수 있다.
    var amount: Float = 0.0f
    //초기값을 설정한다면, non-abstract 변수(혹은 상수)를 sealed class에 추가할 수 있다.

    //따라서 모든 AcceptedCurrency의 하위 클래스는 valueInDollars 속성을 구현해야 하며,
    //amount 속성에 액세스할 수 있다. 이로써, 모든 하위 클래스에서 동일한 기능을 제공할 수 있다.

    class Dollar : AcceptedCurrency() {
        override val valueInDollars = 1.0f
    }

    class Euro : AcceptedCurrency() {
        override val valueInDollars = 1.25f
    }

    class Crypto : AcceptedCurrency() {
        override val valueInDollars = 2534.92f
    }

    //enum class에서 지원하던, name과 order 등의 편리한 기능이 sealed class에서는 자동으로 지원되지 않는다.
    //대신, 사용자 정의 getter를 사용해 추상화 되지 않은 속성을 가질 수 있고, 표현실을 사용할 수도 있다.
    val name: String
        get() = when (this) {
            is Euro -> "Euro"
            is Dollar -> "Dollars"
            is Crypto -> "NerdCoin"
        }

    fun totalValueInDollars(): Float {
        return amount * valueInDollars
    }
}



fun main(args: Array<String>) {
    for (day in DayOfTheWeek.values()) { //values로 enum class의 요소들을 가져올 수 있다.
        println("Day ${day.ordinal}: ${day.name}, is weekend: ${day.isWeekend}") //ordinal은 index를 나타낸다.
    }
    //enum의 장점은 다음과 같다.
    //• 이렇게 열거형으로 만들면, values()로 요소의 목록을 쉽게 가져와서 확인할 수 있다.
    //• ordinal 속성으로 indexing을 할 수 있다(0부터 시작).
    //• name 속성으로 각 요소의 값을 가져올 수 있다.

    //Kotlin에서는 enum도 class이기 때문에 인스턴스를 생성하고 속성 및 메서드를 정의할 수 있다.
    val dayIndex = 0
    val dayAtIndex = DayOfTheWeek.values()[dayIndex] //List에서 index로 사용할 수 있다.
    println("Day at $dayIndex is $dayAtIndex") //Day at 0 is Monday
    //인덱스를 변경하여 반환된 값을 업데이트 할 수 도 있다.

    //valueOf() 메서드는 String으로 해당 문자열과 일치하는 enum 인스턴스를 반환한다.
    val tuesday = DayOfTheWeek.valueOf("Tuesday") //valueOf은 null을 반환하지 않는다.
    println("Tuesday is day ${tuesday.ordinal}") //Tuesday is day 1

//    val notADay = DayOfTheWeek.valueOf("Blernsday") //valueOf은 null을 반환하지 않는다.
//    println("Not a day: $notADay") //out of index와 마찬가지로 예외가 발생해 crash된다.

    //Updating case order
    //enum의 또 다른 장점은 index의 순서를 변경해야  경우 쉽게 추가할 수 있다는 것이다.
    //단순히 enum class의 요소의 순서를 변경해 주면 된다.




    //Enum class properties and functions
    val today = DayOfTheWeek.today()
    val isWeekend = "It is${if (today.isWeekend) "" else " not"} the weekend"
    //삼항 연산자로 today.isWeekend 가 true이면 빈 문자열, false이면 "not"을 추가해 준다.

    println("It is $today. $isWeekend.") //enum class를 interpolation 하면, enum 의 name 속성이 자동으로 출력된다.

    val secondDay = DayOfTheWeek.Friday
    val daysUntil = today.daysUntil(secondDay)

    println("It is $today. $isWeekend. There are $daysUntil days until $secondDay.")




    //Using when with enum classes
    //enum class의 가장 강력한 점은 when(다른 언어의 switch)식과 결합하는 것이다.
    when (today) {
//        DayOfTheWeek.Monday -> println("I don't care if $today's blue")
//        DayOfTheWeek.Tuesday -> println("$today's gray")
//        DayOfTheWeek.Wednesday -> println("And $today, too")
//        DayOfTheWeek.Thursday -> println("$today, I don't care 'bout you")
        DayOfTheWeek.Friday -> println("It's $today, I'm in love")
//        DayOfTheWeek.Saturday -> println("$today, Wait...")
//        DayOfTheWeek.Sunday -> println("$today always comes too late")
        else -> println("I don't feel like singing")
        //보통은 else (다른 언어 switch의 default)에도 동작을 지정해 주지만, 모든 enum에 대해 동작이 지정되면 else를 추가해 줄 필요 없다.
    }
    //모든 case에 대해 동작을 지정해 주지 않으면, 런타임에서 warning이 뜬다.




    //Sealed classes vs. enum classes
    //여러 개의 type에서 enum class와 같은 효과를 내려면 sealed class를 사용한다.
    //sealed class는 제한된 수의 하위 클래스가 있으며, 모두 sealed class와 같은 파일에 정의되어 있다.
    //일부 서브 클래싱이 허용되기도 하지만, 극히 제한적이기 때문에 final과 반대되는 것으로 생각하면 된다.
    //sealed class를 사용해 여러 상속 계층을 추가하지 않고도 서브 클래싱의 유연성을 활용할 수 있다.
    //sealed class의 핵심 사항은 다음과 같다.
    //• abstract하다. 선언된 하위 클래스 중 하나만 직접 인스턴스화 할 수 없다.
    //• 추상 멤버를 가질 수 있으며, sealed class의 모든 하위 클래스에서 구현해야 한다.
    //• 각 case가 단일 인스턴스인 enum class와 달리, sealed class는 하위 클래스에 대해 여러 인스턴스를 가질 수 있다.
    //• 선언된 파일 밖에서 sealed class의 하위 클래스 인스턴스를 직접 생성할 수 없다. sealed class의 생성자는 항상 private이다.
    //• 선언된 파일 밖에서 간접 하위 클래스(ex. sealed class의 하위 클래스 중 하나를 상속하는 클래스)를 만들 수 있지만,
    //  위와 같은 제약 사항들 때문에 제대로 작동하지 않는다.

    //Creating a sealed class
    val currency = AcceptedCurrency.Crypto()
    println("You've got some $currency!")

    //enum class에서 지원하던, name과 order 등의 편리한 기능이 sealed class에서는 자동으로 지원되지 않는다.
    //대신, 사용자 정의 getter를 사용해 추상화 되지 않은 속성을 가질 수 있고, 표현실을 사용할 수도 있다.
    println("You've got some ${currency.name}!")

    currency.amount = .27541f
    println("${currency.amount} of ${currency.name} is "
            + "${currency.totalValueInDollars()} in Dollars") //길어질 경우 문자열을 + 로 열결할 수 있다.
    //AcceptedCurrency의 다양한 하위 클래스에서 원하는 만큼의 인스턴스를 생성할 수 있으며,
    //이런 인스턴스는 enum class 가 가질 수 없는 속성을 저장할 수 있다.




    //Enumeration as state machine
    //상태 머신은 시스템의 가능한 상태의 목록이다. 열거형을 사용하면, 호출자는 시스템이 어떤 상태인지 알 수 있다.
    Downloader().downloadData("foo.com/bar", //주어진 URL에서 데이터를 다운로드 하는 프로세
            progress = { downloadState ->
                when (downloadState) { //enum의 상태들을 when으로 각 case에 맞춘 동작을 할 수 있다.
//                    DownloadState.Idle -> println("Download has not yet started.")
                    null -> println("No download state yet")
                    DownloadState.Starting -> println("Starting download...")
                    DownloadState.InProgress -> println("Downloading data...")
                    DownloadState.Error -> println("An error occurred. Download terminated.")
                    DownloadState.Success -> println("Download completed successfully.")
                }
            },

            completion = { error, list ->
                error?.let { println("Got error: ${error.message}") }
                list?.let { println("Got list with ${list.size} items") }
            })

    //Nullables and enums
    //열거형은 null도 처리할 수 있다. 여기서 null로 Idle 상태를 표현할 수 있다.
    //null을 Idle(대기) 상태 외에도 필요에 따라 정보를 받지 못한 상태를 나타내거나, "Unknown" 등을 나타내는 상태로 사용할 수 있다.
}
