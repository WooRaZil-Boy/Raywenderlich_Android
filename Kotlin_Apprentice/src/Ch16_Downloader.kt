import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.timerTask
import kotlin.system.exitProcess

//Enumeration as state machine
//상태 머신은 시스템의 가능한 상태의 목록이다. 열거형을 사용하면, 호출자는 시스템이 어떤 상태인지 알 수 있다.
enum class DownloadState {
//    Idle,       //대기 상태
    Starting,   //다운로드 시작
    InProgress, //데이터 다운로드
    Error,      //오류
    Success     //다운로드 성공
}

class Downloader { //enum을 사용해, 시스템 상태에 대한 정보를 수집하고 보여준다.

    private val maxData = 100
    var downloadState: DownloadState? = null
    //Nullables and enums
    //열거형은 null도 처리할 수 있다. 여기서 null로 Idle 상태를 표현할 수 있다.
    //null을 Idle(대기) 상태 외에도 필요에 따라 정보를 받지 못한 상태를 나타내거나, "Unknown" 등을 나타내는 상태로 사용할 수 있다.
    private var fakeData: MutableList<Int> = mutableListOf()

    fun downloadData(fromUrl: String, progress: (state: DownloadState?) -> Unit, completion: (error: Error?, data: List<Int>?) -> Unit) {
        println("\"Downloading\" from URL: ${fromUrl}")
        postProgress(progress)
        downloadState = DownloadState.Starting
        keepAddingData(completion)
    }

    private fun keepAddingData(completion: (error: Error?, data: List<Int>?) -> Unit) {
        addMoreData { error ->
            when (downloadState) {
                DownloadState.Error -> completion(error, null)
                DownloadState.Success -> completion(null, fakeData.toList())
                else -> keepAddingData(completion)
            }
        }
    }

    private fun postProgress(progress: (state: DownloadState?) -> Unit) {
        progress(downloadState)

        when (downloadState) {
            DownloadState.Error -> exitProcess(1)
            DownloadState.Success -> exitProcess(0)
            else -> Timer().schedule(timerTask { postProgress(progress) }, 200)
        }
    }

    private fun addMoreData(completion: (error: Error?) -> Unit) {
        Timer().schedule(timerTask {
            val error = randomlyThrowError()
            if (error != null) {
                downloadState = DownloadState.Error
                completion(error)
            } else {
                downloadState = DownloadState.InProgress
                for (i in 0..20) {
                    fakeData.add(i)
                    if (fakeData.size == maxData) {
                        downloadState = DownloadState.Success
                        break
                    }
                }

                completion(null)
            }
        }, 500)
    }

    private fun randomlyThrowError(): Error? {
        val randomNumber: Int = (0..10).random()
        if (randomNumber == 8) {
            return Error("Your download was eaten by a shark.")
        } else {
            return null
        }
    }
}

// Via https://stackoverflow.com/a/45687695/681493
fun ClosedRange<Int>.random() =
        Random().nextInt(endInclusive - start) + start