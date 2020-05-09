package xyz.byxor.thugprohotkeys.logs

import xyz.byxor.thugprohotkeys.lock.util.Publisher
import xyz.byxor.thugprohotkeys.lock.util.Subscriber

class LogController(
        private val logBuffer: LogBuffer
): Subscriber<LogsUpdatedEvent> {

    private val publisher = Publisher<LogsUpdatedEvent>()

    init {
        logBuffer.subscribeToUpdates(this)
    }

    fun subscribeToUpdates(subscriber: Subscriber<LogsUpdatedEvent>) {
        publisher.subscribe(subscriber)
    }

    override fun notify(event: LogsUpdatedEvent) {
        publisher.publish(event)
    }
}