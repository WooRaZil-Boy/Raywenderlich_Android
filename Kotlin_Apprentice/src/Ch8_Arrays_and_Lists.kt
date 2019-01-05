fun main(args: Array<String>) {
    //Collection은 여러 값을 함께 저장할 수 있는 유연한 컨테이너이다.




    //Arrays
    //Java의 기본 배열에 해당한다. 배열은 여러 값을 인접한 메모리에 저장한다.

    //What is an array?
    //배열은 같은 유형의 정렬된 값 모음이다. 요소는 0부터 인덱싱 된다. 따라서 인덱스는 배열의 값 배수보다 작다.

    //When are arrays useful?
    //배열은 항목을 특정 순서로 저장해야 하는 경우 유용하다. 요소를 정렬하거나 전체 배열을 반복하지 않고 인덱스로 해당 요소를 가져올 수 있다.

    //Creating arrays
    val evenNumbers = arrayOf(2, 4, 6, 8) //표준 라이브러리의 arrayOf() 함수를 사용해 배열을 생성 //Array<Int>
    //일반적인 언어에서와 달리 Literal로 val evenNumbers = [2, 3, 6, 8] 이렇게 생성할 수 없다.
    val fiveFives = Array(5, { 5 }) // 5, 5, 5, 5, 5 //뒤의 { 5 } 는  Lamda로, Swift의 클로저와 같다.
    val vowels = arrayOf("a", "e", "i", "o", "u") //Array<String>
    //val(상수)로 선언되었으면 요소를 변경할 수 없다.

    //Arrays of primitive types
    //arrayOf로 생성되는 배열의 경우, 객체의 list type이 된다. JVM에서 Integer는 Boxed Integer가 되며, 기본 Int가 아니다.
    //Boxed Integer를 사용할 필요 없는 경우, 기본 Int를 사용하면, 메모리 소모를 줄일 수 있다.
    val oddNumbers = intArrayOf(1, 3, 5, 7) //JVM에서 int[] (IntArray)으로 컴파일 된다.
    //같은 식으로 floatArrayOf(), doubleArrayOf(), booleanArrayOf() 등을 생성할 수 있다.
    //IntArray(), FloatArray(), DoubleArray() 로도 배열을 생성할 수 있다.
    val zeros = DoubleArray(4) // 0.0, 0.0, 0.0, 0.0
    val otherOddNumbers = arrayOf(1, 3, 5, 7).toIntArray() //arrayOf로 생성 후, toIntArray로 변환해 줄 수도 있다.
    //Array<Int>가 아닌  IntArray 타입이 된다.




    //Lists
    //List는 Array와 거의 유사하다. Kotlin에서의 List는 Java에서와 마찬가지로, ArrayList, LinkedList 등과 같이 Type이 구체화된 인터페이스이다.
    //Array는 일반적으로 List보다 효율적이지만, List는 동적인 크기를 가질 수 있다.
    //Array는 고정 크기 이지만, 나중에 필요에 따라 확장 및 축소할 수 있다.

    //Creating lists
    val innerPlanets = listOf("Mercury", "Venus", "Earth", "Mars") //Array처럼 Standard library에서 생성 가능하다. //List <String>
    val innerPlanetsArrayList = arrayListOf("Mercury", "Venus", "Earth", "Mars") //ArrayList
    //위의 두 list는 작성 후 변경을 할 수 없다.
    val subscribers: List<String> = listOf() //빈 목록으로 생성한다.
    //val subscribers = listOf<String>()와 같다.
    //컴파일러가 type을 유추할 수 없으므로 형식을 명시적으로 선언해 줘야 한다.

    //Mutable lists
    val outerPlanets = mutableListOf("Jupiter", "Saturn", "Uranus", "Neptune")
    val exoPlanets = mutableListOf<String>() //빈 목록으로 생




    //Accessing elements
    //배열과 리스트에서 요소에 엑세스 하는 방법은 비슷하다.

    //Using properties and methods
    val players = mutableListOf("Alice", "Bob", "Cindy", "Dan") //변경 가능한 list

    print(players.isEmpty()) //List가 비었는지 확인
    if (players.size < 2) { //List의 크기를 반환한다.
        println("We need at least two players!")
    } else {
        println("Let's start!")
    }

    var currentPlayer = players.first() //List가 비었을 경우에는 오류를 발생한다.
    println(currentPlayer) // > Alice
    println(players.last()) // > Dan

    val minPlayer = players.min() //가장 낮은 값을 가진 요소 반환
    minPlayer.let { //Nullable을 풀어 준다.
        println("$minPlayer will start") // > Alice will start
    }

    val maxPlayer = players.max()
    if (maxPlayer != null) {
        println("$maxPlayer is the MAX") // > Dan is the MAX
    }

    //fisrt, last 와 달리, min, max는 Nullable을 반환한다.

    //Using indexing
    val firstPlayer = players[0] //index로 해당 요소를 가져온다. //index는 0부터 시작
    println("First player is $firstPlayer") // > First player is Alice

    val secondPlayer = players.get(1) //subscript([])로 가져오는 것과 get 메서드로 가져오는 것은 같다.
//    val player = players[4] // > IndexOutOfBoundsException //인덱스를 벗어나면 오류가 발생한다.

    //Using ranges to slice
    val upcomingPlayersSlice = players.slice(1..2) //List<String> //시작 값이 종료 값보다 큰 경우 빈 배열을 반환한다.
    //slice() 메서드에서 반환한 객체는 원본과 별도의 배열 또는 리스트이므로 수정해도 원본에 영향을 주진 않는다.
    println(upcomingPlayersSlice.joinToString()) // > Bob, Cindy

    //Checking for an element
    fun isEliminated(player: String): Boolean {
        return player !in players //in 연산자로 특정 요소가 하나 이상 존재하는 지 확인 수 있다.
    }

    println(isEliminated("Bob")) // > false
    players.slice(1..3).contains("Alice") // false
    //in 연산자는 contains() 메서드와 같다.




    //Modifying lists
    //Appending elements
    players.add("Eli") //add로 배열(list)의 끝에 요소를 추가 가능하다. //type을 맞춰 (여기서는 String) 추가해 줘야 한다.
    //mutable list에서만 add()메서드를 사용할 수 있다.
    players += "Gina" //+= 연산자로 추가해 줄 수도 있다. //여러 항목도 추가 가능하다.
    println(players.joinToString())
    // > "Alice", "Bob", "Cindy", "Dan", "Eli", "Gina"

    var array = arrayOf(1, 2, 3) //배열은 리스트와 달리 고정 크기 이지만, var로 선언하면 += 연산자를 사용할 수 있다.
    //하지만 실제로는 새로운 크기의 배열을 만들어 다시 할당하는 것이다.
    array += 4
    println(array.joinToString()) // > 1, 2, 3, 4

    //Inserting elements
    players.add(5, "Frank") //주어진 인덱스 위치에 요소를 추가한다.
    //해당 요소 이후의 요소들은 하나씩 인덱스가 밀린다.

    //Removing elements
    val wasPlayerRemoved = players.remove("Gina") //remove로 해당 요소를 삭제할 수 있다.
    //remove 메서드는 제거가 성공했는지 여부를 boolean 으로 반환한다.
    println("It is $wasPlayerRemoved that Gina was removed")
    // > It is true that Gina was removed

    val removedPlayer = players.removeAt(2) //removeAt은 인덱스로 제거한다.
    //removeAt 메서드는 제거된 요소를 반환한다.
    println("$removedPlayer was removed") // > Cindy was removed

    players.indexOf("Bob") //해당 요소의 인덱스를 반환한다.
    //"Bob"이라는 요소가 여러 개 있을 수도 있다. 이 경우에는 첫 번째 index를 반환하며, 만약 요소를 찾지 못한 경우에는 -1을 반환한다.




    //Updating elements
    println(players.joinToString()) // > "Alice", "Bob", "Dan", "Eli", "Frank"
    players[4] = "Franklin" //해당 인덱스 요소의 값을 바꾼다.
    //players.set(4, "Franklin") 와 같은 표현이다.
    //index가 범위를 벗어난 index라면 오류가 발생하며 crash 된다.
    println(players.joinToString()) // > "Alice", "Bob", "Dan", "Eli", "Franklin"

    players[3] = "Anna"
    players.sort() //정렬
    println(players.joinToString()) // > "Alice", "Anna", Bob", "Dan", "Franklin"
    //get, set보다 일반적으로 인덱싱해서 가져오는 것을 주로 사용한다.

    val arrayOfInts = arrayOf(1, 2, 3)
    arrayOfInts[0] = 4
    println(arrayOfInts.joinToString()) // > 4, 2, 3




    //Iterating through a list
    val scores = listOf(2, 2, 8, 6, 1)

    for (player in players) { //for-in loop로, 해당 리스트(배열)의 요소를 반복할 수 있다.
        //index 0 ~ size-1 까지 반복한다.
        println(player)
    }

    for ((index, player) in players.withIndex()) {
        //withIndex로, index와 element를 동시에 가져올 수 있다. //Swift의 enumerate
        println("${index + 1}. $player")
    }

    fun sumOfElements(list: List<Int>): Int { //해당 요소의 합계 반
        var sum = 0
        for (number in list) {
            sum += number
        }
        return sum
    }
    println(sumOfElements(scores)) // > 19




    //Nullability and collection types
    var nullableList: List<Int>? = listOf(1, 2, 3, 4) //Nullable 배열(리스트) 생성
    //여기서 List<Int>? 와 List<Int?> 의 차이에 유의해야 한다.
    nullableList = null
    //List<Int>? 는 리스트 자체가 null이 될 수 있지만, 각 요소는 Int로 null이 될 수 없다.

    var listOfNullables: List<Int?> = listOf(1, 2, null, 4) //Nullable한 요소를 가지는 배열(리스트) 생성
    //여기서 List<Int>? 와 List<Int?> 의 차이에 유의해야 한다.
//    nullableList = null
    //List<Int?> 는 리스트 자체가 null이 될 수 없지만, 각 요소는 Int?로 null이 될 수 있다.

    var nullableListOfNullables: List<Int?>? = listOf(1, 2, null, 4) //Nullable한 요소를 가지는 Nullable 배열(리스트) 생성
    nullableListOfNullables = null
    //List<Int?>? 는 리스트 자체가 null이 될 수 있고, 각 요소도 Int?로 null이 될 수 있다.
}