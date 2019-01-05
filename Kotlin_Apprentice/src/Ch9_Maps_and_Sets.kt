fun main(args: Array<String>) {
    //map은 순서가 없는 쌍으로 각 쌍은 key와 value로 구성된다. //Swift의 Dictionary와 같다.
    //key는 고유한 값이며 동일한 유형이어야 한다. 두 개 이상의 key가 하나의 value를 가리킬 수 있다.
    //map은 식별자를 사용해 값을 검색해야 하는 경우 유용하다.
    //배열은 정수 인덱스로 값을 가져오며 순차적이지만, map에서 key는 모든 유형이 될 수 있으며 특별한 순서가 없다.




    //Creating maps
    var yearOfBirth = mapOf("Anna" to 1990, "Brian" to 1991, "Craig" to 1992, "Donna" to 1993)
    //표준 라이브러리의 mapOf() 함수로 맵을 생성할 수 있다. 쉼표로 쌍을 구분한다. //고정 크기의 변경 불가능한 map이 된다.

    var namesAndScores = mutableMapOf("Anna" to 2, "Brian" to 2, "Craig" to 8, "Donna" to 6)
    //타입은 MutableMap<String, Int> 이 된다. key는 String, value는 Int이다.
    println(namesAndScores) //맵은 특정한 순서가 없기에 출력할 때마다 순서가 달라질 수 있다.

    namesAndScores = mutableMapOf() //빈 맵을 생성한다.
    var pairs = HashMap<String, Int>() //이렇게 빈 맵을 생성할 수도 있다.
    pairs = HashMap<String, Int>(20) //맵의 용량을 정의해 줄 수도 있다.
    //용량을 미리 정의해 주면, 데이터의 양이 정해진 경우 성능을 향상 시킬 수 있다.




    //Accessing values

    //Using the index operator
    namesAndScores = mutableMapOf("Anna" to 2, "Brian" to 2, "Craig" to 8, "Donna" to 6)
    println(namesAndScores["Anna"]) //대괄호에 index를 쓰는 배열과 달리 대괄호에 key를 입력해서 액세스할 수 있다.
    namesAndScores["Greg"] // null //키가 없는 경우에는 null을 반환한다.
    //배열에서는 없는 index를 입력하면 crash가 나지만, 맵에서는 null을 반환할 뿐 crash 되지 않는다.

    //Using properties and methods
    println(namesAndScores.get("Craig")) //배열에서와 같이 subscript 대신 get, set을 사용할 수도 있다.
    //사실 인덱스를 사용하면, 내부적으로 get 함수를 호출해 반환한다.

    namesAndScores.isEmpty() // false
    namesAndScores.size      // 4




    //Modifying mutable maps
    //맵을 수정을 하기 위해서는 mutable map으로 선언해야 한다.

    //Adding pairs
    val bobData = mutableMapOf( //MutableMap<String, String>
            "name" to "Bob",
            "profession" to "CardPlayer",
            "country" to "USA")

    bobData.put("state", "CA") //bobData 맵에 해당 key가 없는 경우 key-value pair를 추가한다.
    bobData["city"] = "San Francisco" //subscripting으로 key-value pair를 추가해 줄 수도 있다.

    //Updating values
    bobData.put("name", "Bobby") //bobData 맵에 해당 key가 있는 경우 key에 대응하는 value를 업데이트 한다.
    //변경되기 전 key의 value인 Bob을 반환한다.
    //이전 값을 반환하는 이유는 put(key: K, value: V): V? 이기 때문이다. key가 없어 추가 되는 경우에는 null이 반환된다.
    bobData["profession"] = "Mailman" //subscripting으로 key에 대응하는 value를 변경해 줄 수 있다.

    val pair = "nickname" to "Bobby D" //Pair<String, String>
//    val pair = Pair("nickname", "Bobby D")와 같다.
    bobData += pair //+= 연산자를 사용해 추가하거나 수정해 줄 수도 있다.
    println(bobData)




    //Removing pairs
    bobData.remove("city") //해당 키와 관련된 값이 제거 된다.
    bobData.remove("state", "CA") //해당 키의 값이 일치하는 경우에만 제거한다.




    //Iterating through maps
    for ((player, score) in namesAndScores) { //맵은 key-value 쌍을 이루고 있으므로 이와 같이 가져온다.
        println ("$player - $score")
    }

    for (player in namesAndScores.keys) { //keys 프로퍼티로 key만 가져올 수 있다. //values로 value만 가져올 수도 있다.
        print("$player, ") // no newline
    }
    println()




    //Running time for map operations
    //맵이 작동하는 원리를 알려면 해싱의 종류와 그 작동 방식을 이해해야 한다.
    //해싱은 String, Int, Double, Boolean 등의 값을 해시라는 숫자로 변환하는 것을 말한다.
    //이 값으로 해시 테이블의 값을 빠르게 검색할 수 있다.
    //Kotlin의 Any 타입은 임의의 객체에 대해 해시 값을 반환하는 hashCode()를 정의한다.
    //모든 기본 유형에는 이 값이 구현되어 있다.
    println("some string".hashCode())
    // > 1395333309
    println(1.hashCode())
    // > 1
    println(false.hashCode())
    // > 1237

    //해시값은 주어진 값에 항상 동일한 값을 반환해야 한다. 하지만 해시 값을 절대로 저장해서 사용해선 안 된다. 프로그램 실행간 동일하다는 보장이 없기 때문이다.
    //Accessing elements, Inserting elements, Deleting elements, Searching for an element 모두 O(1)의 시간이 걸린다.
    //이는 배열보다 빠르지만, 맵은 순서가 없음에 유의해야 한다.
    //이 해시값을 잘 정의해야 성능을 향상 시킬 수 있다. 해시값을 제대로 구현하지 못한 최악의 경우에는 모든 연산이 linear time이나 O(n)이 될 수 있다.
    //보통의 경우에는 구현된 hashCode()를 사용한다. 성능이 중요한 경우에는 mapOf() 대신 hashMapOf()를 사용해 HashMap<K, V>로 구현해야 한다.




    //Sets
    //집합은 정렬되어 있지 않은 동일한 유형의 고유한 값들의 컬렉션이다. 집합은 항목의 순서가 중요하지 않은 경우, 유일한 값을 보장할 때 유용하다.

    //Creating sets
    val names = setOf("Anna", "Brian", "Craig", "Anna") //setOf로 집합을 생성할 수 있다.
    println(names) //순서는 임의이다.

    val hashSet = HashSet<Int>() //빈 집합 생

    //Set from arrays
    //집합은 배열에서 만들 수 있다.
    val someArray = arrayOf(1, 2, 3, 1)
    var someSet = mutableSetOf(*someArray) //배열을 spread operator (*)을 사용해 전달해 집합을 만들 수 있다.
    //배열의 타입에서 집합의 타입이 유추되기 때문에 명시적으로 MutableSet<Int>을 적어줄 필요 없다.
    println(someSet) // > [1, 2, 3]
    //집합은 고유한 값을 가지므로 중복된 1이 제거된다.




    //Accessing elements
    println(someSet.contains(1)) //해당 값을 포함하고 있는 지 확인할 수 있다.
    println(4 in someSet) //in 키워드를 사용해 확인할 수도 있다.

    //first(), last()를 사용할 수도 있다. 하지만, 집합은 순서가 없으므로 어떤 요소가 반환될 지는 알 수 없다.




    //Adding and removing elements
    someSet.add(5) //집합에 요소 추가. 이미 요소가 추가되어 있다면 아무것도 수행되지 않는다.

    val removedOne = someSet.remove(1) //해당 요소 삭제
    println(removedOne) // > true //삭제 여부를 boolean으로 반환한다.
    println(someSet) // > [2, 3, 5]




    //Running time for set operations
    //집합은 맵과 매우 유사하며, 요소에 해시값이 있어야 한다. 모든 작업의 big-O 시간은 HashSet와 HashMap이 같다.
}