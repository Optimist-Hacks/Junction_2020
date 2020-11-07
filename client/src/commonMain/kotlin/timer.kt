import com.soywiz.klock.DateTime
import com.soywiz.klock.TimeSpan
import com.soywiz.klock.milliseconds
import com.soywiz.korio.async.delay
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class Timer {
	var running = false

	var startTime = DateTime.nowUnixLong()
	var now = DateTime.nowUnixLong()
	set(value) {
		measuredTime = value - startTime
		triggerTime?.let {
			if (measuredTime >= it.milliseconds){
				stop()
				triggerEvent(onTimer)
			}

		}
		field = value
	}

	var measuredTime : Long = 0

	var triggerTime : TimeSpan? = null

	lateinit var job : Job

	var onTimer : () -> Unit = {}

	private suspend fun refresh(){
		while (true){
			now = DateTime.nowUnixLong()
			delay(16.milliseconds)
		}

	}

	fun start(){
		running = true
		startTime = DateTime.nowUnixLong()
		job = GlobalScope.launch {
			refresh()
		}
	}

	fun stop(){
		if (running){
			running = false
			job.cancel()
		}
	}

	fun triggerEvent( action : () -> Unit ) : Unit {
		action()
	}
}