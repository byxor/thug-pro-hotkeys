package xyz.byxor.thugprohotkeys.lock

import xyz.byxor.thugprohotkeys.lock.util.Publisher
import xyz.byxor.thugprohotkeys.lock.util.Subscriber


class LockController(
        private val lock: Lock
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