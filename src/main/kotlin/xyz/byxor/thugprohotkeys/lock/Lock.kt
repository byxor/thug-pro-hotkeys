package xyz.byxor.thugprohotkeys.lock

import xyz.byxor.thugprohotkeys.lock.util.Publisher
import xyz.byxor.thugprohotkeys.lock.util.Subscriber


class Lock(private var isLocked: Boolean = false) {

    private val publisher = Publisher<LockStateChangedEvent>()

    fun isLocked() = isLocked
    fun isUnlocked() = !isLocked

    fun toggle() {
        when (isLocked) {
            true -> unlock()
            false -> lock()
        }
    }

    fun lock() {
        isLocked = true
        informSubscribersOfStateChange()
    }

    fun unlock() {
        isLocked = false
        informSubscribersOfStateChange()
    }

    fun subscribeToStateChanges(subscriber: Subscriber<LockStateChangedEvent>) {
        publisher.subscribe(subscriber)
    }

    private fun informSubscribersOfStateChange() {
        publisher.publish(LockStateChangedEvent(isLocked))
    }
}