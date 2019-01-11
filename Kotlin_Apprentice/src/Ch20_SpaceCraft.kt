//Throwing exceptions
class SpaceCraft {
    var isConnectionAvailable: Boolean = false
    var isEngineInOrder: Boolean = false
    var isInSpace: Boolean = false
    var fuel: Int = 0

    fun launch() {
        //문제가 되는 상황에서 throw로 해당 예외를 던 준다.
        if (fuel < 5) {
//            sendMessageToEarth("Out of fuel. Can't take off")
//            throw Exception("Out of fuel. Can't take off.")
            throw OutOfFuelException()
            return
        }

        if (!isEngineInOrder) {
//            sendMessageToEarth("The engine is broken. Can't take off")
//            throw Exception("The engine is broken. Can't take off.")
            throw BrokenEngineException()
            return
        }

        if (!isConnectionAvailable) {
//            sendMessageToEarth("No connection with Earth. Can't take off")
//            throw Exception("No connection with Earth. Can't take off.")
            throw SpaceToEarthConnectionFailedException()
            return
        }

        sendMessageToEarth("Trying to launch...")
        fuel -= 5
        sendMessageToEarth("I'm in space!")
        sendMessageToEarth("I've found some extraterrestrials")
        isInSpace = true
    }

    fun refuel() {
        fuel += 5
        sendMessageToEarth("The fuel tank is filled")
    }

    fun repairEngine() {
        isEngineInOrder = true
        sendMessageToEarth("The engine is in order")
    }

    fun fixConnection() {
        isConnectionAvailable = true
        sendMessageToEarth("Hello Earth! Can you hear me?")
        sendMessageToEarth("Connection is established")
    }

    fun land() {
        sendMessageToEarth("Landing...")
        isInSpace = false
    }

    fun sendMessageToEarth(message: String) {
        println("Spacecraft to Earth: $message")
    }
}




//Handling exceptions
//예외를 throw할 때 처리할 수 있다면 해줘야 한다. 하지만 꼭 예외가 발생한 곳에서 처리할 필요는 없다.
object SpacePort { //싱글톤
    fun investigateSpace(spaceCraft: SpaceCraft) {
        try { //try - catch는 잠재적으로 예외가 발생할 수 있는 코드를 래핑하여 crash를 피하고 예외를 처리한다.
            spaceCraft.launch() //예외는 spaceCraft 인스턴스에서 발생하지만, 처리는 다른 곳에서 해 줄 수 있다.
//        } catch (exception: Exception) { //catch 키워드 다음의 괄호 안에 예상되는 예외 또는 예외의 슈퍼 클래스를 지정한다.
//            //Exception은 모든 예외와 오류의 슈퍼 클래스이다.
//            //세부적인 예외를 구별해서 처리하려면 개별 exception 혹은 사용자 정의 exception을 사용한다.
//            spaceCraft.sendMessageToEarth(exception.localizedMessage)
//            //여기에서는 모든 예외가 이 catch 블록에 걸리게 된다. 예상되는 예외 별로 catch 블록을 따로 생성하는 것이 좋다.
//        }
        //try-catch를 사용하면, 예외가 여전히 발생하고 오류 메시지가 출력되지만 프로그램이 강제 종료되지는 않는다.




        } catch (exception: OutOfFuelException) {
            spaceCraft.sendMessageToEarth(exception.localizedMessage)
            spaceCraft.refuel()
        } catch (exception: BrokenEngineException) {
            spaceCraft.sendMessageToEarth(exception.localizedMessage)
            spaceCraft.repairEngine()
        } catch (exception: SpaceToEarthConnectionFailedException) {
            spaceCraft.sendMessageToEarth(exception.localizedMessage)
            spaceCraft.fixConnection()
        } finally { //finally는 예외 발생 여부와 관계 없이 실행된다.
            if (spaceCraft.isInSpace) {
                spaceCraft.land()
            } else {
                investigateSpace(spaceCraft)
            }
        }
        //여러 catch 블록을 사용해 세부적인 예외를 잡아낼 수 있다. 첫 일치하는 예외 블록에 걸리게 된다.
    }
}




//Creating custom exceptions
//Exception의 하위 클래스로 세부 custom exception을 생성해 준다.
class OutOfFuelException : Exception("Out of fuel. Can't take off.")
class BrokenEngineException : Exception("The engine is broken. Can't take off.")
class SpaceToEarthConnectionFailedException : Exception("No connection with Earth. Can't take off.")





