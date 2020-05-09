package xyz.byxor.hotkeys.logs

import xyz.byxor.hotkeys.lock.util.Publisher
import xyz.byxor.hotkeys.lock.util.Subscriber

class LogController(
        private val logBuffer: LogBuffer
): Subscriber<LogsUpdatedEvent> {

    private val publisher = Publisher<LogsUpdatedEvent>()

    init {
        logBuffer.subscribeToUpdates(this)
    }

    override fun notify(event: LogsUpdatedEvent) {
        publisher.publish(event)
    }

    fun subscribeToUpdates(subscriber: Subscriber<LogsUpdatedEvent>) {
        publisher.subscribe(subscriber)
    }
}