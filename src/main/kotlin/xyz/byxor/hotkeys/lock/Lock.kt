package xyz.byxor.hotkeys.lock

import xyz.byxor.hotkeys.lock.util.Publisher
import xyz.byxor.hotkeys.lock.util.Subscriber


class Lock(private var isLocked: Boolean = false) {

    private val publisher = Publisher<LockStateChangedEvent>()

    fun isLocked() = isLocked

    fun toggle() {
        when (isLocked) {
            true -> unlock()
            false -> lock()
        }
    }

    fun lock() {
        isLocked = true
        // disable /set command
        informControllerOfStateChange()
    }

    fun unlock() {
        isLocked = false
        // enable /set command
        informControllerOfStateChange()
    }

    fun subscribeToStateChanges(subscriber: Subscriber<LockStateChangedEvent>) {
        publisher.subscribe(subscriber)
    }

    private fun informControllerOfStateChange() {
        publisher.publish(LockStateChangedEvent(isLocked))
    }
}