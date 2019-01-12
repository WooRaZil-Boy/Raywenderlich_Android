import kotlinx.coroutines.experimental.async

object BuildingYard {
//  fun startProject(name: String, floors: Int) {
//    val startTime = System.currentTimeMillis()
//    val building = Building(name)
//
//    building.speakThroughBullhorn("$name is started")
//    building.makeFoundation()
//
//    (0 until floors).forEach {
//      building.buildFloor(it)
//      building.placeWindows(it)
//      building.installDoors(it)
//      building.provideElectricity(it)
//      building.fitOut(it)
//    }
//
//    building.buildRoof()
//
//    building.speakThroughBullhorn("${building.name} is ready in ${System.currentTimeMillis() - startTime}!")
//  }

  suspend fun startProject(name: String, floors: Int) {
    val building = async {
      //람다에서 프로세스를 래핑하여 async에 전달한 후, wait()를 호출하여 병행중인 루틴을 일시 중지하고 결과를 기다리게 된다.
      val building = Building(name)

      val cores = Runtime.getRuntime().availableProcessors() //CPU 코어 수 반환 //코어보다 많은 스레드를 가질 수 있다.

      building.speakThroughBullhorn("The building of $name is started with $cores building machines engaged")

      building.makeFoundation().join() //join()을 사용하면, 완료될 때까지 기다린다.
      //foundation이 준비되기 전까지 다른 단계를 시작할 수 없다.

      (1..floors).forEach { //위의 building.makeFoundation().join()이 완료된 후 실행된다.
        building.buildFloor(it).join()  //join()을 사용하면, 완료될 때까지 기다린다.
        //floor가 완성되기 전까지 다른 단계를 시작할 수 없다.

        building.placeWindows(it)
        building.installDoors(it)
        building.provideElectricity(it)
        building.fitOut(it)
        //위의 함수들은 동시에 진행된다.
      }

      building.buildRoof().join()
      building
    }.await()

    if (building.floors == floors) {
      building.speakThroughBullhorn("${building.name} is ready!")
    }
  }

  //CommonPool 내의 다른 스레드에서 동시에 루틴이 실행된다.
}