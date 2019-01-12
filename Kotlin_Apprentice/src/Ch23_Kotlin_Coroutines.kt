import kotlin.concurrent.thread

//fun main(args: Array<String>) {
    //지금까지는 동기로만 코드를 실행했다. 하나의 명령이 CPU에서 순차적으로 실행되며, 다른 Core에서 동시에 실행되지 않는다.
    //결과적으로 실행 시간이 오래 걸리는 작업(서버에 request를 보내거나 대용량 파일 처리 등)의 경우, 작업이 완료될 때까지 사용자는 기다려야 한다.
    //이런 작업이 실행 중이어도, 프로그램과 상호작용할 수 있는 것이 비동기 프로그램의 개념이다.




    //Asynchronous programming
    //동기 방식과 달리 비동기 프로그래밍은 여러 작업을 동시에 병렬로 실행할 수 있다. 다른 작업들을 처리하거나, 한 작업을 분리해 동시에 처리할 수 있다.

    //Threads
    //Java에서는 스레드를 사용해 프로그램을 병렬처리 할 수 있다. java.lang.Thread 객체는 단일 스레드 내에서 명령을 순차적으로 수행하는 하나의 흐름을 나타낸다.
    //생성, 시작, 일시 정지, 결합 등, 다양한 방법으로 스레드를 조작할 수 있다. 여러 스레드를 작성하여 동시에 여러 작업을 수행할 수 있다.
//    thread(start = true, name = "another thread") {
//        //kotlin.concurrent 의 thread() 함수를 사용하여 스레드를 작성한다.
//        //start에 true를 전달하면, 스레드가 명령을 즉시 실행한다.
//        (0..10).forEach {
//            println("Message #$it from the ${Thread.currentThread().name}")
//        }
//    }
//
//    (0..10).forEach {
//        println("Message #$it from the ${Thread.currentThread().name}")
//    } //main thread

    //각 스레드는 병렬로 실행되기 때문에 순서의 불확정적이며 실행 시마다 달라질 수 있다.
    //출력결과를 보면, main thread와 another thread가 서로 다른 스레드의 종료를 기다리지 않고 동시에 실행된다. p.370
    //Java 스레드는 OS 레벨의 스레드를 기반으로 하므로, 많은 양의 시스템 리소스를 소비한다는 점을 유의해야 한다.
    //따라서 수 많은 스레드를 동시에 생성하고 작업하면, JVM에서 OutOfMemoryError가 throw 되면서 비정상 종료될 수 있다.




    //Coroutines
    //Java에서는 이를 해결할 방법이 없었지만, Kotlin에서는 코루틴으로 해결할 수 있다.
    //코루틴은 스레드를 차단하지 않고, 계산의 특정 지점에서 일시 중단되거나 다시 시작될 수 있다.
    //이는 매우 효율적인 작업으로 가볍고 추가적인 리소스를 많이 필요하지 않아 여러 개의 코루틴을 생성하고 동시에 실행할 수 있다.
    //코루틴은 정지 시점을 정할 수 있고, 일시 중단된 함수를 호출할 수 있다. 그렇게 중단된 이후 코루틴 뿐 아니라 다른 함수에서도 호출할 수 있다.
    //Kotlin 1.2 까지 코루틴은 베타버전 기능이다. 이후 버전에서 API가 변경될 수 있으며 마이그레이션을 진행해야 할 수도 있다.




    //Getting started
//    BuildingYard.startProject("Smart house", 20)
    //해당 코드를 실행하면 순차적으로 코드가 실행되기 때문에 시간이 오래 걸리고 작업이 하나씩 진행된다.
    //코루틴을 사용하도록 전환하면, 비동기 코드로 훨씬 빠르게 작업할 수 있다.

    //build.gradle 파일에서 dependency와 experimental feature 를 추가한다. p.372
//    async {
//        (0..10).forEach {
//            println("Message #$it from the ${Thread.currentThread().name}")
//        }
//    }
//
//    (0..10).forEach {
//        println("Message #$it from the ${Thread.currentThread().name}")
//    }
    //처음에 사용한 thread와 비슷한 코루틴이다.
    //코루틴을 사용하면 하나의 스레드는 여러개의 루틴을 실행할 수 있어 메모리 낭비를 막을 수 있다.




    //runBlocking()
//    public fun <T> runBlocking(context: CoroutineContext
//                               = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T): T
    //파라미터 블록에 전달한 일시 중지된 람다를 실행하기 위한 새로운 코루틴을 생성하는 non 코루틴 함수이다. 새 코루틴이 완료될 때까지 현재 스레드를 차단한다.
    //이렇게 하면, 프로그램 실행이 멈추지 않으면서, 코루틴이 완료될 수 있는 시간이 생긴다.
    //CoroutineContext는 코루틴이 실행 중에 액세스할 수 있는 context를 나타낸다. 기본적으로 코루틴에 필요한 값을 key-value로 관리하는 맵이다.

