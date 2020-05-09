package xyz.byxor.thugprohotkeys.lock

import xyz.byxor.thugprohotkeys.commands.SET_RESTART_COMMAND
import xyz.byxor.thugprohotkeys.lock.util.Publisher
import xyz.byxor.thugprohotkeys.lock.util.Subscriber
import xyz.byxor.thugprohotkeys.logs.LogBuffer


class LockController(
        private val lock: Lock,
        private val logBuffer: LogBuffer
) : Subscriber<LockStateChangedEvent> {

    private val publisher = Publisher<LockStateChangedEvent>()

    init {
        lock.subscribeToStateChanges(this)
    }

    // CALLED BY VIEW
    // ================================

    fun subscribeToLockStateChanges(subscriber: Subscriber<LockStateChangedEvent>) {
        publisher.subscribe(subscriber)
    }

    fun toggleLock() {
        lock.toggle()
        val verb = if (lock.isLocked()) "Locked" else "Unlocked"
        val message = "$verb $SET_RESTART_COMMAND"
        logBuffer.addMessage(message)
    }

    // CALLED BY MODEL
    // ================================

    fun informSubscribersOfLockStateChanges(event: LockStateChangedEvent) {
        publisher.publish(event)
    }

    // CALLED INDIRECTLY BY MODEL
    // ================================

    override fun notify(event: LockStateChangedEvent) {
        publisher.publish(event)
    }
}