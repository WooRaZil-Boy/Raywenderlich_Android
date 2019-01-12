import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class Building(val name: String, var floors: Int = 0) {
  //각 메서드마다, 특정 시간(밀리초) 동안 현재 스레드를 sleep한 다음, speakThroughBullhorn()를 호출하여 메시지를 출력한다.
  //코루틴을 사용하려면 다음과 같이 변경해야 한다.
  // • coroutines 및 다른 suspending 함수에서 호출할 수 있도록 suspend 키워드를 표시해 준다.
  // • launch()로 해당 함수를 래핑해 준다
  // • Thread.sleep() 대신 coroutine 함수 delay()로 변경한다.
  // • Thread.currentThread().name 으로 현재 스레드의 이름을 가져올 수 있다.
  // • floors 매개변수 Building 생성자에 추가한다.
  // • buildFloor()가 호출되면 마지막에 floors를 증가시킨다.


//  fun makeFoundation() {
//    Thread.sleep(2000)
//    speakThroughBullhorn("The foundation is ready")
//  }

  suspend fun makeFoundation() = launch {
    delay(300)
    speakThroughBullhorn("[${Thread.currentThread().name}] The foundation is ready")
  }

//  fun buildFloor(floor: Int) {
//    Thread.sleep(2000)
//    speakThroughBullhorn("The $floor'th floor is raised")
//  }

  suspend fun buildFloor(floor: Int) = launch {
    delay(100)
    speakThroughBullhorn("[${Thread.currentThread().name}] Floor number $floor floor is built")
    ++floors
  }

//  fun placeWindows(floor: Int) {
//    Thread.sleep(500)
//    speakThroughBullhorn("Windows are placed on the $floor'th floor")
//  }

  suspend fun placeWindows(floor: Int) = launch {
    delay(100)
    speakThroughBullhorn("[${Thread.currentThread().name}] Windows are placed on floor number $floor")
  }

//  fun installDoors(floor: Int) {
//    Thread.sleep(500)
//    speakThroughBullhorn("Doors are installed on the $floor'th floor")
//  }

  suspend fun installDoors(floor: Int) = launch {
    delay(100)
    speakThroughBullhorn("[${Thread.currentThread().name}] Doors are installed on floor number $floor")
  }

//  fun provideElectricity(floor: Int) {
//    Thread.sleep(500)
//    speakThroughBullhorn("Electricity is provided on the $floor'th floor")
//  }

  suspend fun provideElectricity(floor: Int) = launch {
    delay(100)
    speakThroughBullhorn("[${Thread.currentThread().name}] Electricity is provided on floor number $floor")
  }

//  fun buildRoof() {
//    Thread.sleep(2000)
//    speakThroughBullhorn("The roof is ready")
//  }

  suspend fun buildRoof() = launch {
    delay(200)
    speakThroughBullhorn("[${Thread.currentThread().name}] The roof is ready")
  }

//  fun fitOut(floor: Int) {
//    Thread.sleep(2000)
//    speakThroughBullhorn("The $floor'th floor is furnished")
//  }

  suspend fun fitOut(floor: Int) = launch {
    delay(200)
    speakThroughBullhorn("[${Thread.currentThread().name}] Floor number $floor is furnished")
  }

  fun speakThroughBullhorn(message: String) = println(message)

  //각 작업에 대해 프로세스를 최적화하려면 코루틴이 필요하다. 이 작업들은 결과가 따로 필요하지 않으므로, launch()로 코루틴을 만든다.
  //delay()로 특정 시간 동안 코루틴을 일시 정지한다.
}