//    public interface CoroutineScope {
//        public actual val isActive: Boolean
//        public actual val coroutineContext: CoroutineContext
//    }
    //CoroutineScope는 runBlocking()에 전달하는 람다의 receiver type이다.
    //runBlocking()에 전달한 람다 내부에서 코루틴이 활성화되어 있는지 여부 확인하고 context에 액세스할 수 있다.

    //async() and await()
    //async()와 await()는 같이 쓰이는 경우가 많다.
//    public actual fun <T> async(
//        context: CoroutineContext = DefaultDispatcher,
//        start: CoroutineStart = CoroutineStart.DEFAULT,
//        parent: Job? = null,
//        block: suspend CoroutineScope.() -> T
//    ): Deferred<T>
    //runBlocking()과 마찬가지로 async() 함수는 새로운 코루틴을 작성한다. 차이점은 async()는 스레드를 차단하지 않고 지연된 객체를 반환한다는 점이다.
    //코루틴의 시작 시간을 수정할 수도 있다(default는 즉시 시작).

    //context 매개변수의 기본값은 DefaultDispatcher이며, CommonPool과 같다.
//    public actual val DefaultDispatcher: CoroutineDispatcher = CommonPool
    //CoroutineDispatcher는 CoroutineContext의 하위 클래스로, 모든 코루틴 Dispatcher의 기본 클래스이다.
    //CommonPool은 시간과 자원을 소비하는 작업에 필요한 공유 스레드 풀이다. 효율을 높이기 위해 여러 스레드에서 코루틴이 실행된다.

    //Deferred은 미래에 계산될 수 있는 결과를 나타내는 인터페이스이다. 블록이 성공적으로 실행되어 T 타입의 결과를 가져오거나, 실패하여 예외가 발생한다.
    //블록 실행 결과까지 시간이 걸릴 수 있으므로, 실제 값을 가져오기 전까지 awit() 로 일시중지 해 줄 수 있다.

//    public actual suspend fun await(): T
    //await() 함수는 결과가 나올때 까지 해당 코루틴을 일시 중지한다. 완료가 되면 결과를 반환하며, 현재 스레드를 차단하지 않는다.
//    val userData = async { getUserDataFromServer() }.await()

    //launch() and join()
//    public actual fun launch(
//        context: CoroutineContext = DefaultDispatcher,
//        start: CoroutineStart = CoroutineStart.DEFAULT,
//        parent: Job? = null,
//        block: suspend CoroutineScope.() -> Unit
//    ): Job
    //launch()는 async()와 비슷하지만, 결과값에 관심이 없는 경우 사용한다. 코루틴 context에서 코루틴을 나타내는 Job 객체를 반환한다.
    //launch() 내부에서 thrown 된 예외는 catch 되지 않는다. 따라서 예외가 발생하면, stacktrace 가 출력되고 코루틴이 즉시 중단된다.

    //launch()에서 블록이 완료되는 것을 기다려야 한다면 join()을 사용한다. await()와 비슷하지만, 반환값은 Unit이다.
//    public actual suspend fun join()
    //사용은 아래와 같이 한다.
//    launch { postVideoToFeed() }.join()

//}




//Example: A high-rise building
//일부 파트의 작업들은 동시에 실행될 수 있지만, 서로 다른 파트들은 순차적으로 완료되어야 한다.
fun main(args: Array<String>) = runBlocking {
    BuildingYard().startProject("Smart house", 20)
    //완료되기 전까지 프로그램을 종료시키지 않으려면 runBlocking()을 사용한다.
}




//Understanding coroutines under the hood
//코루틴은 소프트웨어 개발의 새로운 개념이 아니다. C#, Ruby, Python 등 여러 언어가 지원한다.
//Kotlin 캄파일러는 가각의 코루틴에 대한 상태 시스템을 나타내는 클래스를 생성한다.
//코루틴 실행이 일시 중단 지점에 도달하면, state machine은 나중에 재실행을 쉽게 하기 위해 코루틴의 현재 상태를 저장한다.
//이런 방식으로 코루틴은 스레드를 차단하지 않고 스레드의 실행을 위해 하나의 클래스만 필요하기 때문에 효율적이다.




//Where to go from here?
//Kotlin 에서 코드 병렬화를 위한 다른 방식으로는 Rx가 있다.
//또, Rx와 coroutine을 동시에 사용할 수도 있다.

